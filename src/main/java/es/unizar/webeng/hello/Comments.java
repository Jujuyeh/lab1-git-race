package es.unizar.webeng.hello;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class Comments {
        
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Returns the list of all the comments saved on redis.
     * @return List of comments saved on redis.
     */
    private List<String> getComments() {
        try {

            if (sharedData.hasKey("comments")) {
                /** Loads the comments into a string */
                String readValue = sharedData.opsForValue().get("comments");

                /** Returns the string converted in to a list of strings */
                return mapper.readValue(readValue, List.class);

            } else {
                
                List<String> comments = new ArrayList<String>();
                String jsonInString = mapper.writeValueAsString(comments);
                sharedData.opsForValue().set("comments", jsonInString );
                return comments;
            }
            
        } catch (Exception e) {
            System.out.println("Error obtaining comments");
            return null;
        }
    }

    /**
     * Stores the comment "comment" to redis.
     * @param comment comment to store to redis.
     * @param name author of the comment.
     */
    private void insertComment(String comment, String name) {
        Date currentTime = new Date();
        comment = "<font size=\"+1\">" + comment + "</font>";
        comment += "<br>Written by " + name + " on " + currentTime.toString();
        List<String> comments = getComments();
        comments.add(comment);
        try {
            String jsonInString = mapper.writeValueAsString(comments);
            sharedData.opsForValue().set("comments", jsonInString );
        } catch (Exception e) {
            System.out.println("Error inserting comment");
        }
    }

    /**
     * Loads the coments to the "model" object and renders the page.
     *
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/comments")
    public String welcome(Map<String, Object> model) {

        /** Loads comments saved on redis */
        List<String> comments = getComments();

        /** If there are no comments, show a custom message */
        if (comments.isEmpty()) {
            comments.add("There are no comments yet");
        }
        System.out.println(comments);
        model.put("comments", comments);

        /** Renders "comments" view using "model" attributes */
        return "comments";
    }

    /**
     * Stores the comment "comment" to redis.
     * @param model the attributes for rendering, not null.
     * @param comment comment to store to redis.
     * @param name name of the author of the comment.
     * @return redirect to main page.
     */
    @PostMapping("/comments") 
    public String newComment(Map<String, Object> model, 
                            @RequestParam("comment") String comment, 
                            @RequestParam("name") String name) {
        insertComment(comment, name);
        return "redirect:/comments";
    }

}
