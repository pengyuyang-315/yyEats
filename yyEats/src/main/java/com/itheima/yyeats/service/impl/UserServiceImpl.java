package com.itheima.yyeats.service.impl;

import com.itheima.yyeats.entity.User;
import com.itheima.yyeats.mapper.UserMapper;
import com.itheima.yyeats.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
