package com.itheima.yyeats.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.yyeats.common.R;
import com.itheima.yyeats.entity.Category;
import com.itheima.yyeats.entity.Employee;
import com.itheima.yyeats.service.CategoryService;
import com.itheima.yyeats.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @param
 * @return
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("new category successfully added");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        //        构造分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);

//        构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper =new LambdaQueryWrapper<>();

//        添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);


        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("delete category according to id:{}",ids);

        categoryService.removeById(ids);
        return R.success("successfully deleted");
    }





}
