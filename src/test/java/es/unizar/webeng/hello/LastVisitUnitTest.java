package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LastVisit.class)
public class LastVisitUnitTest {

    @MockBean
    private ValueOperations<String, String> valueOperations;

    @MockBean
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MockMvc mvc;

    private static final String FIRST_IP = "10.0.0.1";
    private static final String SECOND_IP = "10.0.0.2";
    private static final String STRING_FIRST_TIME = "This is your first time! Welcome!";
    private static final String STRING_TIME = "Sat Sep 20 01:01:01 UTC 2019";

    /**
     * Check if displayed text is correct for the user's first visit
     */
    @Test
    public void testOneVisit() throws Exception {
        RequestPostProcessor postProcessor = request -> {
            request.setRemoteAddr(FIRST_IP);
            return request;
        };
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(get("/visit")
            .with(postProcessor))
                .andExpect(status().isOk())
                .andExpect(model().attribute("visitTime", STRING_FIRST_TIME));
    }

    /**
     * Check if displayed text is correct if it isn't the user's first visit
     */
    @Test
    public void testTwoVisits() throws Exception {
        RequestPostProcessor postProcessor = request -> {
            request.setRemoteAddr(FIRST_IP);
            return request;
        };
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get(FIRST_IP)).willReturn(STRING_TIME);
        this.mvc.perform(get("/visit")
            .with(postProcessor))
                .andExpect(status().isOk())
                .andExpect(model().attribute("visitTime", STRING_TIME));
    }

    /**
     * Check if requests with different IPs are treated differently from
     * other ones that are already stored
     */
    @Test
    public void testTwoUsers() throws Exception {
        RequestPostProcessor postProcessor = request -> {
            request.setRemoteAddr(FIRST_IP);
            return request;
        };
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get(SECOND_IP)).willReturn(STRING_TIME);
        this.mvc.perform(get("/visit")
            .with(postProcessor))
                .andExpect(status().isOk())
                .andExpect(model().attribute("visitTime", STRING_FIRST_TIME));
    }

}
