package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /*
    * 新增员工
    * */
    void save(EmployeeDTO employeeDTO);

    /*
    * 分页查询
    * */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /*
     * 启动、禁用员工账号
     * */
    void startOrStop(Integer status, Long id);

    /*
     * 根据id查询员工信息查询回显
     * */
    Employee getInfo(Long id);

    /*
     * 根据id 修改员工数据
     * */
    void update(Employee emp);
}
