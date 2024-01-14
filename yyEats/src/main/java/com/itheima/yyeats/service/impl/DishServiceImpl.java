package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.dto.DishDto;
import com.itheima.yyeats.entity.Dish;
import com.itheima.yyeats.entity.DishFlavor;
import com.itheima.yyeats.mapper.DishMapper;
import com.itheima.yyeats.service.DishFlavorService;
import com.itheima.yyeats.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @param
 * @return
 */
@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;

    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors =dishDto.getFlavors();
        for(DishFlavor flavor:flavors){
            flavor.setDishId(dishId);
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }
}
