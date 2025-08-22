package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.LoginInfo;
import org.example.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface EmpService {
    /*
    * 条件分页查询
    * @param page 页码
    * @param pageSize 每页记录数
    * */
//    PageResult<Emp> page(Integer page, Integer pageSize,
//                         String name, Integer gender,
//                         LocalDate begin,LocalDate end);

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /*
    * 新增员工
    * */
    void save(Emp emp);

    /*
    * 删除员工
    * */
    void delete(List<Integer> ids);

    /*
    * 根据id实现查询
    * */
    Emp getInfo(Integer id);

    /*
    * 修改员工
    * */
    void update(Emp emp);

    /*
    * 查询所有班主任员工
    * */
    List<Emp> listMaster();

    /*
    * 员工登录
    * */
    LoginInfo login(Emp emp);
}
