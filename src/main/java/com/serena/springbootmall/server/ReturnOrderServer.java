package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.dto.ReturnItem;
import com.serena.springbootmall.model.Order;
import com.serena.springbootmall.model.ReturnOrder;

import java.util.List;


public interface ReturnOrderServer {
    Integer createReturnOrder(Integer orderId, OrderRequest<ReturnItem> orderRequest);
    Integer countReturnOrder(OrderQueryParams orderQueryParams);
    List<ReturnOrder> getReturnOrdersByUserId(OrderQueryParams orderQueryParams);
    ReturnOrder getReturnOrderById(Integer returnOrderId);
}
