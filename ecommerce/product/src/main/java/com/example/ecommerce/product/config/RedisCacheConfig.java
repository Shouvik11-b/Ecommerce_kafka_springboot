package com.example.ecommerce.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;


import java.time.Duration;


@Configuration
public class RedisCacheConfig {


    @Value("${app.cache.ttl-seconds:300}")
    private long ttlSeconds;


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer(objectMapper);


        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlSeconds))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));


        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .transactionAware()
                .build();
    }
}
