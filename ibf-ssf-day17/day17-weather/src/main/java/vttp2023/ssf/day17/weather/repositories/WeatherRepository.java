package vttp2023.ssf.day17.weather.repositories;

import java.io.StringReader;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2023.ssf.day17.weather.AppConfig;
import vttp2023.ssf.day17.weather.models.Weather;

@Repository
public class WeatherRepository {

   @Autowired @Qualifier(AppConfig.REDIS_BEAN)
   private RedisTemplate<String, String> template;

   public void cache(String city, Weather weather) {
      String _key = normalizeKey(city);
      String _value = toJson(weather).toString();
      ValueOperations<String, String> ops = template.opsForValue();
      // Cache for 30 mins
      ops.set(_key, _value, Duration.ofMinutes(30));
   }

   public void cache(String city, List<Weather> weather) {
      String _key = normalizeKey(city);
      JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
      for (Weather w: weather)
         arrBuilder.add(toJson(w));
      ValueOperations<String, String> ops = template.opsForValue();
      // Cache for 30 mins
      ops.set(_key, arrBuilder.build().toString(), Duration.ofMinutes(30));
   }

   public Optional<List<Weather>> get(String city) {
      ValueOperations<String, String> ops = template.opsForValue();
      String _key = normalizeKey(city);
      String _value = ops.get(_key);
      if (null == _value)
         return Optional.empty();
      return Optional.of(toWeather(_value));
   }

   private String normalizeKey(String city) {
      return city.trim().toLowerCase().replaceAll("\\s+", "");
   }

   private List<Weather> toWeather(String str) {
      JsonReader reader = Json.createReader(new StringReader(str));
      JsonArray arr = reader.readArray();
      List<Weather> weather = new LinkedList<>();
      for (int i = 0; i < arr.size(); i++)
         weather.add(toWeather(arr.get(i).asJsonObject()));
      return weather;
   }

   private Weather toWeather(JsonObject j) {
      return new Weather(j.getString("main"), j.getString("description"), j.getString("icon"));
   }

   private JsonObject toJson(Weather weather) {
      return Json.createObjectBuilder()
            .add("main", weather.main())
            .add("description", weather.description())
            .add("icon", weather.icon())
            .build();

   }
   
}
