package es.unizar.webeng.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.GregorianCalendar;
import java.util.List;
import java.lang.IllegalStateException;
import java.lang.NullPointerException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicInteger;


@Controller
public class HelloController {
    
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StringRedisTemplate sharedData;

    /**
     * Returns the list of all the comments saved on redis.
     * @return List of comments saved on redis.
     */
    private List<String> getComments() {
        try {

            /** Loads the comments into a string */
            String readValue = sharedData.opsForValue().get("comments");

            /** Returns the string converted in to a list of strings */
            return mapper.readValue(readValue, List.class);
            
        } catch (Exception e) {
            System.out.println("Error obtaining comments");
            return null;
        }
    }

    /**
     * Stores the comment "comment" to redis.
     * @param comment comment to store to redis.
     */
    private void insertComment(String comment) {
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
     * Sets message value from properties,
     * or default string if not defined.
     */
    @Value("${app.message:Hello World}")
    private String message;
    private Date last_time = new Date();
    private Date deadline = new GregorianCalendar(2019, Calendar.OCTOBER, 25, 23, 59, 59).getTime();
    private String last_ip = "0.0.0.0";

    @Value("${app.visitorCount}")
    private int visitorCount;
    
    @Autowired
    private HttpServletRequest request;

    private long getSecondsLeft(long difference){
        return difference/1000%60;
    }

    private long getMinutesLeft(long difference){
        return difference/(1000*60)%60;
    }

    private long getHoursLeft(long difference){
        return difference/(1000*60*60)%24;
    }

    private long getDaysLeft(long difference){
        return difference/(1000*60*60*24);
    }

    /**
     * Application welcome page.
     *
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        /** Sets "os" and "version" from system information */
        model.put("os", System.getProperty("os.name"));
        model.put("version", System.getProperty("os.version"));
        /** @see https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java */
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            /** Sets "hostname" attribute to server machine name */
            model.put("hostname", addr.getHostName());
        }
        catch (UnknownHostException ex)
        {
            logger.info("Hostname can not be resolved");
        }
        /** Sets "time" attribute to current date and time */
        model.put("time", new Date());
        /** Sets time of last request */
        model.put("last_time", last_time);
        last_time = new Date();
        /** Sets IP of last request */
        model.put("last_ip", last_ip);
        try {
            /** @see https://www.mkyong.com/spring-mvc/spring-mvc-how-to-get-client-ip-address/ */
            String remoteAddr = "";
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
            last_ip = remoteAddr;
        }
        catch (IllegalStateException e) {
            /** For unit tests */
            logger.debug("Request object is not valid");
        }
        model.put("message", message);

        /* It adds one to the visits of the web */
        synchronized(this){ // We do it in an atomic way to avoid race conditions.
            visitorCount++;
        }
        model.put("visitorCount", visitorCount);

        /** Sets date of deadline */
        model.put("deadline", deadline);
        long countdownDifference = deadline.getTime() - last_time.getTime();
        /** Sets seconds left to deadline */
        model.put("secondsLeft", getSecondsLeft(countdownDifference));
        /** Sets minutes left to deadline */
        model.put("minutesLeft", getMinutesLeft(countdownDifference));
        /** Sets hours left to deadline */
        model.put("hoursLeft", getHoursLeft(countdownDifference));
        /** Sets days left to deadline */
        model.put("daysLeft", getDaysLeft(countdownDifference));

        /** Loads comments saved on redis */
        List<String> comments = getComments();

        /** If there are no comments, show a custom message */
        if (comments.isEmpty()) {
            comments.add("There are no comments yet");
        }
        model.put("comments", comments);

        /** Renders "wellcome" view using "model" attributes */
        return "wellcome";
    }

    /**
     * Stores the comment "comment" to redis.
     * @param model the attributes for rendering, not null.
     * @param comment comment to store to redis.
     * @return redirect to main page.
     */
    @PostMapping("/newComment") 
    public String newComment(Map<String, Object> model, @RequestParam("comment") String comment) {
        insertComment(comment);
        return "redirect:/";
    }

}
