package ibf2023.ssf.day11.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// GET /time

@Controller
@RequestMapping(path = "/time")
public class TimeController {

    @GetMapping
    public String getTime(Model model) {
        System.out.println(">>> GET /time");

        String currTime = (new Date()).toString();

        model.addAttribute("time", currTime);
        return "time";
    }


    @GetMapping(path={"dayofweek", "/dow"}) //means either..or can still access the page
    public String getDayOfWeek(Model model) {
        System.out.println(">>> GET /time/dow");

        DateFormat formatter = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayOfWeek = formatter.format(date);

        model.addAttribute("dow", dayOfWeek);
        return "dayofweek";
    }
}
