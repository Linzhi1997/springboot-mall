package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.ReturnOrder;
import com.serena.springbootmall.model.ReturnOrderItem;

import java.util.List;

public interface ReturnOrderDao {

    Integer createReturnOrder(Integer userId,Integer orderId,Integer returnTotalAmount);

    void createReturnOrderItem(Integer returnOrderId, List<ReturnOrderItem> returnOrderItemList);

    ReturnOrder getReturnOrderById(Integer returnOrderId);

    List<ReturnOrder> getReturnOrdersByUserId(OrderQueryParams orderQueryParams);

    List<ReturnOrderItem> getReturnOrderItemsByOrderId(Integer returnOrderId);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
