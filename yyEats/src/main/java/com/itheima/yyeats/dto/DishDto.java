package com.itheima.yyeats.dto;
import com.itheima.yyeats.entity.Dish;
import com.itheima.yyeats.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}