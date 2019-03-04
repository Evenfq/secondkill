package com.fanqiao.secondkill.controller;

import com.fanqiao.secondkill.entity.OrderInfo;
import com.fanqiao.secondkill.entity.SecondkillOrder;
import com.fanqiao.secondkill.entity.SecondkillUser;
import com.fanqiao.secondkill.result.CodeMessage;
import com.fanqiao.secondkill.service.GoodsService;
import com.fanqiao.secondkill.service.OrderService;
import com.fanqiao.secondkill.service.SecondkillService;
import com.fanqiao.secondkill.vo.GoodsVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/secondkill")
@Log4j2
public class SecondkillController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private SecondkillService secondkillService;

    @RequestMapping("/do_secondkill")
    public String doSecondkill(Model model,SecondkillUser user,
                       @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errmsg", CodeMessage.REPERTORY_EMPTY);
            return "secondkill_fail";
        }
        //判断是否已经秒杀到了
        SecondkillOrder order = orderService.getSecondkillOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMessage.SECONDKILL_REPEAT.getMessage());
            return "secondkill_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = secondkillService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
