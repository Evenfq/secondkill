package com.fanqiao.secondkill.redis;


public class GoodsListPrefix extends BasePrefix {

    private static int seconds = 3600;

    private GoodsListPrefix(int seconds, String prefix) {
        super(seconds, prefix);
    }

    public static GoodsListPrefix getGoodsList = new GoodsListPrefix(seconds, "GoodsList");

}
