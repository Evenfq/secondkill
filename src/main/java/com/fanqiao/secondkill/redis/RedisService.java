package com.fanqiao.secondkill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean set(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            jedis.set(key, str);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value == null) {
            return null;
        } else if(value.getClass() == int.class || value.getClass() == Integer.class) {
            return "" + value;
        } else if(value.getClass() == long.class || value.getClass() == Long.class) {
            return "" + value;
        } else if(value.getClass() == String.class) {
            return (String)value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String value, Class<T> clazz) {
        if(value == null || value.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(value);
        } else if(clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(value);
        } else if(clazz == String.class) {
            return (T)value;
        } else {
            return JSON.toJavaObject(JSON.parseObject(value), clazz);
        }
    }
}
