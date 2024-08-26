package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.Order;
import com.serena.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
}
