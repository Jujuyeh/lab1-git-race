package es.unizar.webeng.hello;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * REST API for comments stored on redis.
 */
@Controller
public class Comments {

    /** Logs errors */
    Logger logger = LoggerFactory.getLogger(Comments.class);
        
    /** Creates JSON strings from objects and viceversa */
    Gson gson = new Gson();

    /** Redis object */
    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Returns the list of all the comments saved on redis.
     * @return List of comments saved on redis.
     */
    private List<Comment> getComments() throws Exception {

        /** Checks if redis has the key "comments" */
        if (sharedData.hasKey("comments")) {

            /** Loads the comments into a string */
            String readValue = sharedData.opsForValue().get("comments");

            /** Returns the string converted in to a list of comments */
            Type type = new TypeToken<List<Comment>>(){}.getType();
            List<Comment> comments = gson.fromJson(readValue, type);
            return comments;

        } else {
            
            /** If redis does not have the key, it creates it */
            List<Comment> comments = new ArrayList<Comment>();
            sharedData.opsForValue().set("comments", gson.toJson(comments));

            /** Returns the empty array */
            return comments;
        }
    }

    /**
     * Returns the array of comments stored on redis.
     * @return Array of comments stored on redis.
     */
    @GetMapping("/comments")
    public @ResponseBody ResponseEntity<List<Comment>> welcome() {

        try {

            /** Loads the comments saved on redis */
            List<Comment> comments = getComments();

            /** If there are no comments, it will save a custom comment */
            if (comments.isEmpty()) {
                comments.add(new Comment("There are no comments yet", "admin", new Date()));
            }

            return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);

        } catch (Exception e) {
            logger.debug("Error obtaining comments");
            return new ResponseEntity<List<Comment>>(new ArrayList<Comment>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Stores the comment "comment" to redis.
     * @param comment comment to store to redis.
     * @param name name of the author of the comment.
     * @return redirect to main page.
     */
    @PostMapping("/comments") 
    public @ResponseBody ResponseEntity<String> newComment(@RequestParam(name=  "comment", required = true) String comment, 
                            @RequestParam(name = "name", required = true) String name) {

        try {

            /** Constructs the Comment object that will be saved in redis */
            Comment newComment = new Comment(comment, name, new Date());

            /** Obtains the saved comments in redis */
            List<Comment> comments = getComments();

            /** Adds the new comment to the list of comments retrieved */
            comments.add(newComment);
    
            /** Converts the list into a JSON string */
            String jsonString = gson.toJson(comments);

            /** Saves the string to redis */
            sharedData.opsForValue().set("comments", jsonString);

            return new ResponseEntity<String>("Ok", HttpStatus.CREATED);

        } catch (Exception e) {
            logger.debug("Error inserting comment");
            return new ResponseEntity<String>("Error inserting comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
