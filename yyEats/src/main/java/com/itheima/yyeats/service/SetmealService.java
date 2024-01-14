package com.itheima.yyeats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.yyeats.dto.SetmealDto;
import com.itheima.yyeats.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
}
