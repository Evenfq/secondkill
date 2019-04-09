package com.fanqiao.secondkill.rabbitmq;

import com.fanqiao.secondkill.redis.RedisService;
import com.rabbitmq.client.AMQP;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     *
     * RabbitMQ的Direct模式
     */
    public void send(Object message) {
        String stringMessage = RedisService.beanToString(message);
        log.info("send {}", stringMessage);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, stringMessage);
    }
}
