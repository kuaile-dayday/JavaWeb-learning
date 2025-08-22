package org.example.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.Emp;
import org.example.pojo.OperateLog;
import org.example.pojo.PageResult;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /*
     * 操作日志分页查询
     * */
    @Override
    public PageResult<OperateLog> page(Integer page, Integer pageSize) {
//        1、设置分页参数
        PageHelper.startPage(page, pageSize);
//        2、执行查询
        List<OperateLog> operateLogslist = operateLogMapper.list(page, pageSize);

//        3、解析查询结果，并封装
        Page<OperateLog> p =(Page<OperateLog>) operateLogslist;
        return new PageResult<OperateLog>(p.getTotal(),p.getResult());
    }
}
