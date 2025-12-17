package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /*
    * 根据菜品id查询对应的套餐id（可能是一对多 多对多的关系 所以返回列表）
    * */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    /*
    * 插入套餐对应包含的菜品数据 一条数据可能有多项数据插入 所以“批次”插入
    * */
    void insertBatch(List<SetmealDish> setmealDishList);

    /*
    * 根据套餐 ID 删除套餐和菜品的关联关系
    * */
    void deleteBySetmealIds(List<Long> setMealids);

    /*
    * 根据套餐id查询套餐和菜品的关联关系
    * */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);
}
