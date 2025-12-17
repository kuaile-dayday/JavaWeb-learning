package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /*
     * 新增套餐
     * */
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        // 分别操作两张表

        // 将套餐基础信息保存到setmeal表中
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);

        // 将套餐和菜品的关联关系保存到setmeal_dish表中
        // setmeal_dish表中的setmeal_id 是 新增套餐后自动生成的id，所以这里要主键回显
        Long setmealId = setmeal.getId();
        // setmealDTO对象中 套餐和菜品的关联关系 的数据可能有多条，所以每一条数据都要设置 setmealId，所以遍历
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        if (setmealDishList != null && setmealDishList.size() > 0){
            setmealDishList.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
            setmealDishMapper.insertBatch(setmealDishList);
        }


    }

    /*
     * 套餐分页查询
     * */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<SetmealVO> setmealVOList = setmealMapper.page(setmealPageQueryDTO);
        Page<SetmealVO> page = (Page<SetmealVO>)setmealVOList;
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*
     * 批量删除套餐
     * */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        // 分别操作两张表，但是先判断启售状态，所以这里要先查询套餐信息
        for (Long id : ids){
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        // setmeal表 传入ids 依次删除其所有信息
        setmealMapper.deleteByIds(ids);

        // setmeal_dish表 删除所有关联关系
        setmealDishMapper.deleteBySetmealIds(ids);

    }

    /*
     * 根据id查询套餐信息及其关联菜品信息
     * */
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        // 分开查询吧 先查询套餐基础信息
        Setmeal setmeal = setmealMapper.getById(id);
        // 现在查询 套餐关联的菜品信息
        List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(id);
        // 拼接在一起
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishList);

        return setmealVO;
    }

    /*
     * 修改套餐信息
     * */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        // 套餐基础信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        Long setmealId = setmeal.getId();

        // 删除套餐和菜品的关联关系，操作setmeal_dish表，执行delete
        setmealDishMapper.deleteBySetmealIds(Collections.singletonList(setmealId));

        // 添加新的套餐和菜品的关联关系，操作setmeal_dish表，执行insert
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        if (setmealDishList != null && setmealDishList.size() > 0){
            setmealDishList.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
            setmealDishMapper.insertBatch(setmealDishList);
        }

    }

    /*
     * 套餐起售状态更改
     * */
    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        // 先写停售的吧
        if(setmeal.getStatus()==StatusConstant.ENABLE){
            // 当前状态是起售，那就可以停售了 这个没有什么规则
            setmeal.setStatus(StatusConstant.DISABLE);
            setmealMapper.update(setmeal);
        }
        else {
            // 此时是停售，如果要起售就需要判断了
            // 先获取关联菜品
            List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(id);
            if (setmealDishList != null && setmealDishList.size() > 0){
                for (SetmealDish setmealDish : setmealDishList){
                    // 获取每一个关联的菜品
                    Dish dish = dishMapper.getById(setmealDish.getDishId());
                    if (dish.getStatus() == StatusConstant.DISABLE){
                        // 菜品停售了，不能起售套餐
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }

                }
            }
            // 运行到这里 说明没有包含停售菜品 可以起售套餐
            setmeal.setStatus(StatusConstant.ENABLE);
            setmealMapper.update(setmeal);
        }


    }

    /*
    * 条件查询
    * */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /*
    * 根据id查询菜品选项
    * */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
