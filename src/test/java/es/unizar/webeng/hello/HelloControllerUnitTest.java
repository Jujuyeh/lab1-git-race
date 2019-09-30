package es.unizar.webeng.hello;

import org.hamcrest.core.IsInstanceOf;
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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerUnitTest {

    /**
     * Sets message value from properties or default string if not defined
     */
    @Value("${app.message:Hello World}")
    private String message;

    @Autowired
    private HelloController controller;

    Logger logger = LoggerFactory.getLogger(HelloControllerUnitTest.class);

    private Date expectedDeadline = new GregorianCalendar(2019, Calendar.OCTOBER, 25, 23, 59, 59).getTime();

    /*
     * Checks message to be displayed
     */
    @Test
    public void testMessage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
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
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if time exists */
        assertThat(map.containsKey("time"), is(true));
        /** Checks if time is before current time */
        assertThat((Date) map.get("time"), lessThanOrEqualTo(new Date()));
    }

    /**
     * Checks server system information
     */
    @Test
    public void testSystem() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
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
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if data exists and is valid */
        assertThat(map.containsKey("last_ip"), is(true));
        assertNotNull(map.get("last_ip"));
        assertThat(map.containsKey("last_time"), is(true));
        assertThat((Date) map.get("last_time"), lessThanOrEqualTo(new Date()));
    }

    /**
     * Checks deadline to be displayed
     */
    @Test
    public void testDeadline() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        try {
            /** Checks if deadline exists and its value has expected type */
            assertEquals(Date.class, map.get("deadline").getClass());
            Date deadlineLocal = (Date) map.get("deadline");
            /** Checks if deadline has expected value */
            assertEquals(expectedDeadline, deadlineLocal);
        } catch (NullPointerException e) {
            logger.debug("Deadline is missing");
            fail("Deadline is missing");
        }
    }

    /**
     * Checks countdown values
     */
    @Test
    public void testCountdown() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if deadline exists and its value has expected type */
        assertEquals(Date.class, map.get("deadline").getClass());
        Date deadlineLocal = (Date) map.get("deadline");
        Date last_timeLocal = (Date) map.get("last_time");
        /** Checks if real time is before deadline */
        assertTrue(last_timeLocal.getTime() <= deadlineLocal.getTime());
        /** Checks if secondsLeft exists and its value has expected type */
        assertEquals(Long.class, map.get("secondsLeft").getClass());
        long secondsLeftLocal = (long) map.get("secondsLeft");
        /** Checks if secondsLeft has a valid value */
        assertTrue("secondsLeft must less or equal to 60", secondsLeftLocal <= 60L);
        assertTrue("secondsLeft must be at least 0", secondsLeftLocal >= 0L);
        /** Checks if minutesLeft exists and its value has expected type */
        assertEquals(Long.class, map.get("minutesLeft").getClass());
        long minutesLeftLocal = (long) map.get("minutesLeft");
        /** Checks if minutesLeft has a valid value */
        assertTrue("minutesLeft must less or equal to 60", minutesLeftLocal <= 60L);
        assertTrue("minutesLeft must be at least 0", minutesLeftLocal >= 0L);
        /** Checks if hoursLeft exists and its value has expected type */
        assertEquals(Long.class, map.get("hoursLeft").getClass());
        long hoursLeftLocal = (long) map.get("hoursLeft");
        /** Checks if hoursLeft has a valid value */
        assertTrue("hoursLeft must less or equal to 24", hoursLeftLocal <= 24L);
        assertTrue("hoursLeft must be at least 0", hoursLeftLocal >= 0L);
        /** Checks if daysLeft exists and its value has expected type */
        assertEquals(Long.class, map.get("daysLeft").getClass());
        long daysLeftLocal = (long) map.get("daysLeft");
        /** Checks if daysLeft has a valid value */
        assertTrue("daysLeft must be at least 0", daysLeftLocal >= 0L);
    }

    @Test
    public void testCount() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if data exist and is valid */
        assertThat(map.containsKey("visitorCount"), is(true));
        assertNotNull(map.get("visitorCount"));
        assertTrue("The value of visitor count must be greater than 0", (int) map.get("visitorCount") > 0);
    }

    /**
     * Checks GitHub information
     */
    @Test
    public void testGitHub() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(null, map, "");
        /** Checks if controller uses "wellcome" view */
        assertThat(view, is("wellcome"));
        /** Checks if the message exists and is valid */
        assertThat(map.containsKey("commitMessage"), is(true));
        assertNotNull(map.get("commitMessage"));
        /** Checks if the date exists and is valid */
        assertThat(map.containsKey("commitDate"), is(true));
        assertNotNull(map.get("commitDate"));
        try {
            Instant timestamp = Instant.parse((String) map.get("commitDate"));
            assertThat(timestamp, lessThanOrEqualTo(Instant.now()));
        } catch (Exception e) {
            assertThat((String) map.get("commitDate"), is("-"));
        }

    }
}
