package com.fanqiao.secondkill.service;


import com.fanqiao.secondkill.dao.GoodsDao;
import com.fanqiao.secondkill.entity.SecondkillGoods;
import com.fanqiao.secondkill.vo.GoodsVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean reduceStock(GoodsVo goods) {
		log.info("reduceStock 入参 {}", goods.toString());
		SecondkillGoods g = new SecondkillGoods();
		g.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(g);
		return ret > 0;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			SecondkillGoods g = new SecondkillGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}
	
	
	
}
