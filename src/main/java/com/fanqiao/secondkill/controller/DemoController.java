package com.fanqiao.secondkill.controller;

import com.fanqiao.secondkill.entity.Demo;
import com.fanqiao.secondkill.rabbitmq.MQSender;
import com.fanqiao.secondkill.redis.RedisService;
import com.fanqiao.secondkill.redis.UserKey;
import com.fanqiao.secondkill.result.Result;
import com.fanqiao.secondkill.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //如果要返回页面使用@Controller  @RestController 则Controller中的方法无法返回jsp页面，或者html
@RequestMapping("/demo")
@Log4j2
@Api(description = "DemoController相关的api")
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;

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

    @RequestMapping("/page")  //返回页面只能使用RequestMapping
    public String page(Model model) {
        model.addAttribute("name", "fq");
        return "page";
    }

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

    @PostMapping("/mq")
    @ResponseBody
    public Result<String> send() {
        mqSender.send("Hello MQ");
        log.info("Hello MQ");
        return Result.success("success");
    }

    @PostMapping("/topicMQ")
    @ResponseBody
    public Result<String> send2() {
        mqSender.sendTopic("Hello MQ");
        log.info("Hello MQ");
        return Result.success("success");
    }

    @PostMapping("/fanoutMQ")
    @ResponseBody
    public Result<String> send3() {
        mqSender.sendFanout("Hello MQ");
        log.info("Hello MQ");
        return Result.success("success");
    }

    @PostMapping("/headerMQ")
    @ResponseBody
    public Result<String> send4() {
        mqSender.sendHeaders("Hello header MQ");
        log.info("Hello header MQ");
        return Result.success("success");
    }
}
