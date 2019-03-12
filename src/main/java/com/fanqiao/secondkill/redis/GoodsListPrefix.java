package com.fanqiao.secondkill.redis;


public class GoodsListPrefix extends BasePrefix {

    private static int seconds = 60;

    private GoodsListPrefix(int seconds, String prefix) {
        super(seconds, prefix);
    }

    public static GoodsListPrefix getGoodsList = new GoodsListPrefix(seconds, "GoodsList");
    public static GoodsListPrefix getGoodsDetail = new GoodsListPrefix(seconds, "GoodsDetail");

}
