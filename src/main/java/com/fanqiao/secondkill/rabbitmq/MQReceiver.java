package com.fanqiao.secondkill.rabbitmq;

import com.fanqiao.secondkill.entity.OrderInfo;
import com.fanqiao.secondkill.entity.SecondkillOrder;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.result.CodeMessage;
import com.fanqiao.secondkill.result.Result;
import com.fanqiao.secondkill.service.GoodsService;
import com.fanqiao.secondkill.service.OrderService;
import com.fanqiao.secondkill.service.SecondkillService;
import com.fanqiao.secondkill.vo.GoodsVo;
import com.fanqiao.secondkill.vo.SecondkillMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecondkillService secondkillService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopicQueue1(String message) {
        log.info("receiveTopicQueue1 {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopicQueue2(String message) {
        log.info("receiveTopicQueue2 {}", message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeadersQueue(byte[] message) {
        log.info("receiveHeadersQueue {}", new String(message));
    }

    @RabbitListener(queues = MQConfig.SECONDKILL_QUEUE)
    public Result receiveSecondkillQueue(String message) {
        log.info("receiveSecondkillQueue {}", message);
        SecondkillMessage secondkillMessage = RedisService.stringToBean(message, SecondkillMessage.class);
        Long goodsId = secondkillMessage.getGoodsId();
        SecondkillUser user = secondkillMessage.getSecondkillUser();
         //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.message(CodeMessage.REPERTORY_EMPTY);
        }
        //判断是否已经秒杀到了
        SecondkillOrder order = orderService.getSecondkillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.message(CodeMessage.SECONDKILL_REPEAT);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = secondkillService.miaosha(user, goods);
        return Result.success(orderInfo);
    }

}
