package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    * 设置店铺的营业状态
    * */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }

    /*
    * 获取店铺的营业状态
    * */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result getStatus(){
        // 这里默认传回来的是 object，前面存入状态类型是integer 所以这里强转回来
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取当前店铺的营业状态：{}",shopStatus == 1 ? "营业中" : "打烊中");
        return Result.success(shopStatus);
    }

}
