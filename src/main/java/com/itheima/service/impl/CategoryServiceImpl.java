package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.Common.CustomException;
import com.itheima.domain.Category;
import com.itheima.domain.Dish;
import com.itheima.domain.Setmeal;
import com.itheima.mapper.CategoryMapper;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        queryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(queryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count>0){
            //能进入代表已经关联菜品，不能删除因此抛出一个业务异常，这个业务异常专门做了一个异常类CustomException
            throw new CustomException("当前分类下，关联了菜品，不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealqueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealqueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealqueryWrapper);
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        if (count>0){
            //能进入代表已经关联套餐，不能删除因此抛出一个业务异常
            throw new CustomException("当前分类下，关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);

    }
}
