/*
 * File: IDLetter
 * Author: Daniel Revillo Rey
 * Last edit: 16/10/2019
 * Comment: 
 * 	Sources are prime.jsp 
 */

package es.unizar.webeng.hello;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IDLetter {

    /**
     * Application that discovers your ID's letter (only in Spain)
     * 
     * @param ID  ID of user
     * @param model the attributes (map) for the views, not null
     * 
     */
    @GetMapping("/letter")
    public String letter(@RequestParam(value = "ID", defaultValue = "0") int 		ID, Map<String, Object> model) {
        if(ID != 0){
            //calculate the ID's letter
            char letter = calculateLetter(ID);
   
	    /* Show the calculated letter */
            model.put("msgID", "Your ID's letter is " + letter +", right?");
        }
	/* Reload the page with the variable 'msgID' filled with the right data */
	return "letter";
    }

    /**
     * Function that returns your ID's letter (only in Spain)
     * 
     * @param ID  ID of user
     * @return the letter 
     */
    public char calculateLetter(int ID)
    {
    	String characters="TRWAGMYFPDXBNJZSQVHLCKE";
    	int module= ID % 23;
    	char character = characters.charAt(module);
    	return character;
    } 

} /*End of IDLetter class*/