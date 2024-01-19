package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.entity.OrderDetail;
import com.itheima.yyeats.mapper.OrderDetailMapper;
import com.itheima.yyeats.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @param
 * @return
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
