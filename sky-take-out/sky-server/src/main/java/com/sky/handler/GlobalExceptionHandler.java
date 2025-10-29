package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /*
    * 处理 SQL异常
    * */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
//        控制台报错话语：Duplicate entry 'zhangsan' for key 'idx_username'
        String message = ex.getMessage();
//        如果存在关键报错话语 此时要来找到重复的用户名
        if (message.contains("Duplicate entry")){
//            对报错话语进行切割 可以看到是空格隔开 放进数组后 'zhangsan'在第三个位置 故索引为2
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username+"已存在";
            return Result.error(msg);
        }
        else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
