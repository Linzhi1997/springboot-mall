package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.CreateOrderRequest;
import com.serena.springbootmall.model.Order;

public interface OrderServer {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer userId);
}
