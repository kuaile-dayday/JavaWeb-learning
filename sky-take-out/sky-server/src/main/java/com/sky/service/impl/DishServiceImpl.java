package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /*
     * 新增菜品和对应的口味
     * */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        // 设计两个表的操作，所以打开事务管理

        Dish  dish = new Dish();

//        BeanUtils.copyProperties(dishDTO,dish);

        dish.setName(dishDTO.getName());
        dish.setCategoryId(dishDTO.getCategoryId());
        dish.setPrice(dishDTO.getPrice());
        dish.setImage(dishDTO.getImage());
        dish.setDescription(dishDTO.getDescription());
        dish.setStatus(dishDTO.getStatus());
        dishMapper.insert(dish);


        /*
        * dish_flavor 表中的 dish_id 是上述操作完成后自动生成的id
        * 所以需要主键回显，在DishMapper具体插入语句中使用 useGeneratedKeys等 （标准语句）
        * */
        Long dishId = dish.getId(); // 获取insert语句生成的主键值

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            // 向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);

        }


    }





}
