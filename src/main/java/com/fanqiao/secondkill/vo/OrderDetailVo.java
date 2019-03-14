package com.fanqiao.secondkill.vo;


import com.fanqiao.secondkill.entity.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
	private GoodsVo goods;
	private OrderInfo order;

}
