package com.fanqiao.secondkill.controller;

import com.fanqiao.secondkill.entity.Demo;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.redis.UserKey;
import com.fanqiao.secondkill.result.Result;
import com.fanqiao.secondkill.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Log4j2
@Api(description = "DemoController相关的api")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private RedisService redisService;

    /*@RequestMapping("/")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("success");
    }

    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute("name", "fq");
        return "page";
    }

    @ApiOperation(value = "获取Demo信息")
    @GetMapping("/selectDemo")
    @ResponseBody  //加上ResponseBody返回值就作为实体数据返回，不作为template返回
    public Result<Demo> selectDemo() {
        Demo demo = demoService.selectDemo(1L);
        log.info("demo: {}", demo.toString());
        return new Result(demo);
    }*/

    @ApiOperation(value = "获取redis给定的值")
    @GetMapping("/redis/get")
    @ResponseBody  //加上ResponseBody返回值就作为实体数据返回，不作为template返回
    public Result<String> redisGet(String value) {
        String str = redisService.get(UserKey.getById, value, String.class);
        return new Result(str);
    }

    @ApiOperation(value = "设置redis给定的值")
    @GetMapping("/redis/set")
    @ResponseBody  //加上ResponseBody返回值就作为实体数据返回，不作为template返回
    public Result<String> redisSet(String key, String value) {
        boolean rst = redisService.set(UserKey.getById, key, value);
        String str = redisService.get(UserKey.getById, key, String.class);
        return new Result(str);
    }
}
