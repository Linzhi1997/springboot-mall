package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.BuyItem;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.Order;

import java.util.List;

public interface OrderServer {

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, OrderRequest<BuyItem> orderRequest);

    Order getOrderById(Integer userId);

}
