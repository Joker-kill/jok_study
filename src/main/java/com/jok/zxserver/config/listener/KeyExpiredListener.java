package com.jok.zxserver.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Author JOKER
 * create time 2024/8/26 13:51
 */

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        log.info("过期的key：" + expireKey);
        //在这里做过期key的处理
    }

}
