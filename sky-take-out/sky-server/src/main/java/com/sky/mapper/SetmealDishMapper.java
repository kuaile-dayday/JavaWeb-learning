package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /*
    * 根据菜品id查询对应的套餐id（可能是一对多 多对多的关系 所以返回列表）
    * */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
