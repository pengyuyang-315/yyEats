package com.itheima.yyeats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.yyeats.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
