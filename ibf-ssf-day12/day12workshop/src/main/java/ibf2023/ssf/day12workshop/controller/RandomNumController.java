package ibf2023.ssf.day12workshop.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/random")
public class RandomNumController {
    
    @GetMapping
    public String getRandomNum (Model model) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new SecureRandom();
        for (int i = 0; i < 3; i++) {
            numbers.add(random.nextInt(31));
        }
        model.addAttribute("numbers", numbers);
        return "random"; // name of your Thymeleaf template
    }

    @PostMapping
    public String generateNumbers(@RequestParam("numInput") int numInput, Model model) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new SecureRandom();
        for (int i = 0; i < numInput; i++) {
            numbers.add(random.nextInt(31));
        }
        model.addAttribute("numbers", numbers);
        return "random"; // name of your Thymeleaf template
    }

    // You can also write "produces = MediaType.TEXT_HTML_VALUE" as "produces = text/html"
    // In this case of GetMapping, the "produces..." is optional as Spring will know its HTML
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getRandom(Model model) {
        Random rand = new SecureRandom();
        int value = rand.nextInt(1, 11);
        boolean odd = (value % 2) > 0;

        model.addAttribute("rndNum", value);
        model.addAttribute("odd", odd);

        return "numbers";
    }

    // In this case, you are not returning HTML, so you need to put
    // "produces = MediaType.APPLICATION_JSON_VALUE"
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("{\"random\": 45}");
    }
}
