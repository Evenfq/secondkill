package com.fanqiao.secondkill.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expiredSeconds;
    private String prefix;

    public BasePrefix(int expiredSeconds, String prefix) {
        this.expiredSeconds = expiredSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) { //默认0代表永不过期
        this(0, prefix);
    }

    @Override
    public int getExpiredSeconds() {
        return expiredSeconds;
    }

    @Override
    public String getPrefix() {
        return getClass().getSimpleName() + ":" + prefix;
    }


}
