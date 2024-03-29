package ibf2023.ssf.day11workshop.controllers;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path={"/", "/index.html"})
public class RandomNumController {

    // Inside this controller, never ever create fields here. You should create the 
    // the fields under a separate object and use Autowiring to get the values to
    // put into your methods here. 
    // Random random = new SecureRandom();  <--- SO NEVER DO THIS. PUT INSIDE the method
    
    @GetMapping
    public String getRandomNum (Model model) {
        Random random = new SecureRandom();
        int randNum = random.nextInt(31);
        model.addAttribute("randomnum", randNum);
        return "index";
    }

    // Alternative method
    // @GetMapping
    // public String getIndex(Model model) {
    //     Random rand = new SecureRandom();
    //     int n = rand.nextInt(31);

    //     String imgUrl = "/numbers/number%d.jpg".formatted(n);
    //     model.addAttribute("imgUrl", imgUrl);

    //     return "number";
    // }
}
