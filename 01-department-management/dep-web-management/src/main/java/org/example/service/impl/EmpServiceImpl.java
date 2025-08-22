package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

//    -------------  原始分页查询  --------------------
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
////        1、调用mapper接口，查询总记录数
//        Long total = empMapper.count();
////        2、调用mapper接口，查询结果列表  注意传进来的是页码和页码数据数，但是mapper层用的是start 起始索引
//        Integer start = (page - 1) * pageSize;
//        List<Emp> rows = empMapper.list(start,pageSize);
////        3、封装结果
//        return new PageResult<Emp>(total,rows);


/*
*  pageHelper 分页查询
* */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize, String name,
//                                Integer gender, LocalDate begin, LocalDate end) {
//
////        1、设置分页参数
//        PageHelper.startPage(page, pageSize);
////        2、执行查询
//        List<Emp> emplist = empMapper.list(name,gender,begin,end);
//
////        3、解析查询结果，并封装
//        Page<Emp> p =(Page<Emp>) emplist;
//        return new PageResult<Emp>(p.getTotal(),p.getResult());
//
//
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {

//        1、设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
//        2、执行查询
        List<Emp> emplist = empMapper.list(empQueryParam);

//        3、解析查询结果，并封装
        Page<Emp> p =(Page<Emp>) emplist;
        return new PageResult<Emp>(p.getTotal(),p.getResult());


    }

    /*
    * 新增员工
    * */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
//        1、保存员工基本信息（前端传进来的数据没有 创建时间、修改时间，所以补全
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);


//        2、保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(empExpr->{empExpr.setEmpId(emp.getId());});
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工："+emp);
            empLogService.insertLog(empLog);
        }




    }


    /*
    * 删除员工
    * */
    @Override
    @Transactional(rollbackFor = {Exception.class})// 由于有多个数据库表的操作 所以添加事务 同时成功同时失败
    public void delete(List<Integer> ids) {
//        1、删除员工基本信息
        empMapper.deleteByIds(ids);

//        2、批量删除员工的工作信息
        empExprMapper.deleteByEmpIds(ids);

    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Emp emp) {
//        1、根据id修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
//        2、根据id修改员工的工作经历
//        2.1 先删除
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
//        2.2 再添加
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr->{empExpr.setEmpId(emp.getId());});
            empExprMapper.insertBatch(exprList);
        }
    }

    /*
    * 查询全部班主任员工
    * */
    @Override
    public List<Emp> listMaster() {
        List<Emp> masterList = empMapper.listMaster();
        return masterList;
    }

    /*
    * 员工登录
    * */
    @Override
    public LoginInfo login(Emp emp) {
        // 1. 调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        // 2. 判断：判断是否存在这个员工，如果存在，组装登录成功信息
        if (e != null) {
            log.info("登录成功，员工信息为：｛｝",e);
            // 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(),e.getUsername(),e.getPassword(),e.getName(),jwt);
        }

        // 3. 不存在，返回null
        return null;


    }
}
