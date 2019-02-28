package com.fanqiao.secondkill.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "order_info")
public class OrderInfo {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "delivery_addr_id")
    private Long deliveryAddrId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_count")
    private Integer goodsCount;

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    @Column(name = "order_channel")
    private Byte orderChannel;

    /**
     * 0 未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成
     */
    private Byte status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "pay_date")
    private Date payDate;

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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return delivery_addr_id
     */
    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    /**
     * @param deliveryAddrId
     */
    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
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
     * @return goods_count
     */
    public Integer getGoodsCount() {
        return goodsCount;
    }

    /**
     * @param goodsCount
     */
    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
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
     * @return order_channel
     */
    public Byte getOrderChannel() {
        return orderChannel;
    }

    /**
     * @param orderChannel
     */
    public void setOrderChannel(Byte orderChannel) {
        this.orderChannel = orderChannel;
    }

    /**
     * 获取0 未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成
     *
     * @return status - 0 未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置0 未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成
     *
     * @param status 0 未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return pay_date
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * @param payDate
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}