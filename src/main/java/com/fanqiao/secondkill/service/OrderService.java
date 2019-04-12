package com.fanqiao.secondkill.service;

import com.fanqiao.secondkill.dao.OrderInfoDao;
import com.fanqiao.secondkill.entity.OrderInfo;
import com.fanqiao.secondkill.entity.SecondkillOrder;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.OrderKey;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.vo.GoodsVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Log4j2
public class OrderService {
	
	@Autowired
	private OrderInfoDao orderInfoDao;

	@Autowired
	private RedisService redisService;
	
	public SecondkillOrder getSecondkillOrderByUserIdGoodsId(long userId, long goodsId) {
		return redisService.get(OrderKey.getMiaoshaOrderByUidGid, userId + "_" + goodsId, SecondkillOrder.class);
	}

	@Transactional
	public OrderInfo createOrder(SecondkillUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSecondkillPrice());
		orderInfo.setOrderChannel(new Byte("1"));
		orderInfo.setStatus(new Byte("0"));
		orderInfo.setUserId(user.getId());
		orderInfoDao.insert(orderInfo);
		SecondkillOrder secondkillOrder = new SecondkillOrder();
		secondkillOrder.setGoodsId(goods.getId());
		secondkillOrder.setOrderId(orderInfo.getId());
		secondkillOrder.setUserId(user.getId());
		orderInfoDao.insertSecondkillOrder(secondkillOrder);

		redisService.set(OrderKey.getMiaoshaOrderByUidGid, user.getId() + "_" + goods.getId(), secondkillOrder);

		log.info("orderInfo {}", orderInfo.getId());
		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {
		return orderInfoDao.getOrderById(orderId);
	}

}
