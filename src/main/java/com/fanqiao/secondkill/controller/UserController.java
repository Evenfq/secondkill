package com.fanqiao.secondkill.controller;


import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {


	
    @RequestMapping("/info")
    @ResponseBody
    public Result<SecondkillUser> info(SecondkillUser user) {
        return Result.success(user);
    }
    
}
