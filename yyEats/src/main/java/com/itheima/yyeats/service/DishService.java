package com.itheima.yyeats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.yyeats.dto.DishDto;
import com.itheima.yyeats.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
