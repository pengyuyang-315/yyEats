package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.common.CustomException;
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

    @Transactional
    public void removeWithDish(List<Long> ids) {
//        查询套餐状态
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
//        如果不行 抛出异常
        if(count>0){
            throw new CustomException("Comba is in sell, cannot delete");
        }
//        如果可以 删除套餐数据
        this.removeByIds(ids);
//        删除关系表数据 setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }


}
