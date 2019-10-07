package es.unizar.webeng.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import java.util.stream.IntStream;

import java.util.stream.Collectors;

@Controller
public class PrimeNumber {

    /**
     * Application for know if your nip is a prime number and its multiples
     * 
     * @param nip  nip of student
     * @param model  the attributes (map) for the views, not null
     * @return the view
     */
    @GetMapping("/prime")
    public String prime(@RequestParam(value = "nip", defaultValue = "-1") int nip, Map<String, Object> model) {
        if(nip != -1){
            //calculate if nip is a prime number
            Boolean esPrimoActual = isPrime(nip);
            //calculate prime factors of nip
            String primeFactors = primeFactors(nip).mapToObj(Integer::toString).collect(Collectors.joining(", "));
            model.put("primeFactors", primeFactors);
            if (esPrimoActual){
                // Prime
                model.put("msg", "Your nip " + nip +" is a prime number");
            }else {
                // Not Prime
                model.put("msg", "Your nip " + nip +" is not a prime number");
            }
            return "prime2";
        }
        else{
            return "prime";
        }
    }

    /**
     * Method that calculate if number is prime
     * 
     * @param n  number
     * @return boolean
     */
    public static boolean isPrime(int number) {
        return !IntStream.rangeClosed(2, number/2).anyMatch(i -> number%i == 0); 
    }

    /**
     * Method that calculate prime factors of one number
     * 
     * @param n  number
     * @return IntStream
     */
    public static IntStream primeFactors(int n) {
        return IntStream.range(2, n-1)
            .filter(i -> n % i == 0 && 
                    !primeFactors(i).findAny().isPresent()); // or primeFactors(i).count() == 0
    }
}
