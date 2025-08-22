package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.StuMapper;
import org.example.pojo.PageResult;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;
import org.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    /*
     * 条件分页查询
     * */
    @Override
    public PageResult<Student> page(StuQueryParam stuQueryParam) {
        Integer page = stuQueryParam.getPage();
        Integer pageSize = stuQueryParam.getPageSize();
        PageHelper.startPage(page,pageSize);
        List<Student> stuList = stuMapper.page(stuQueryParam);

        Page<Student> p = (Page<Student>) stuList;
        return new PageResult<Student>(p.getTotal(),p.getResult());
    }

    /*
     * 根据学员ID删除
     * */
    @Override
    public void delete(List<Integer> ids) {
        stuMapper.delete(ids);
    }

    /*
    * 添加学员
    * */
    @Override
    public void insert(Student stu) {
        stu.setCreateTime(LocalDateTime.now());
        stu.setUpdateTime(LocalDateTime.now());
        stu.setViolationCount((short) 0);
        stu.setViolationScore((short) 0);
        stuMapper.insert(stu);
    }

    /*
     * 根据ID查询学员
     * */
    @Override
    public Student findByid(Integer id) {
        Student student = stuMapper.findByid(id);
        return student;
    }

    /*
    * 修改学员
    * */
    @Override
    public void update(Student student) {
        stuMapper.update(student);
    }

    /*
    * 修改违纪信息
    * */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void violate(Integer id, Integer score) {
        Student stu = stuMapper.findByid(id);

        short count = stu.getViolationCount();
        count = (short) (count+1);

        short sum = stu.getViolationCount();
        sum = (short) (score+sum);

        stuMapper.violate(id,sum,count);
    }
}
