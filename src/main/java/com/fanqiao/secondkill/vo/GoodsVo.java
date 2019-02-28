package com.fanqiao.secondkill.vo;

import com.fanqiao.secondkill.entity.Goods;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsVo extends Goods {

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    private BigDecimal secondkillPrice;
}
