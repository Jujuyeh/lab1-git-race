package es.unizar.webeng.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class Paper {

    /**
     * Application for play Rock, Paper, Scissors, Lizard or Spock game.
     * 
     * @param nip  nip of student
     * @param model  the attributes (map) for the views, not null
     * @return the view
     */
    @GetMapping("/paper")
    public String prime(@RequestParam(value = "option", required=false)  String option, Map<String, Object> model) {
            model.put("nip", option);
            if (option != null && !option.isEmpty() && option != "PAPER" && option != "ROCK"&& option !="SCISSORS"&& option != "LIZARD" && option != "SPOCK") {
                int random = (int)(Math.random()*5+1);
                if(random == 1){ //Server -> rock
                    if(option.equals("PAPER")|| option.equals("SPOCK")){
                        model.put("msg", "You win!");
                        model.put("msg2", "Paper >> Rock or Spock");
                        model.put("img", "https://media1.tenor.com/images/0a3e7b49d308b6f4b7a15f348b78378e/tenor.gif?itemid=3556398");
                    }
                    else if(option.equals("ROCK")){
                        model.put("msg", "REPLY!");
                        model.put("msg2", "Rock = Rock");
                        model.put("img", "https://thumbs.gfycat.com/OrdinaryThornyBasenji-size_restricted.gif");
                    }
                    else if(option.equals("SCISSORS") || option.equals("LIZARD")){
                        model.put("msg", "You lose!");
                        model.put("msg2", "Rock >> Scissors or Lizard");
                        model.put("img", "https://thumbs.gfycat.com/DownrightSentimentalBumblebee-size_restricted.gif");
                    }
                    return "paper2";
                }

                else if(random == 2){ //Server -> paper
                    if(option.equals("ROCK")|| option.equals("SPOCK")){
                        model.put("msg", "You Lose!");
                        model.put("msg2", "Paper >> Rock or Spock");
                        model.put("img", "https://thumbs.gfycat.com/DownrightSentimentalBumblebee-size_restricted.gif");
                    }
                    else if(option.equals("PAPER")){
                        model.put("msg", "REPLY!");
                        model.put("msg2", "Paper = Paper");
                        model.put("img", "https://thumbs.gfycat.com/OrdinaryThornyBasenji-size_restricted.gif");
                    }
                    else if(option.equals("SCISSORS")|| option.equals("LIZARD")){
                        model.put("msg", "You win!");
                        model.put("msg2", "Scissors >> Paper or Lizard");
                        model.put("img", "https://media1.tenor.com/images/0a3e7b49d308b6f4b7a15f348b78378e/tenor.gif?itemid=3556398");
                    }
                    return "paper2";
                }

                else if(random == 3){ //Server -> scissors
                    if(option.equals("ROCK")|| option.equals("SPOCK")){
                        model.put("msg", "You win!");
                        model.put("msg2", "Rock or Spock>> Scissors");
                        model.put("img", "https://media1.tenor.com/images/0a3e7b49d308b6f4b7a15f348b78378e/tenor.gif?itemid=3556398");
                    }
                    else if(option.equals("SCISSORS")){
                        model.put("msg", "REPLY!");
                        model.put("msg2", "Scissors = Scissors");
                        model.put("img", "https://thumbs.gfycat.com/OrdinaryThornyBasenji-size_restricted.gif");
                    }
                    else if(option.equals("PAPER")|| option.equals("LIZARD")){
                        model.put("msg", "You lose!");
                        model.put("msg2", "Scissors >> Paper or Lizard");
                        model.put("img", "https://thumbs.gfycat.com/DownrightSentimentalBumblebee-size_restricted.gif");
                    }
                    return "paper2";
                }
                else if(random == 4){ //Server -> lizard
                    if(option.equals("PAPER")|| option.equals("SPOCK")){
                        model.put("msg", "You win!");
                        model.put("msg2", "Paper or Spock>> Lizard");
                        model.put("img", "https://media1.tenor.com/images/0a3e7b49d308b6f4b7a15f348b78378e/tenor.gif?itemid=3556398");
                    }
                    else if(option.equals("LIZARD")){
                        model.put("msg", "REPLY!");
                        model.put("msg2", "Lizard = Lizard");
                        model.put("img", "https://thumbs.gfycat.com/OrdinaryThornyBasenji-size_restricted.gif");
                    }
                    else if(option.equals("SCISSORS")|| option.equals("ROCK")){
                        model.put("msg", "You lose!");
                        model.put("msg2", "Lizard >> Scissors or Rock");
                        model.put("img", "https://thumbs.gfycat.com/DownrightSentimentalBumblebee-size_restricted.gif");
                    }
                    return "paper2";
                }
                else if(random == 5){ //Server -> Spock
                    if(option.equals("ROCK")|| option.equals("SCISSORS")){
                        model.put("msg", "You win!");
                        model.put("msg2", "Rock or Spock>> Scissors");
                        model.put("img", "https://media1.tenor.com/images/0a3e7b49d308b6f4b7a15f348b78378e/tenor.gif?itemid=3556398");
                    }
                    else if(option.equals("SPOCK")){
                        model.put("msg", "REPLY!");
                        model.put("msg2", "Spock = Spock");
                        model.put("img", "https://thumbs.gfycat.com/OrdinaryThornyBasenji-size_restricted.gif");
                    }
                    else if(option.equals("PAPER")|| option.equals("LIZARD")){
                        model.put("msg", "You lose!");
                        model.put("msg2", "Spock >> Paper or Lizard");
                        model.put("img", "https://thumbs.gfycat.com/DownrightSentimentalBumblebee-size_restricted.gif");
                    }
                    return "paper2";
                }
                else {
                    return "paper";
                
                }
            }
            else{
                return "paper";
            }
        }

    }