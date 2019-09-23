package es.unizar.webeng.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
    public String prime(@RequestParam(value = "nip", required=false) String nip1, Map<String, Object> model) {
        if(nip1 != null && !nip1.isEmpty()){
            //nip is not null and convert to integer
            int nip = Integer.parseInt(nip1);
            //calculate if nip is a prime number
            Boolean esPrimoActual = true;
            if(nip%2==0){
                esPrimoActual = false;
            }
            else{ 
                for(int i=3; i*i<=nip; i+=2){
                    if(nip%i==0){
                        esPrimoActual = false;
                        break;
                    }
                }
            }

            if (esPrimoActual){
                // Prime
                model.put("msg", "Your nip " + nip +" is a prime number");
                model.put("multiplos", "1, " + nip);
            }else {
                // Not Prime

                //calculate multiples of nip
                String multiplos="1";
                for (int i=2;i<=nip;i++){
                    if (nip%i==0){
                        multiplos = multiplos + ", " + i;
                    }
                }
                model.put("msg", "Your nip " + nip +" is not a prime number");
                model.put("multiplos", multiplos);
            }
            return "prime2";
        }
        else{
            return "prime";
        }
    }
}
