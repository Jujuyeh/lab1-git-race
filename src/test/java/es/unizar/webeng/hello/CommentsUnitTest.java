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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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
     * Checks the returned values when there is no comments.
     */
    @Test
    public void testGetNoComments() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment", is("There are no comments yet")))
                .andExpect(jsonPath("$[0].name", is("admin")))
                .andExpect(jsonPath("$[0].date",not(isEmptyString())))
                .andReturn();
    }
    
    /**
     * Checks that the response of the POST method with a valid comment is a
     * "created" code.
     */
    @Test
    public void testWriteComment() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(post("/comments").param("comment", "This is a comment")
                        .param("name", "Someone"))
                        .andExpect(status().isCreated());
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
