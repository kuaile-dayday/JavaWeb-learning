package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.pojo.*;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/log")
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    /*
    * 日志列表分页查询
    * */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("日志列表分页查询：page = {}, pageSize = {}",page,pageSize);
        PageResult<OperateLog> pageResult = logService.page(page,pageSize);


        return Result.success(pageResult);
    }

}
