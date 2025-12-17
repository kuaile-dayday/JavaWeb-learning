package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/setmeal")
@RestController
@Slf4j
@Api(tags = "套餐相关接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    /*
    * 新增套餐
    * */
    @PostMapping
    @ApiOperation("新增套餐")
    @Cacheable(cacheNames = "setmealCache",key="#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}",setmealDTO);
        setMealService.save(setmealDTO);
        return Result.success();
    }

    /*
    * 套餐分页查询
    * */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}",setmealPageQueryDTO);
        PageResult pageResult = setMealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
    * 批量删除套餐
    * */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除套餐：{}",ids);
        setMealService.delete(ids);
        return Result.success();
    }

    /*
    * 根据id查询套餐信息及其关联菜品信息，用于修改界面回显数据
    * */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐信息及其关联菜品信息")
    public Result getById(@PathVariable Long id){
        log.info("根据id查询套餐及其关联菜品信息：{}",id);
        SetmealVO setmealVO = setMealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /*
    * 修改套餐信息
    * */
    @PutMapping
    @ApiOperation("修改套餐信息")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐信息：{}",setmealDTO);
        setMealService.update(setmealDTO);
        return Result.success();
    }

    /*
    * 套餐起售状态更改
    * */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售状态更改")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("套餐 {} 起售状态更改：{}",id, status);
        setMealService.startOrStop(status,id);
        return Result.success();
    }

}
