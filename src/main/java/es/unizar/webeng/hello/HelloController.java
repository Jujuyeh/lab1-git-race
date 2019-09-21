package es.unizar.webeng.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

import java.lang.IllegalStateException;
import java.lang.NullPointerException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    
    /**
     * Sets message value from properties,
     * or default string if not defined.
     */
    @Value("${app.message:Hello World}")
    private String message;
    private Date last_time = new Date();
    private String last_ip = "0.0.0.0";
    private String userAgent;
    
    @Autowired
    private HttpServletRequest request;

    /**
     * Application welcome page.
     *
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/")
    public String welcome(Map<String, Object> model, @CookieValue(value= "yay", required=false) String yay) {
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
        /** Renders "wellcome" view using "model" attributes */
        
        try {            
            if (request != null) {
            	userAgent = request.getHeader("User-Agent");
            }
        }
        catch (IllegalStateException e) {
            /** For unit tests */
            logger.debug("Request object is not valid");
        }
        
        if (yay == null) {
        	model.put("color", "red");
        }
        else {
        	model.put("color", "green");
        	
        }
  
        return "wellcome";
        
        
    }

}
