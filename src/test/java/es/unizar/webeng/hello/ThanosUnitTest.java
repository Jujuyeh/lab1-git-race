package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(Thanos.class)
public class ThanosUnitTest {

    @Autowired
    private Thanos controller;


    // Test for thanos app (form)
    @Test
    public void testThanos() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.play(null, map);
        assertThat(view, is("thanos"));
    }

    // Test for thanos app (response alive)
    @Test
    public void testThanos_res_alive() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.play("true", map);
        assertThat(view, is("thanos_res"));
        assertThat(map.containsKey("msg2"), is(true));
        assertThat(map.get("msg2"), is("Congratulations!!"));
    }

    // Test for thanos app (response dead)
    @Test
    public void testThanos_res_dead() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.play("false", map);
        assertThat(view, is("thanos_res"));
        assertThat(map.containsKey("msg2"), is(true));
        assertThat(map.get("msg2"), is("You would dead :("));
    }
}
