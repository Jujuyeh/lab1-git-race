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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class Comments {

    Logger logger = LoggerFactory.getLogger(Comments.class);
        
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Returns the list of all the comments saved on redis.
     * @return List of comments saved on redis.
     */
    private List<String> getComments() {
        try {

            /** Checks if redis has the key "comments" */
            if (sharedData.hasKey("comments")) {

                /** Loads the comments into a string */
                String readValue = sharedData.opsForValue().get("comments");

                /** Returns the string converted in to a list of strings */
                return mapper.readValue(readValue, List.class);

            } else {
                
                /** If redis does not have the key, it creates it */
                List<String> comments = new ArrayList<String>();
                String jsonInString = mapper.writeValueAsString(comments);
                sharedData.opsForValue().set("comments", jsonInString );

                /** Returns the empty array */
                return comments;
            }
            
        } catch (Exception e) {
            logger.debug("Error obtaining comments");
            return new ArrayList<String>();
        }
    }

    /**
     * Stores the comment "comment" to redis.
     * @param comment comment to store to redis.
     * @param name author of the comment.
     */
    private void insertComment(String comment, String name) {

        /** Obtains the time when the comment was written */
        Date currentTime = new Date();

        /** Constructs the string that will be saved in redis */
        comment = "<font size=\"+1\">" + comment + "</font>";
        comment += "<br>Written by " + name + " on " + currentTime.toString();

        /** Obtains the saved comments in redis */
        List<String> comments = getComments();

        /** Adds the new comment to the list of comments retrieved */
        comments.add(comment);
        try {

            /** Converts the list into a string */
            String jsonInString = mapper.writeValueAsString(comments);

            /** Saves the string to redis */
            sharedData.opsForValue().set("comments", jsonInString );

        } catch (Exception e) {
            logger.debug("Error inserting comment");
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

        /** Loads the comments saved on redis */
        List<String> comments = getComments();

        /** If there are no comments, it will save a custom comment */
        if (comments.isEmpty()) {
            comments.add("There are no comments yet");
        }
        
        /** Adds the list of comments to "model" */
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
                            @RequestParam(name=  "comment", required = true) String comment, 
                            @RequestParam(name = "name", required = true) String name) {

        /** Stores the new comment to redis */
        insertComment(comment, name);

        /** Redirects to "/comments" */
        return "redirect:/comments";
    }

}
