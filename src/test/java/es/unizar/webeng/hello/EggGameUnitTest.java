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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EggGame.class)
public class EggGameUnitTest {

    @MockBean
    private ValueOperations<String, String> valueOperations;

    @MockBean
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MockMvc mvc;

    private static final String STRING_NO_ONE = "No one has played";
    private static final String STRING_WON = "You have achieved the best score! :)";
    private static final String STRING_LOST = "You have not exceeded the best score... :(";
    private static final String STRING_BEST = "Best score: ";
    private static final String STRING_USER = ". User -> ";
    private static final String STRING_NO_PARAM = "Click on the egg to play and enter the username before sending.";

    /**
     * Checks the message when no one has played.
     */
    @Test
    public void testNoPlayed() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("eggValueStored")).willReturn(null);
        this.mvc.perform(get("/egg")).andExpect(status().isOk()).andExpect(model().attribute("bestEgg", STRING_NO_ONE));
    }

    /**
     * Checks the message when someone has played.
     */
    @Test
    public void testPlayed() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("eggValueStored")).willReturn("10");
        given(valueOperations.get("usernameStored")).willReturn("User1");
        this.mvc.perform(get("/egg")).andExpect(status().isOk())
                .andExpect(model().attribute("bestEgg", STRING_BEST + "10" + STRING_USER + "User1"));
    }

    /**
     * Checks the message when someone has won.
     */
    @Test
    public void testWon() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("eggValueStored")).willReturn("10");
        given(valueOperations.get("usernameStored")).willReturn("User1");
        this.mvc.perform(post("/egg").param("eggValue", "20").param("username", "User2")).andExpect(status().isOk())
                .andExpect(model().attribute("eggRes", STRING_WON))
                .andExpect(model().attribute("bestEgg", STRING_BEST + "20" + STRING_USER + "User2"));
    }

    /**
     * Checks the message when someone has lost.
     */
    @Test
    public void testLost() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("eggValueStored")).willReturn("10");
        given(valueOperations.get("usernameStored")).willReturn("User1");
        this.mvc.perform(post("/egg").param("eggValue", "5").param("username", "User2")).andExpect(status().isOk())
                .andExpect(model().attribute("eggRes", STRING_LOST))
                .andExpect(model().attribute("bestEgg", STRING_BEST + "10" + STRING_USER + "User1"));
    }

    /**
     * Checks the message if eggValue is missing.
     */
    @Test
    public void testNoParam() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(post("/egg").param("eggValue", "")).andExpect(status().isOk())
                .andExpect(model().attribute("eggRes", STRING_NO_PARAM));
    }

    /**
     * Checks the message if username is missing.
     */
    @Test
    public void testNoUsername() throws Exception {
        given(stringRedisTemplate.opsForValue()).willReturn(valueOperations);
        this.mvc.perform(post("/egg").param("username", "")).andExpect(status().isOk())
                .andExpect(model().attribute("eggRes", STRING_NO_PARAM));
    }

}
