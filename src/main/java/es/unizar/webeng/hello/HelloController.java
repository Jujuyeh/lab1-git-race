package es.unizar.webeng.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.lang.IllegalStateException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.atomic.AtomicInteger;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.github.Coordinates;
import com.jcabi.github.RepoCommits;
import com.jcabi.github.RepoCommit;
import com.jcabi.github.Repo;
import javax.json.JsonObject;
import java.util.Iterator;
import java.util.HashMap;


@Controller
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * Sets message value from properties, or default string if not defined.
     */
    @Value("${app.message:Hello World}")
    private String message;
    private Date last_time = new Date();
    private Date deadline = new GregorianCalendar(2019, Calendar.OCTOBER, 25, 23, 59, 59).getTime();
    private String last_ip = "0.0.0.0";

    private String userAgent;
    private LinkedList<RegisterIP> listaIps = new LinkedList<RegisterIP>();



    @Value("${app.visitorCount}")
    private int visitorCount;

    @Autowired
    private HttpServletRequest request;

    private long getSecondsLeft(long difference) {
        return difference / 1000 % 60;
    }

    private long getMinutesLeft(long difference) {
        return difference / (1000 * 60) % 60;
    }

    private long getHoursLeft(long difference) {
        return difference / (1000 * 60 * 60) % 24;
    }

    private long getDaysLeft(long difference) {
        return difference / (1000 * 60 * 60 * 24);
    }

    /**
     * @see https://github.jcabi.com/
     */
    private void getGitHubInfo(Map<String, Object> model) {
        /**
         * There is a rate limit of up to 60 requests per hour, an exception is risen in
         * case this limit is exceeded.
         * 
         * @see https://developer.github.com/v3/#rate-limiting
         */
        try {
            final Github github = new RtGithub();
            Repo repo = github.repos().get(new Coordinates.Simple("UNIZAR-30246-WebEngineering/lab1-git-race"));
            RepoCommits commits = repo.commits();
            Map<String, String> params = new HashMap<String, String>();
            Iterator<RepoCommit> it = commits.iterate(params).iterator();
            RepoCommit lastCommit = it.next();
            RepoCommit.Smart smartRepo = new RepoCommit.Smart(lastCommit);
            String message = smartRepo.message();
            JsonObject json = smartRepo.json();
            json = json.getJsonObject("commit");
            json = json.getJsonObject("author");
            String date = json.getString("date");
            model.put("commitMessage", message);
            model.put("commitDate", date);
        } catch (Throwable t) {
            model.put("commitMessage", "-");
            model.put("commitDate", "-");
        }
    }

    /**
     * Application welcome page.
     *
     * @param model the attributes for rendering, not null
     * @return the view name
     */

    //Function to track the user
    void tracking(HttpServletResponse response, Map<String, Object> model, String yay) {

        try {
            if (request != null) {
                userAgent = request.getHeader("User-Agent");
                model.put("uagent", userAgent);
            }
        } catch (IllegalStateException e) {
            /** For unit tests */
            logger.debug("Request object is not valid");
        }

        if (yay == null) {
            model.put("color", "red");
            model.put("status", "New user");
            // create a cookie
            Cookie cookie = new Cookie("yay", "Welcome");

            // add cookie to response
            response.addCookie(cookie);
        } else {
            model.put("color", "green");
            model.put("status", "Previous user");

        }

    }

    @GetMapping("/")
    public String welcome(HttpServletResponse response, Map<String, Object> model,
            @CookieValue(value = "yay", required = false) String yay) {
        /** Sets "os" and "version" from system information */
        model.put("os", System.getProperty("os.name"));
        model.put("version", System.getProperty("os.version"));
        /**
         * @see https://stackoverflow.com/questions/7883542/getting-the-computer-name-in-java
         */
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            /** Sets "hostname" attribute to server machine name */
            model.put("hostname", addr.getHostName());
			
			/** Control of number of visits for each user **/
            int yourVisits = monitorizeNumberOfVisits(addr);
			model.put("yourVisits", yourVisits);
			
        } catch (UnknownHostException ex) {
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
            /**
             * @see https://www.mkyong.com/spring-mvc/spring-mvc-how-to-get-client-ip-address/
             */
            String remoteAddr = "";
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
            last_ip = remoteAddr;
        } catch (IllegalStateException e) {
            /** For unit tests */
            logger.debug("Request object is not valid");
        }
        model.put("message", message);

        /* It adds one to the visits of the web */
        synchronized (this) { // We do it in an atomic way to avoid race conditions.
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
        /** Renders "wellcome" view using "model" attributes */
      
        getGitHubInfo(model);

        tracking(response, model, yay);

        return "wellcome";

    }

    /**
     * Application jokes page.
     *
     * @param model  the attributes for rendering, not null
     * @return the view name
     */
    @GetMapping("/JavaJoke")
    public String javaJoke(Map<String, Object> model) {
        /** Get the day of the current date */
        /** @see https://www.baeldung.com/java-year-month-day */
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        /** Sets the "intro" */
        model.put("intro", "Java joke of the day: ");

        /** If the day is even, the first joke is shown */
        if (calendar.get(Calendar.DAY_OF_MONTH) % 2 == 0) {
            model.put("joke", "Why are communists bad Java programmers? Because they don't like classes.");
        }
        else {
            model.put("joke", "I made a Java program to tell me my purpose. It keeps saying null pointer exception, so it works great.");
        }

        /** Renders "joke" view using "model" attributes */
        return "joke";
    }
	
	
	 /** Atomic way in order to avoid race conditions **/
	private synchronized int monitorizeNumberOfVisits(InetAddress addr) {
		
		 RegisterIP r = new RegisterIP();
         r.setDirection(addr);
         
		 int yourVisits;
         int index = containsRegisterIP(r);
        
         if (index == -1) {
         	r.setNumberVisits(1);
         	listaIps.addLast(r);
         	yourVisits = 1;
         }
         else {
         	r = listaIps.get(index);
         	int times = r.getNumberVisits() + 1;
         	yourVisits = times;
         	r.setNumberVisits(times);
         	listaIps.add(index, r);
         }
		 return yourVisits;
	}

	
	private int containsRegisterIP(RegisterIP r) {
		for (int i = 0; i < listaIps.size(); i++) {
			if (listaIps.get(i).equals(r)) {
				return i;
			}
		}
		return -1;
	}
}
