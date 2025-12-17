package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    /*
     * 新增套餐
     * */
    void save(SetmealDTO setmealDTO);

    /*
    * 套餐分页查询
    * */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /*
     * 批量删除套餐
     * */
    void delete(List<Long> ids);

    /*
     * 根据id查询套餐信息及其关联菜品信息
     * */
    SetmealVO getByIdWithDish(Long id);

    /*
     * 修改套餐信息
     * */
    void update(SetmealDTO setmealDTO);

    /*
     * 套餐起售状态更改
     * */
    void startOrStop(Integer status, Long id);

    /*
    * 条件查询
    * */
    List<Setmeal> list(Setmeal setmeal);

    /*
    * 根据id查询菜品选项
    * */
    List<DishItemVO> getDishItemById(Long id);
}
