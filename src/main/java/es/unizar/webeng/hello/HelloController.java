package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class HelloController {
    // Sets message value from properties,
    // or default string if not defined.
    @Value("${app.message:Hello World}")
    private String message;

    /**
     * Application welcome page.
     *
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        // Sets "time" attribute to current date and time
        model.put("time", new Date());
        model.put("message", message);
        // Renders "wellcome" view using "model" attributes
        return "wellcome";
    }
}
