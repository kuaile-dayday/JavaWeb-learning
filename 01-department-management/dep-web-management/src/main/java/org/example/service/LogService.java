package org.example.service;

import org.example.pojo.OperateLog;
import org.example.pojo.PageResult;

public interface LogService {

    /*
    * 操作日志分页查询
    * */
    PageResult<OperateLog> page(Integer page, Integer pageSize);
}
