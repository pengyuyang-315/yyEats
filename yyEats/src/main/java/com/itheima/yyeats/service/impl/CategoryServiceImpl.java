package com.itheima.yyeats.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.yyeats.common.CustomException;
import com.itheima.yyeats.entity.Category;
import com.itheima.yyeats.entity.Dish;
import com.itheima.yyeats.entity.Setmeal;
import com.itheima.yyeats.mapper.CategoryMapper;
import com.itheima.yyeats.service.CategoryService;
import com.itheima.yyeats.service.DishService;
import com.itheima.yyeats.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * @param
 * @return
 */
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id){

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        查询当前分类是否关联了菜品
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);

        if(count>0){
//            已经关联了菜品
            throw new CustomException("当前分类项关联了菜品");
        }
//        查询当前分类是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        查询当前分类是否关联了菜品
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        if(count1>0){
//            已经关联了套餐
            throw new CustomException("当前分类项关联了套餐");
        }

//        正常删除
        super.removeById(id);
   }

}
