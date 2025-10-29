package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.*;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


/*
* 员工管理
*
* */
@Slf4j
@RequestMapping("/emps")// 由于该文件中 所有方法的请求路径都是在emps下的，所以定义一个公共前缀
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    /*
    * 分页查询
    * */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询：{}",empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);

        return Result.success(pageResult);
    }

    /*
    * 新增员工
    * */
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("新增员工信息为：{}",emp);
        empService.save(emp);
        return Result.success();

    }

//    /*
//    * 员工删除 —— 数组
//    * */
//    @DeleteMapping
//    public Result delete(Integer[] ids) {
//        log.info("要删除的员工信息为：｛｝", Arrays.toString(ids));
//
//        return Result.success();
//    }
    /*
     * 员工删除 —— 集合
     * */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("要删除的员工信息为：{}", ids);
        empService.delete(ids);

        return Result.success();
    }

    /*
    * 根据id查询员工信息查询回显
    * */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /*
    * 根据id 修改员工数据
    * */
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工：｛｝",emp);
        empService.update(emp);
        return Result.success();

    }

    /*
     * 查询所有班主任(员工）
     * */
    @GetMapping("/list")
    public Result listMaster() {
        log.info("查询班主任列表");
        List<Emp> masterList = empService.listMaster();
        return Result.success(masterList);
    }

}
