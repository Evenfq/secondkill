package com.fanqiao.secondkill.controller;

import com.fanqiao.secondkill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @RequestMapping("/")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("success");
    }
}
