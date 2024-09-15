package com.serena.springbootmall.controller;

import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.dto.ReturnItem;
import com.serena.springbootmall.model.ReturnOrder;
import com.serena.springbootmall.server.ReturnOrderServer;
import com.serena.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReturnOrderController {
    @Autowired
    ReturnOrderServer returnOrderServer;

    @PostMapping("/orders/{orderId}/return-orders")
    public ResponseEntity<ReturnOrder> createReturnOrder(@PathVariable Integer orderId,
                                                        @RequestBody @Valid OrderRequest<ReturnItem> orderRequest){
        Integer returnOrderId = returnOrderServer.createReturnOrder(orderId,orderRequest);
        ReturnOrder returnOrder = returnOrderServer.getReturnOrderById(returnOrderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnOrder);
    }


    @GetMapping("/users/{userId}/return-orders")
    public ResponseEntity<Page<ReturnOrder>> getUserReturnOrders(@PathVariable Integer userId,
                                                                 @RequestParam(defaultValue ="10") @Max(1000) @Min(0) Integer limit,
                                                                 @RequestParam(defaultValue ="0") @Max(10) @Min(0)   Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUsersId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
        // 取得該order list
        List<ReturnOrder> returnOrderList = returnOrderServer.getReturnOrdersByUserId(orderQueryParams);
        // 取得訂單總數
        Integer count = returnOrderServer.countReturnOrder(orderQueryParams);
        // 分頁
        Page<ReturnOrder> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(returnOrderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

}
