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
@WebMvcTest(PrimeNumber.class)
public class PrimeNumberTest {

    @Autowired
    private PrimeNumber controller;


    /**
     * Check that NIP is null
     */
    @Test
    public void testPrimeNumber() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.prime(-1, map);
        assertThat(view, is("prime"));
    }

    /**
     * Check that NIP is a prime number
     */
    @Test
    public void testNip_is_PrimeNumber() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.prime(13, map);
        assertThat(view, is("prime2"));
        assertThat(map.containsKey("msg"), is(true));
        assertThat(map.get("msg"), is("Your nip 13 is a prime number"));
    }

    /**
     * Check that NIP is not a prime number
     */
    @Test
    public void testNip_isNot_PrimeNumber() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.prime(222222, map);
        assertThat(view, is("prime2"));
        assertThat(map.containsKey("msg"), is(true));
        assertThat(map.get("msg"), is("Your nip 222222 is not a prime number"));
    }

    /**
     * Check that the multiples of NIP are correct
     */
    @Test
    public void testNip_multiples_PrimeNumber() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.prime(6, map);
        assertThat(view, is("prime2"));
        assertThat(map.containsKey("primeFactors"), is(true));
        assertThat(map.get("primeFactors"), is("2, 3"));
    }

}
