package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /*
    * 新增员工
    * */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("当前线程的id："+ Thread.currentThread().getId());

        Employee employee = new Employee();

/*        以下可以直接用 对象属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);*/

        employee.setUsername(employeeDTO.getUsername());
        employee.setName(employeeDTO.getName());
//        设置密码 默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setPhone(employeeDTO.getPhone());
        employee.setSex(employeeDTO.getSex());
        employee.setIdNumber(employeeDTO.getIdNumber());
//        表示账户状态 默认正常状态 1表示启用 0表示禁用 这里用变量名称
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
//        设置当前记录创建人id和修改人id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);



    }

    /*
    * 分页查询
    * */
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        // 设置分页参数（固定的）
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        // 调用mapper层查询
        List<Employee> emplist = employeeMapper.list(employeePageQueryDTO);
        // 解析查询结果并封装返回
        Page<Employee> p = (Page<Employee>) emplist;

//        Page<Employee> p = employeeMapper.list(employeePageQueryDTO);


        return new PageResult(p.getTotal(),p.getResult());
    }

    /*
     * 启动、禁用员工账号
     * */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee emp = new Employee();
        emp.setId(id);
        emp.setStatus(status);
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(emp);

    }

    /*
     * 根据id查询员工信息查询回显
     * */
    @Override
    public Employee getInfo(Long id) {
        Employee employee = employeeMapper.getInfoById(id);
        employee.setPassword("****");
        return employee;
    }

    /*
     * 根据id 修改员工数据
     * */
    @Override
    public void update(Employee emp) {
        // 从数据库查询原始员工数据
        Employee originalEmployee = employeeMapper.getInfoById(emp.getId());

        // 将原始密码设置回emp对象，避免被"****"覆盖
        emp.setPassword(originalEmployee.getPassword());

        // AutoFill 已为公共字段赋值
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(emp);
    }

}
