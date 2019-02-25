package com.fanqiao.secondkill.controller;

import com.fanqiao.secondkill.dao.SecondkillUserDao;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.service.LoginService;
import com.fanqiao.secondkill.util.MD5Util;
import com.fanqiao.secondkill.util.ValidatorUtil;
import com.fanqiao.secondkill.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fanqiao.secondkill.result.Result ;
import com.fanqiao.secondkill.result.CodeMessage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Log4j2
public class LoginController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SecondkillUserDao secondkillUserDao;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "返回登录页面接口") //返回页面只能使用RequestMapping
    @RequestMapping ("/login")
    //加上ResponseBody返回值就作为实体数据返回，不作为template返回
    public String login() {
        return "login";
    }

    @ApiOperation(value = "登录接口")
    @PostMapping("/doLogin")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info("loginVo: {}", loginVo.toString());
        loginService.doLogin(response, loginVo);
        return Result.success(true);
    }
}
