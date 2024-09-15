package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.model.Order;
import com.serena.springbootmall.model.OrderItem;
import com.serena.springbootmall.model.ReturnOrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    OrderItem getOrderItemById(Integer orderItemId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
