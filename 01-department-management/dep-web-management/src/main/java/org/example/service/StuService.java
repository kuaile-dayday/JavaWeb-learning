package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;

import java.util.List;

public interface StuService {

    /*
    * 条件分页查询
    * */
    PageResult<Student> page(StuQueryParam stuQueryParam);

    /*
    * 根据id删除学员
    * */
    void delete(List<Integer> ids);

    /*
    * 添加学员
    * */
    void insert(Student stu);

    /*
    * 根据ID查询学员
    * */
    Student findByid(Integer id);

    /*
    * 修改学员数据
    * */
    void update(Student student);

    /*
    * 修改学员违纪信息
    * */
    void violate(Integer id, Integer score);
}
