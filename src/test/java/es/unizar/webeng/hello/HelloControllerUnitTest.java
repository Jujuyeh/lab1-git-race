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

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerUnitTest {

    // Sets message value from properties
    // or default string if not defined
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
        // Checks if controller uses "wellcome" view
        assertThat(view, is("wellcome"));
        // Checks if message exists
        assertThat(map.containsKey("message"), is(true));
        // Checks if message matches expected value
        assertThat(map.get("message"), is(message));
    }
    
    /** 
     * Checks time to be displayed
     */
    @Test
    public void testTime() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        // Checks if controller uses "wellcome" view
        assertThat(view, is("wellcome"));
        // Checks if time exists
        assertThat(map.containsKey("time"), is(true));
        // Checks if time is before current time
        assertThat((Date)map.get("time"), lessThanOrEqualTo(new Date()));
    }
}
