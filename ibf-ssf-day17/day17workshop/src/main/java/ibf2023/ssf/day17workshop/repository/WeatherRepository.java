package ibf2023.ssf.day17workshop.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2023.ssf.day17workshop.model.Weather;

@Repository
public class WeatherRepository {

    @Autowired
    private RedisTemplate<String, List<Weather>> redisTemplate;

    @SuppressWarnings("null")
    public List<Weather> getWeatherFromCache(String cityName) {
        cityName = normalizeKey(cityName);
        return redisTemplate.opsForValue().get(cityName);
    }

    @SuppressWarnings("null")
    public void cacheWeather(String cityName, List<Weather> weatherList, long expirationTime, TimeUnit timeUnit) {
        cityName = normalizeKey(cityName);
        redisTemplate.opsForValue().set(cityName, weatherList);
        redisTemplate.expire(cityName, expirationTime, timeUnit);
    }

    private String normalizeKey(String city) {
        return city.trim().toLowerCase().replaceAll("\\s+", "");
    }
}
