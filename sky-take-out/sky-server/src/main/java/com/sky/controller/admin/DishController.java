package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/*
* 菜品管理
* */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    * 新增菜品和对应的口味
    * */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);


        return Result.success();

    }

    /*
    * 菜品分页查询
    * */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);

        return Result.success(pageResult);
    }

    /*
    * （批量）删除菜品
    * */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品，ids：{}",ids);
        dishService.delete(ids);

        // 清理缓存数据 因为这里的ids可能是一个也可能是多个，如果要去判断删除对应的就很麻烦，
        //　所以这里直接将所有的菜品缓存数据都清理掉，所有以ｄｉｓｈ＿开头的ｋｅｙ
        cleanCache("dish_*");

        return Result.success();
    }

    /*
    * 根据id查询菜品和对应口味
    * */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和口味")
    public Result getDishById(@PathVariable Long id){
        log.info("根据id查询菜品和口味：{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /*
    * 修改菜品
    * */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.update(dishDTO);

        // 清理缓存数据 因为这里的ids可能是一个也可能是多个，如果要去判断删除对应的就很麻烦，
        //　所以这里直接将所有的菜品缓存数据都清理掉，所有以ｄｉｓｈ＿开头的ｋｅｙ
        cleanCache("dish_*");


        return Result.success();
    }

    /*
    * 根据菜品分类id（categoryId）查询菜品
    * */
    @GetMapping("/list")
    @ApiOperation("根据菜品分类id查询菜品")
    public Result getDishByCategoryId(Long categoryId){
        log.info("根据分类id查询菜品：{}",categoryId);
        List<Dish> dishList = dishService.getDishByCategoryId(categoryId);

        return Result.success(dishList);
    }

    /*
    * 修改菜品的启售状态
    * */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品的启售状态")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用或禁用菜品：{}",id);
        dishService.startOrStop(status,id);

        // 清理缓存数据 因为这里的ids可能是一个也可能是多个，如果要去判断删除对应的就很麻烦，
        //　所以这里直接将所有的菜品缓存数据都清理掉，所有以ｄｉｓｈ＿开头的ｋｅｙ
        cleanCache("dish_*");

        return Result.success();
    }

    /*
    * 清理缓存数据
    * */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }


}
