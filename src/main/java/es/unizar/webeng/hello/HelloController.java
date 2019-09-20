package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import com.google.common.hash.Hashing;

@Controller
public class HelloController {
    @Value("${app.message:Hello World}")
    private String message;

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", message);
        return "wellcome";
    }

    /**
     * Application for thanos game page. 
     * In case the parameter "name"" doesnt exist, the application 
     * give  the view to a form in order to get it. If the parameter "name"
     * exists the application give the result dead or alive (views)
     * 
     * @param name  the name of the player, no required
     * @param model  the attributes (map) for the views, not null
     * @return the view
     */
    @GetMapping("/thanos")
    public String play(@RequestParam(value = "name", required=false) String name, Map<String, Object> model) {
        model.put("name", name);
        if (name != null && !name.isEmpty()) {
            //Name is not null, we get the firt 4 bits of the hash into a integer
            Integer hash_bytes = Hashing.murmur3_128().hashString(name, StandardCharsets.UTF_8).asInt();
            if (hash_bytes < 0) {
                hash_bytes = hash_bytes * -1;
            }
            
            // If the integer is even the result is dead, else alive.
            if (hash_bytes % 2 == 0) {
                // Dead
                model.put("msg", "Look like you are from the other 50%...");
                model.put("msg2", "You would dead :(");
                model.put("img", "https://media3.giphy.com/media/AiEr9b7sX5VKIoIvQL/giphy.gif");
            } else {
                // Alive
                model.put("msg", "You would survive!");
                model.put("msg2", "Congratulations!!");
                model.put("img", "https://media2.giphy.com/media/Q8auEgoR7x0CcgH4uQ/giphy.gif");
            }
            return "thanos_res";
        } else {
            //The name is null, return the form page
            return "thanos";
        }
    }
}
