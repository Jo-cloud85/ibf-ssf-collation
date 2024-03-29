package ibf2023.ssf.day17workshop.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import ibf2023.ssf.day17workshop.model.Weather;
import ibf2023.ssf.day17workshop.utils.Util;

/* We need to configure Redis this way because Spring Boot does not know how to. */

@Configuration
public class RedisConfig {
    
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUser;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @SuppressWarnings("null")
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        
        if (!"NOT_SET".equals(redisUser.trim())) {
            redisConfig.setUsername(redisUser);
            redisConfig.setPassword(redisPassword);
        }

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnFac = new JedisConnectionFactory(redisConfig, jedisClient);

        jedisConnFac.afterPropertiesSet();

        return jedisConnFac;
    }

    /* By default there is a RedisTemplate<String, String> provided by Spring Boot once we have Redis */

    @Bean(Util.REDIS_ONE) // Factory method
    public RedisTemplate<String, List<Weather>> redisObjectTemplate(){
        // Create the template with the client
        RedisTemplate<String, List<Weather>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }
}
