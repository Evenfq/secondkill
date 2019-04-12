package com.fanqiao.secondkill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;


@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    //分布式相关
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    public void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean set(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            String str = beanToString(value);
            if(str == null || str.length() <= 0) {
                return false;
            }
            if(keyPrefix.getExpiredSeconds() <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, keyPrefix.getExpiredSeconds(),str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean exists(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long incr(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Boolean del(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + ":" + key;
            return jedis.del(realKey) > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) {
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

    public static <T> T stringToBean(String value, Class<T> clazz) {
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


    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public  boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {

        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock(String lockKey, String requestId) {

        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } finally {
            returnToPool(jedis);
        }
    }
}
