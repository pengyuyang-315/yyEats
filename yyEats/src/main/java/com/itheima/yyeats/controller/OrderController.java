package com.itheima.yyeats.controller;

import com.itheima.yyeats.common.R;
import com.itheima.yyeats.entity.Orders;
import com.itheima.yyeats.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @return
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("order information:{}",orders);
        orderService.submit(orders);
        return R.success("ordered successfully");
    }
}
