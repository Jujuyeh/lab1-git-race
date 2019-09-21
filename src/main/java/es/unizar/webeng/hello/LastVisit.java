package es.unizar.webeng.hello;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LastVisit {

    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Basic application that tells you whether it is the first
     * time you visit the page or not. If it isn't your first visit,
     * it also tells you the time of said visit.
     * 
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/visit")
    public String visit(Map<String, Object> model) {
        // Get request's IP address
        // https://stackoverflow.com/questions/22877350/how-to-extract-ip-address-in-spring-mvc-controller-get-call
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        // Check if savedTime exists on sharedData
        String lastVisitTime = sharedData.opsForValue().get(ip);
        if (lastVisitTime == null) {
            // It's the user's first time
            model.put("visitTime", "This is your first time! Welcome!");
        } else {
            // Display the last visit time
            model.put("visitTime", lastVisitTime);
        }
        // Save time info. for the user's next visit
        Date currentTime = new Date();
        sharedData.opsForValue().set(ip, currentTime.toString());
        return "visit";
    }
}