package cn.lixingyu.springmybatisthymeleaf.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Rlxy93
 * @time 2019/10/20 15:08
 */
@Configuration
public class Config {

    @Bean("myRedisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(json);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    //防止出现Could not read JSON: Unrecognized field "valid" (class org.apache.shiro.session.mgt.SimpleSession), not marked as ignorable
    @Bean("myRedisSessionTemplate")
    public RedisTemplate<Object, Object> redisSessionTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(json);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        //设置默认超过期时间是30秒
        defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
        //初始化RedisCacheManager
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
