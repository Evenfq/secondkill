package com.fanqiao.secondkill.rabbitmq;

import com.fanqiao.secondkill.redis.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive {}", message);
    }
}
