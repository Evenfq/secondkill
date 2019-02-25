package com.fanqiao.secondkill.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2018-2021, 深圳小财迷信息科技有限公司
 * FileName: RedisLock
 * Author:   xiaojian
 * Date:     2019/2/13 14:15
 * Description: 分布式锁
 *
 * @since 1.0.0
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String LOCK_VALUE = "lockValue";

    private boolean locked = false;

    public synchronized boolean lock(String lockKey, Long second){
        /* 该方法会在没有key时，设置key;存在key时返回false；因此可以通过该方法及设置key的有效期，判断是否有其它线程持有锁 */
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, LOCK_VALUE);
        if (success != null && success) {
            log.info(">>>>>>>>>>>>>>>>>>>>>分布式锁, 锁定时间[{}]秒", second);
            redisTemplate.expire(lockKey, second, TimeUnit.SECONDS);
            locked = true;
        }
        else {
            locked = false;
        }
        return locked;
    }

    /**
     * 功能描述: 当前时间到第二天凌晨的秒数
     *
     * @Author:xiaojian
     * @Date: 2019/2/13 14:26
     * @since: v1.0
     */
    public Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }
    
}