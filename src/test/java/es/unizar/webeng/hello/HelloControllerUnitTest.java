package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerUnitTest {

    @Value("${app.message:Hello World}")
    private String message;

    @Autowired
    private HelloController controller;


    @Test
    public void testMessage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        assertThat(view, is("wellcome"));
        assertThat(map.containsKey("message"), is(true));
        assertThat(map.get("message"), is(message));
    }

    // Test for thanos app (form)
    @Test
    public void testThanos() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.play(null, map);
        assertThat(view, is("thanos"));
    }

    // Test for thanos app (response)
    @Test
    public void testThanos_res() throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        //Alive
        String view = controller.play("true", map);
        assertThat(view, is("thanos_res"));
        assertThat(map.containsKey("msg2"), is(true));
        assertThat(map.get("msg2"), is("Congratulations!!"));

        //Dead
        view = controller.play("false", map);
        assertThat(view, is("thanos_res"));
        assertThat(map.containsKey("msg2"), is(true));
        assertThat(map.get("msg2"), is("You would dead :("));
    }
}
