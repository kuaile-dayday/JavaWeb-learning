package org.example.service;

import org.apache.ibatis.annotations.Select;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /*
    * 分页查询
    * */
//    PageResult<Clazz> page(Integer page, Integer pageSize);

    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    /*
    * 根据id删除班级
    * */
    void deleteById(Integer id);

    /*
    * 新增班级
    * */
    void insert(Clazz clazz);

    /*
    * 根据ID查询班级
    * */
    Clazz findByid(Integer id);

    /*
    * 查询班级列表
    * */
    List<Clazz> list();

    /*
     * 修改班级
     * */
    void update(Clazz clazz);
}
