package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.*;
import org.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StuController {

    @Autowired
    private StuService stuService;

    /*
     * 条件分页查询
     * */
    @GetMapping
    public Result page(StuQueryParam stuQueryParam) {
        log.info("分页查询：｛｝",stuQueryParam);
        PageResult<Student> pageResult = stuService.page(stuQueryParam);
        return  Result.success(pageResult);
    }

    /*
     * 根据id删除学员
     * */
    @DeleteMapping("/{ids}")
    public Result deleteById(@PathVariable List<Integer> ids) {
        log.info("根据id："+ids+" 删除学员");
        stuService.delete(ids);
        return Result.success();
    }

    /*
    * 添加学员
    * */
    @PostMapping
    public Result insert(@RequestBody Student stu) {
        log.info("添加的学员为｛｝",stu);
        stuService.insert(stu);
        return Result.success();
    }

    /*
     * 根据ID查询学员
     * */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("根据ID: "+id+" 进行查询学员");
        Student student = stuService.findByid(id);
        return Result.success(student);
    }

    /*
    * 修改学员信息
    * */
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("修改学员信息为：｛｝",student);
        stuService.update(student);
        return Result.success();
    }

    /*
    * 修改违纪
    * */
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("修改学生ID: "+id+" ，违纪分为："+score);
        stuService.violate(id, score);
        return Result.success();
    }


}
