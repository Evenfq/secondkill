package com.fanqiao.secondkill.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//读取redis配置文件
@Component
@ConfigurationProperties(prefix = "redis") //在配置文件xml中读取以redis开头的文件
@Data
public class RedisConfig {

    private String host;
    private int port;
    private String password;
    private int database;
    private int timeout;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
}
