package ibf2023.ssf.day17workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ibf2023.ssf.day17workshop.model.Weather;
import ibf2023.ssf.day17workshop.service.HttpbinService;
import ibf2023.ssf.day17workshop.service.WeatherService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private HttpbinService httpbinService;

    @GetMapping("/search")
    public ModelAndView getWeather(@RequestParam String q) {
        List<Weather> city = weatherService.getWeather(q);
        ModelAndView mav = new ModelAndView("result");
        
        mav.addObject("q", q);
        mav.addObject("cities", city);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    // Checking liveness
    @GetMapping("/healthz")
    @ResponseBody
    public ResponseEntity<String> getHealthz() {
        JsonObject j = Json.createObjectBuilder()
                           .build();
        
        if (httpbinService.isAlive()) {
            return ResponseEntity.ok(j.toString());
        }

        return ResponseEntity.status(400)
                             .body(j.toString());
    }
}