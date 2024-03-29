package vttp2023.ssf.day17.weather.services;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2023.ssf.day17.weather.models.Weather;
import vttp2023.ssf.day17.weather.repositories.WeatherRepository;

@Service
public class WeatherService {

   public static final String URL = "https://api.openweathermap.org/data/2.5/weather";

   @Value("${openweathermap.key}")
   private String appId;

   @PostConstruct
   public void setup() {
      // called after creating the bean
   }

   @PreDestroy
   public void cleanup() {
      // called before deleting the bean
   }

   @Autowired
   private WeatherRepository weatherRepo;

   public List<Weather> getWeatherForCity(String city) {

      Optional<List<Weather>> opt = weatherRepo.get(city);
      if (opt.isPresent()) {
            System.out.printf(">>>>> %s in cache\n", city);
            return opt.get();
      }

      System.out.printf(">>>>> %s not in cache\n", city);

      String url = UriComponentsBuilder
            .fromUriString(URL)
            .queryParam("q", city.replaceAll(" ", "+"))
            .queryParam("appid", appId)
            .queryParam("units", "metric")
            .toUriString();

      RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

      ResponseEntity<String> resp;
      try {
         RestTemplate template = new RestTemplate();
         resp = template.exchange(req, String.class);
      } catch (Exception ex) {
         ex.printStackTrace();
         return List.of();
      }

      JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
      JsonObject data = reader.readObject();
      List<Weather> weather = data.getJsonArray("weather").stream()
            .map(value -> value.asJsonObject())
            .map(j -> 
                  new Weather(j.getString("main"), j.getString("description"), j.getString("icon")))
            .toList();

      weatherRepo.cache(city, weather);

      return weather;
   }
   
}
