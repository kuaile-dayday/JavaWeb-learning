package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private EmpService empService;

    /*
    * 条件分页查询
    * */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询（班级管理）：｛｝，｛｝",clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return  Result.success(pageResult);
    }

    /*
    * 删除班级
    * */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        log.info("删除班级id：{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    /*
    * 新增班级
    * */
    @PostMapping
    public Result insert(@RequestBody Clazz clazz) {
        log.info("新增班级：｛｝",clazz);
        clazzService.insert(clazz);
        return Result.success();
    }

    /*
    * 根据ID查询班级
    * */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("根据ID: "+id+" 进行查询");
        Clazz clazz = clazzService.findByid(id);
        return Result.success(clazz);
    }

    /*
     * 修改班级
     * */
    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级信息为：｛｝",clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    /*
    * 查询班级列表
    * */
    @GetMapping("/list")
    public Result list(){
        log.info("班级管理——查询班级列表");
        List<Clazz> clazzList = clazzService.list();
        return Result.success(clazzList);

    }









}
