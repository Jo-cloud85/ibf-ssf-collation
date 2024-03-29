package vttp2023.ssf.day17.weather.controllers;

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

import jakarta.json.Json;
import jakarta.json.JsonObject;

import vttp2023.ssf.day17.weather.models.Weather;
import vttp2023.ssf.day17.weather.services.HttpbinService;
import vttp2023.ssf.day17.weather.services.WeatherService;

@Controller
@RequestMapping
public class WeatherController {

   @Autowired
   private HttpbinService httpbinSvc;

   @Autowired
   private WeatherService weatherSvc;

   @GetMapping(path = "/weather")
   public ModelAndView getWeather(@RequestParam String city) {

      List<Weather> result = weatherSvc.getWeatherForCity(city);

      ModelAndView mav = new ModelAndView("weather");
      mav.addObject("city", city);
      mav.addObject("weather", result);
      mav.setStatus(HttpStatusCode.valueOf(200));

      return mav;
   }

   @GetMapping("/healthz")
   @ResponseBody
   public ResponseEntity<String> getHealthz() {
      JsonObject j = Json.createObjectBuilder()
         .build();
      if (httpbinSvc.isAlive())
         return ResponseEntity.ok(j.toString());

      return ResponseEntity.status(400).body(j.toString());
   }
   
}
