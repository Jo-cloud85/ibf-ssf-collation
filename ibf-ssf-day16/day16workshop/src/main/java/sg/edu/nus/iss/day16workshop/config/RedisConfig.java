package sg.edu.nus.iss.day16workshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.utils.Util;

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

    @Bean(Util.REDIS_ONE) //bean name comes from Util.java
    public RedisTemplate<String, String> redisTemplate() {

        // Create the template with the client
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // Keys are in UTF-8
        template.setKeySerializer(new StringRedisSerializer()); 

        template.setValueSerializer(new StringRedisSerializer());

        // This is when you use Hashmap to store data in your Redis
        template.setHashKeySerializer(new StringRedisSerializer());
        // Optional value serializer if string values are saved as UTF-8
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }

    @Bean(Util.REDIS_TWO)
    public RedisTemplate<String, Game> redisObjectTemplate(){
        // Create the template with the client
        RedisTemplate<String, Game> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // // Keys are in UTF-8
        // template.setKeySerializer(new StringRedisSerializer()); 
        // // Optional value serializer if string values are saved as UTF-8
        // template.setValueSerializer(new StringRedisSerializer()); 

        // // This is when you use Hashmap to store data in your Redis
        // template.setHashKeySerializer(new StringRedisSerializer());
        // template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }
}
