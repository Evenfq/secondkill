package com.fanqiao.secondkill.service;

import com.fanqiao.secondkill.dao.OrderInfoDao;
import com.fanqiao.secondkill.entity.OrderInfo;
import com.fanqiao.secondkill.entity.SecondkillOrder;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
	
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	public SecondkillOrder getSecondkillOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderInfoDao.getSecondkillOrderByUserIdGoodsId(userId, goodsId);
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
		long orderId = orderInfoDao.insert(orderInfo);
		SecondkillOrder secondkillOrder = new SecondkillOrder();
		secondkillOrder.setGoodsId(goods.getId());
		secondkillOrder.setOrderId(orderId);
		secondkillOrder.setUserId(user.getId());
		orderInfoDao.insertSecondkillOrder(secondkillOrder);
		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {
		return orderInfoDao.getOrderById(orderId);
	}

}
