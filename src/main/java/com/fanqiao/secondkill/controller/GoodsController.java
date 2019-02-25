package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model, SecondkillUser  user) {
    	model.addAttribute("user", user);
        return "goods_list";
    }
    
}
