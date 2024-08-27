package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.CreateOrderRequest;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.Order;

import java.util.List;

public interface OrderServer {

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer userId);

    void deleteOrder(Integer orderId);
}
