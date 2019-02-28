package com.fanqiao.secondkill.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

public class Goods {
    @Id
    private Long id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_title")
    private String goodsTitle;

    @Column(name = "goods_img")
    private String goodsImg;

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    @Column(name = "goods_stock")
    private Integer goodsStock;

    @Column(name = "goods_detail")
    private String goodsDetail;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return goods_name
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return goods_title
     */
    public String getGoodsTitle() {
        return goodsTitle;
    }

    /**
     * @param goodsTitle
     */
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    /**
     * @return goods_img
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * @param goodsImg
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    /**
     * @return goods_price
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * @param goodsPrice
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * @return goods_stock
     */
    public Integer getGoodsStock() {
        return goodsStock;
    }

    /**
     * @param goodsStock
     */
    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    /**
     * @return goods_detail
     */
    public String getGoodsDetail() {
        return goodsDetail;
    }

    /**
     * @param goodsDetail
     */
    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}