package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.GoodsListPrefix;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.service.GoodsService;
import com.fanqiao.secondkill.vo.GoodsVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/goods")
@Log4j2
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping("/to_list")
	@ResponseBody
    public String list(Model model, SecondkillUser secondkillUser, HttpServletRequest request, HttpServletResponse response) {
		if(secondkillUser != null) {
			log.info("list 入参 secondkillUser {}", secondkillUser.toString());
		}
    	model.addAttribute("user", secondkillUser);
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);

		//查询缓存
		String goodsListHtml = redisService.get(GoodsListPrefix.getGoodsList, "", String.class);
		if(!StringUtils.isEmpty(goodsListHtml)) {
			log.info("GoodsList 取缓存");
			return goodsListHtml;
		}

		//手动渲染
		WebContext ctx =
				new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
		goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
		if(!StringUtils.isEmpty(goodsListHtml)) {
			redisService.set(GoodsListPrefix.getGoodsList, "", goodsListHtml);
		}
		return goodsListHtml;
    }

	@RequestMapping("/to_detail/{goodsId}")
	@ResponseBody
	public String detail(Model model, SecondkillUser secondkillUser, @PathVariable("goodsId")Long goodsId, HttpServletRequest request, HttpServletResponse response) {

		model.addAttribute("user", secondkillUser);

		//查询缓存
		String goodsDetailHtml = redisService.get(GoodsListPrefix.getGoodsDetail, goodsId.toString(), String.class);
		if(!StringUtils.isEmpty(goodsDetailHtml)) {
			log.info("GoodsDetailHtml 取缓存");
			return goodsDetailHtml;
		}

		//手动渲染
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now < startAt ) {//秒杀还没开始，倒计时
			miaoshaStatus = 0;
			remainSeconds = (int)((startAt - now )/1000);
		}else  if(now > endAt) {//秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);

		WebContext ctx =
				new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
		goodsDetailHtml = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
		if(!StringUtils.isEmpty(goodsDetailHtml)) {
			redisService.set(GoodsListPrefix.getGoodsDetail, goodsId.toString(), goodsDetailHtml);
		}

		return goodsDetailHtml;
	}
}
