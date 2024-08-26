package com.serena.springbootmall.controller;

import com.serena.springbootmall.dto.CreateOrderRequest;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.Order;
import com.serena.springbootmall.server.OrderServer;
import com.serena.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class OrderController {

    @Autowired
    OrderServer orderServer;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderServer.createOrder(userId, createOrderRequest);

        Order order = orderServer.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>>getOrders(@PathVariable Integer userId,
                                                @RequestParam(defaultValue ="10") @Max(1000) @Min(0) Integer limit,
                                                @RequestParam(defaultValue ="0") @Max(0) @Min(0)   Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUsersId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
        // 取得該order list
        List<Order> orderlist = orderServer.getOrders(orderQueryParams);
        // 取得訂單總數
        Integer count = orderServer.countOrder(orderQueryParams);
        // 分頁
        // Page是一個自創的generic
        // 以下設定返回值
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderlist);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
