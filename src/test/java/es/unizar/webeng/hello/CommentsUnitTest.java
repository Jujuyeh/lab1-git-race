package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Comments.class)
public class CommentsUnitTest {

    @MockBean
    private ValueOperations<String, String> valueOperations;

    @MockBean
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MockMvc mvc;

    /**
     * Checks the message when there is no comments.
     */
    @Test
    public void testNoComments() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        List<String> comments = new ArrayList<String>();
        comments.add("There are no comments yet");
        this.mvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("comments", comments));
    }
    
    /**
     * Checks that the response of the POST method with a valid comment is a
     * redirect to "/comments".
     */
    @Test
    public void testWriteComment() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(post("/comments").param("comment", "This is a comment")
                        .param("name", "Someone"))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/comments"));
    }

    /**
     * Checks that the response of the POST method with missing parameters is
     * 400 (bad request).
     */
    @Test
    public void testWriteInvalidComment() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(post("/comments")).andExpect(status().isBadRequest());
    }

}
