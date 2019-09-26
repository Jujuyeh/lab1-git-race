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
@WebMvcTest(Disney.class)
public class DisneyTest {

    @Autowired
    private Disney controller;


    //Test the access to the quiz
    @Test
    public void testDisney() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.quiz(null, map);
        assertThat(view, is("disney_test"));
    }

    //Test the correct result of complete the quiz, that should return Elsa as you character
    @Test
    public void test_char_Elsa() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.quiz(1, map);
        assertThat(view, is("disney_test"));
        view = controller.quiz(1, map);
        assertThat(view, is("disney_test"));
        view = controller.quiz(1, map);
        assertThat(view, is("disney_test"));
        view = controller.quiz(1, map);
        assertThat(view, is("disney_char"));
        assertThat(map.get("name"), is("Elsa"));
    }
}
