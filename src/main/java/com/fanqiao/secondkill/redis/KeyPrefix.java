package com.fanqiao.secondkill.redis;

public interface KeyPrefix {

    public int getExpiredSeconds();

    public String getPrefix();

}
