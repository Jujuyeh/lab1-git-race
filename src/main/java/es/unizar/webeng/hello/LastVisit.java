package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class LastVisit {

    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Basic application that tells you whether it is the first
     * time you visit the page or not. If it isn't your first visit,
     * it also tells you the 
     * 
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/visit")
    public String visit(Map<String, Object> model) {
        // Check if savedTime exists on sharedData
        String lastVisitTime = sharedData.opsForValue().get("savedTime");
        if (lastVisitTime == null) {
            // No data, it is the user's first visit
            model.put("displayText", "This is your first visit to this page! Welcome!");
        } else {
            // Display the last visit time
            model.put("displayText", "Your last visit to this page was on " + lastVisitTime);
        }
        // Save time info. for the user's next visit
        Date currentTime = new Date();
        sharedData.opsForValue().set("savedTime", currentTime.toString());
        return "visit";
    }
}