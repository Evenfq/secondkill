package com.fanqiao.secondkill.redis;


public class UserKey extends BasePrefix {

    private static int seconds = 3600;

    private UserKey(int seconds, String prefix) {
        super(seconds, prefix);
    }

    public static UserKey getById = new UserKey(seconds, "id");
    public static UserKey getByToken = new UserKey(seconds, "token");
}
