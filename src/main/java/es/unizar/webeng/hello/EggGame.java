package es.unizar.webeng.hello;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EggGame {
    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Show the Egg game consisting of clicking the egg until you get a better score
     * than the accumulated one.
     * 
     * If there is accumulated score, the sample. If there isn't, it shows that
     * nobody has played yet.
     * 
     * @param model the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/egg")
    public String egg(Map<String, Object> model) {
        String bestEgg = "";
        String eggValueStored = sharedData.opsForValue().get("eggValueStored");
        String usernameStored = sharedData.opsForValue().get("usernameStored");

        if (eggValueStored != null && usernameStored != null) {
            Integer eggNumberStored = Integer.valueOf(eggValueStored);
            bestEgg = "Best score: " + eggNumberStored + ". User -> " + usernameStored;
        } else {
            bestEgg = "No one has played";
        }

        model.put("bestEgg", bestEgg);
        return "egg";
    }

    /**
     *
     * If the number of clicks on the egg, "eggValue", is the highest, it stores it
     * in redis with the username and displays a message about it.
     * 
     * If it is not, it does not store it and displays the corresponding message.
     * 
     * @param eggValue number of clicks on the egg
     * @param username current user name
     * @param model    the attributes for rendering, not null
     * @return the view name
     */
    @PostMapping("/egg")
    public String eggPlayed(@RequestParam(value = "eggValue", required = false) String eggValue,
            @RequestParam(value = "username", required = false) String username, Map<String, Object> model) {
        String eggRes = "";
        String bestEgg = "";
        Integer eggNumberStored;
        String eggValueStored = sharedData.opsForValue().get("eggValueStored");
        String usernameStored = sharedData.opsForValue().get("usernameStored");

        if (eggValueStored != null && usernameStored != null) {
            eggNumberStored = Integer.valueOf(eggValueStored);
        } else {
            eggNumberStored = 0;
            usernameStored = "";
        }

        if (eggValue != null && !eggValue.isEmpty() && eggValue != "0" && username != null && !username.isEmpty()) {
            Integer eggNumberNew = Integer.valueOf(eggValue);
            if (eggNumberNew > eggNumberStored) {
                sharedData.opsForValue().set("eggValueStored", Integer.toString(eggNumberNew));
                sharedData.opsForValue().set("usernameStored", username);
                eggRes = "You have achieved the best score! :)";
                bestEgg = "Best score: " + eggNumberNew + ". User -> " + username;
            } else {
                eggRes = "You have not exceeded the best score... :(";
                bestEgg = "Best score: " + eggNumberStored + ". User -> " + usernameStored;
            }
        } else {
            eggRes = "Click on the egg to play and enter the username before sending.";
            bestEgg = "Best score: " + eggNumberStored + ". User -> " + usernameStored;
        }

        model.put("eggRes", eggRes);
        model.put("bestEgg", bestEgg);

        return "egg";
    }
}