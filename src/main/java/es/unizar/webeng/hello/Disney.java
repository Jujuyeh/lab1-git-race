package es.unizar.webeng.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.common.hash.Hashing;

@Controller
public class Disney {

    static int answersGiven = 0;
    static int score = 0;
    /**
     *  
     * @param ans  number of the answer checked, no required
     * @param model  the attributes (map) for the views, not null
     * @return the view
     */
    @GetMapping("/disney")
    public String quiz(@RequestParam(value = "answer", required=false) Integer ans, Map<String, Object> model) {
        
        if (ans != null && ans > 0) {
            score = score + ans;
            answersGiven++;
            if (answersGiven > 3) {//The quiz has ended, and the result is given.
                switch (score % 4) {
                    case 0:
                        model.put("name", "Elsa");
                        model.put("img", "https://lumiere-a.akamaihd.net/v1/images/6d7454cea6644379adc7e529c5790a28078a2823.jpeg");
                        break;
                    case 1:
                        model.put("name", "Musu");
                        model.put("img", "http://images2.fanpop.com/image/polls/389000/389788_1267581962077_full.jpg");
                        break;
                    case 2:
                        model.put("name", "Simba");
                        model.put("img", "https://vignette.wikia.nocookie.net/lionking/images/9/95/Simba.png/revision/latest?cb=20190517162940"); 
                        break;
                    case 3:
                        model.put("name", "Pocahontas");
                        model.put("img", "https://www.syfy.com/sites/syfy/files/styles/1200x680/public/2018/04/pocahontas_main_image.jpg"); 
                        break;
                    default:
                        break;
                }
                answersGiven = 0;
                return "disney_char";
            }else{
                model.put("question", answersGiven);
                return "disney_test";
            }
        } else {
            answersGiven = 0;
            model.put("question", answersGiven);
            //There are no answers, it returns the test page
            return "disney_test";
        }
    }
}
