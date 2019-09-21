package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
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

    private Date expectedDeadline = new GregorianCalendar(2019, Calendar.OCTOBER, 25, 23, 59, 59).getTime();


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

    /** 
     * Checks deadline to be displayed
     */
    @Test
    public void testDeadline() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if deadline exists and has expected value */
        assertThat(map.containsKey("deadline"), is(true));
        assertEquals(expectedDeadline, map.get("deadline"));
    }

    /** 
     * Checks countdown values
     */
    @Test
    public void testCountdown() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if deadline exists */
        assertThat(map.containsKey("deadline"), is(true));
        /** Checks if real time is before deadline*/
        if(((Date)map.get("last_time")).getTime() <= ((Date)map.get("deadline")).getTime()){
            /** Checks if secondsLeft exists and is valid */
            assertThat(map.containsKey("secondsLeft"), is(true));
            assertTrue("secondsLeft must less or equal to 60", (long)map.get("secondsLeft") <= ((long)60));
            assertTrue("secondsLeft must be at least 0", (long)map.get("secondsLeft") >= ((long)0));
            /** Checks if minutesLeft exists and is valid */
            assertThat(map.containsKey("minutesLeft"), is(true));
            assertTrue("minutesLeft must less or equal to 60", (long)map.get("minutesLeft") <= ((long)60));
            assertTrue("minutesLeft must be at least 0", (long)map.get("minutesLeft") >= ((long)0));
            /** Checks if hoursLeft exists and is valid */
            assertThat(map.containsKey("hoursLeft"), is(true));
            assertTrue("hoursLeft must less or equal to 24", (long)map.get("hoursLeft") <= ((long)24));
            assertTrue("hoursLeft must be at least 0", (long)map.get("hoursLeft") >= ((long)0));
            /** Checks if daysLeft exists and is valid */
            assertThat(map.containsKey("daysLeft"), is(true));
            assertTrue("daysLeft must be at least 0", (long)map.get("daysLeft") >= ((long)0));
        }
    }
}
