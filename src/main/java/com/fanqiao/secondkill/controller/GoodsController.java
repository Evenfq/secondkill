package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.redis.UserKey;
import com.fanqiao.secondkill.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
@Log4j2
public class GoodsController {

	@Autowired
	private RedisService redisService;
	@Autowired
	private LoginService loginService;
	
    @RequestMapping("/to_list")
    public String list(Model model,
					   //@CookieValue(value = LoginService.COOKIE_NAME, required = false) String cookieName,
					   //@RequestParam(value = LoginService.COOKIE_NAME, required = false) String paramName
					   SecondkillUser secondkillUser
	) {
    	/*if(StringUtils.isEmpty(cookieName) && StringUtils.isEmpty(paramName)) {
    		return "login";
		}
		log.info("cookieName {}", cookieName);
		log.info("paramName {}", paramName);
		String token = StringUtils.isEmpty(cookieName)?paramName:cookieName;
		SecondkillUser secondkillUser = loginService.getByToken(response, token);*/
    	model.addAttribute("user", secondkillUser);
        return "goods_list";
    }
}
