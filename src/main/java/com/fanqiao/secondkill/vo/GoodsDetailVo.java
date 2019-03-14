package com.fanqiao.secondkill.vo;


import com.fanqiao.secondkill.entity.SecondkillUser;
import lombok.Data;

@Data
public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods ;
	private SecondkillUser user;

}
