package ibf2023.ssf.day17workshop.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2023.ssf.day17workshop.model.Weather;
import ibf2023.ssf.day17workshop.repository.WeatherRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {

    public static final String SEARCH_URL="https://api.openweathermap.org/data/2.5/weather";

    @Autowired
    private WeatherRepository weatherRepo;

    @Value("${openweathermap.key}")
    private String apiKey;

    public JsonReader getJsonReader(String cityName) {
        String unit = "metric";

        String url = UriComponentsBuilder
                        .fromUriString(SEARCH_URL)
                        .queryParam("q", cityName)
                        .queryParam("units", unit)
                        .queryParam("appid", apiKey)
                        .toUriString();

        System.out.printf(">>>>> url %s\n", url);

        // Make the GET request
        RequestEntity<Void> req = RequestEntity.get(url).build();

        // Make the call
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        System.out.printf("Status code: %d\n", resp.getStatusCode().value());
        System.out.printf("Payload: %s\n", resp.getBody());

        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));

        return reader;
    }

    @SuppressWarnings("null")
    public List<Weather> getWeather(String cityName) {

        // Check if the data is cached in Redis
        List<Weather> cachedWeather = weatherRepo.getWeatherFromCache(cityName);
        if (cachedWeather != null) {
            System.out.println("Fetching weather data from Redis cache for city: " + cityName);
            return cachedWeather;
        } else {
            System.out.println("Fetching weather data from OpenWeatherMap API for city: " + cityName);

            List<Weather> weatherlist = new LinkedList<>();
        
            JsonArray weatherData;

            try {
                JsonReader reader = getJsonReader(cityName);
                JsonObject weatherResp = reader.readObject();
                weatherData = weatherResp.getJsonArray("weather");
                for (int i=0; i<weatherData.size(); i++) {
                    JsonObject elem = weatherData.get(i).asJsonObject();
                    String description = elem.getString("description");
                    String icon = elem.getString("icon");
                    Weather weather = new Weather(description, icon);
                    weatherlist.add(weather);
                }
    
                // Cache the data in Redis
                weatherRepo.cacheWeather(cityName, weatherlist, 30, TimeUnit.MINUTES);
                
            } catch (Exception e) {
                System.out.println("Cannot find city");
            }

            return weatherlist;
        }
    }
}