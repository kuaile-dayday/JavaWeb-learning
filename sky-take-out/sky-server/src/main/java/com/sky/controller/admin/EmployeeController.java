package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /*
    * 新增员工
    * */
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工，员工数据：{}",employeeDTO);
        System.out.println("当前线程的id："+ Thread.currentThread().getId());
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /*
    * 分页查询
    * */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询，参数：{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /*
    * 启动、禁用员工账号
    * */
    @PostMapping("/status/{status}")
    @ApiOperation("启动、禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("员工状态：{}，员工id：{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /*
    * 根据id查询员工信息查询回显
    * */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息查询回显")
    public Result getInfo(@PathVariable Long id){
        log.info("查询员工id：{}",id);
        Employee emp = employeeService.getInfo(id);
        return Result.success(emp);
    }

    /*
    * 根据id 修改员工数据
    * */
    @PutMapping
    @ApiOperation("根据id 修改员工数据")
    public Result update(@RequestBody Employee emp){
        log.info("修改员工：{}",emp);
        employeeService.update(emp);
        return Result.success();
    }



}
