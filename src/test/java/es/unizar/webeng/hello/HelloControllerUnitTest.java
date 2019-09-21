package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerUnitTest {

    /**
     * Sets message value from properties
     * or default string if not defined
     */
    @Value("${app.message:Hello World}")
    private String message;

    @Autowired
    private HelloController controller;


    /** 
     * Checks message to be displayed
     */
    @Test
    public void testMessage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if message exists */
        assertThat(map.containsKey("message"), is(true));
        /** Checks if message matches expected value */
        assertThat(map.get("message"), is(message));
    }
    
    /** 
     * Checks time to be displayed
     */
    @Test
    public void testTime() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if time exists */
        assertThat(map.containsKey("time"), is(true));
        /** Checks if time is before current time */
        assertThat((Date)map.get("time"), lessThanOrEqualTo(new Date()));
    }
    
    /** 
     * Checks server system information
     */
    @Test
    public void testSystem() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if data exists and is not null */
        assertThat(map.containsKey("os"), is(true));
        assertNotNull(map.get("os"));
        assertThat(map.containsKey("version"), is(true));
        assertNotNull(map.get("version"));
        assertThat(map.containsKey("hostname"), is(true));
        assertNotNull(map.get("hostname"));
    }
    
    /** 
     * Checks client information
     */
    @Test
    public void testClient() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if data exists and is valid */
        assertThat(map.containsKey("last_ip"), is(true));
        assertNotNull(map.get("last_ip"));
        assertThat(map.containsKey("last_time"), is(true));
        assertThat((Date)map.get("last_time"), lessThanOrEqualTo(new Date()));
    }

    @Test
    public void testCount() throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /**Checks if data exist and is valid */
        assertThat(map.containsKey("visitorCount"), is(true));
        assertNotNull(map.get("visitorCount"));
        assertTrue("The value of visitor count must be greater than 0", (int)map.get("visitorCount") > 0);
    }
}
