package com.fanqiao.secondkill.rabbitmq;

import com.fanqiao.secondkill.redis.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * RabbitMQ的Direct模式
     */
    public void send(Object message) {
        String stringMessage = RedisService.beanToString(message);
        log.info("send {}", stringMessage);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, stringMessage);
    }

    /**
     * RabbitMQ的Topic模式
     */
    public void sendTopic(Object message) {
        String stringMessage = RedisService.beanToString(message);
        log.info("sendTopic {}", stringMessage);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.TOPIC_KEY1, stringMessage + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.TOPIC_KEY2, stringMessage + "2");
    }

    /**
     * RabbitMQ的fanout模式
     */
    public void sendFanout(Object message) {
        String stringMessage = RedisService.beanToString(message);
        log.info("sendFanout {}", stringMessage);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", stringMessage);
    }

    /**
     * RabbitMQ的Header模式  消息必须带上head的匹配模式才能发给队列
     */
    public void sendHeaders(Object message) {
        String stringMessage = RedisService.beanToString(message);
        log.info("sendHeaders {}", stringMessage);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("header1", "value1");
        messageProperties.setHeader("header2", "value2");
        Message msg =  new Message(stringMessage.getBytes(), messageProperties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", msg);
    }
}
