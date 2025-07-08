package org.example.service;

import org.example.pojo.Dept;

import java.util.List;

public interface DeptService {
    /*
    * 查询所有部门数据
    * */
    List<Dept> findAll();


    /*
    * 根据id删除部门
    * */
    void deleteById(Integer id);

    /*
    *  增加部门
    * */
    void add(Dept dept);

    /*
    *  根据id 查询数据
    * */
    Dept getById(Integer deptId);

    /*
    * 修改部门
    * */
    void update(Dept dept);
}
