package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.dto.SetmealDto;
import com.itheima.yyeats.entity.Setmeal;
import com.itheima.yyeats.entity.SetmealDish;
import com.itheima.yyeats.mapper.SetmealMapper;
import com.itheima.yyeats.service.SetmealDishService;
import com.itheima.yyeats.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @param
 * @return
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
//      保存套餐基本信息，操作setmeal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());

//        save the information of relation between combo and dish
        setmealDishService.saveBatch(setmealDishes);
    }
}
