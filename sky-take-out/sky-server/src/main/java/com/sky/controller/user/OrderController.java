package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "C端订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /*
    * 历史订单查询
    * status: 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
    * */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result page(int page,int pageSize,Integer status){
        log.info("历史订单查询");
        PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
        return Result.success(pageResult);
    }

    /*
    * 查看订单详情
    * */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查看订单详情")
    public Result details(@PathVariable Long id){
        log.info("查看订单id {} 的详情", id);
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /*
    * 用户取消订单
    * */
    @PutMapping("/cancel/{id}")
    @ApiOperation("用户取消订单")
    public Result cancel(@PathVariable Long id) throws Exception{
        log.info("用户取消订单 {}", id);
        orderService.userCancelById(id);
        return Result.success();
    }

    /*
    * 再来一单
    * */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result reOrder(@PathVariable Long id){
        log.info("再来一单 {}", id);
        orderService.repetition(id);
        return Result.success();
    }

}
