package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.redis.UserKey;
import com.fanqiao.secondkill.service.GoodsService;
import com.fanqiao.secondkill.service.LoginService;
import com.fanqiao.secondkill.vo.GoodsVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
@Log4j2
public class GoodsController {

	@Autowired
	private RedisService redisService;
	@Autowired
	private LoginService loginService;
	@Autowired
	GoodsService goodsService;

	@RequestMapping("/to_list")
    public String list(Model model, SecondkillUser secondkillUser) {
    	model.addAttribute("user", secondkillUser);
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }




}
