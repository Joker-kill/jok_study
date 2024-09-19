package com.jok.zxserver.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.jok.zxserver.config.handlers.BooKingStatusTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JOKER
 * create time 2024/8/13 14:03
 */

@Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer(){
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(BooKingStatusTypeHandler.class);
        };
    }
}
