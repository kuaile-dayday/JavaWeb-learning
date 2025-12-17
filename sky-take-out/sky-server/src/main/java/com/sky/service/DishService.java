package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /*
     * 新增菜品和对应的口味
     * */
    void saveWithFlavor(DishDTO dishDTO);

    /*
    * 菜品分页查询
    * */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /*
     * （批量）删除菜品
     * */
    void delete(List<Long> ids);

    /*
     * 根据id查询菜品和对应口味
     * */
    DishVO getByIdWithFlavor(Long id);

    /*
     * 修改菜品 包括口味信息
     * */
    void update(DishDTO dishDTO);

    /*
     * 根据菜品分类id（categoryId）查询菜品
     * */
    List<Dish> getDishByCategoryId(Long categoryId);

    /*
     * 修改菜品的启售状态
     * */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
