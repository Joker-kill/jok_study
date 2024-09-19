package com.jok.zxserver.config;

import com.jok.zxserver.config.listener.KeyExpiredListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author JOKER
 * create time 2024/8/23 14:20
 */

@Configuration
public class RedisConfig {

    // 注入连接工厂类
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        //设置redis单机远程服务地址
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1",
                6379);

        // 设置连接密码等
        // standaloneConfiguration.setPassword();....
        //  return new JedisConnectionFactory(standaloneConfiguration);

        // 连接池相关配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(jedisPoolConfig)
                .build();
        return new JedisConnectionFactory(standaloneConfiguration,clientConfiguration);
    }

    /**
     * 配置redis模板类
     * @param redisConnectionFactory
     * @return
     */

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer()); // 设置序列化器

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(this.redisConnectionFactory());

        return redisMessageListenerContainer;
    }

    @Bean
    public KeyExpiredListener keyExpiredListener() {
        return new KeyExpiredListener(this.redisMessageListenerContainer());
    }

}
