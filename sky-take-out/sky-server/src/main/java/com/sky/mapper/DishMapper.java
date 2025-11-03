package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /*
    * 新增菜品
    * */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /*
     * 菜品分页查询
     * */
    List<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 批量删除菜品
     * */
    void deleteByIds(List<Long> ids);

    /*
    * 根据id查询菜品数据
    * */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /*
    * 修改菜品基本信息
    * */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
