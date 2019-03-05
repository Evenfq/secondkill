package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.result.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {


	
    @RequestMapping("/info")
    @ResponseBody
    public Result<SecondkillUser> info(SecondkillUser user) {
        log.info("user {}", user != null ? user.toString() : "空user");
        return Result.success(user);
    }
    
}
