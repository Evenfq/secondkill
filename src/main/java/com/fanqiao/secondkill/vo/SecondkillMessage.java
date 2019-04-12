package com.fanqiao.secondkill.vo;

import com.fanqiao.secondkill.entity.SecondkillUser;
import lombok.Data;

@Data
public class SecondkillMessage {
    private SecondkillUser secondkillUser;
    private Long goodsId;
}
