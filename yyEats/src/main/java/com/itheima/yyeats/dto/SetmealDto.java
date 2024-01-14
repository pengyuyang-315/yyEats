package com.itheima.yyeats.dto;

import com.itheima.yyeats.entity.Setmeal;
import com.itheima.yyeats.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}