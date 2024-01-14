package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.dto.DishDto;
import com.itheima.yyeats.entity.Dish;
import com.itheima.yyeats.entity.DishFlavor;
import com.itheima.yyeats.mapper.DishMapper;
import com.itheima.yyeats.service.DishFlavorService;
import com.itheima.yyeats.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    public DishDto getByIdWithFlavor(Long id) {
//        get dish
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
//        get dishFlavor
        LambdaQueryWrapper<DishFlavor> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    public void updateWithFlavor(DishDto dishDto) {
//        update dish basic
        this.updateById(dishDto);
//        clean dish_flavor
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

//        add new dish_flavor
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors =dishDto.getFlavors();
        for(DishFlavor flavor:flavors){
            flavor.setDishId(dishId);
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }
}
