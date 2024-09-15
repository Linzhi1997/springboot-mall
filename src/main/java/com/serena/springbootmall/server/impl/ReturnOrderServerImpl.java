package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.constant.OrderStatus;
import com.serena.springbootmall.dao.*;
import com.serena.springbootmall.dto.*;
import com.serena.springbootmall.model.*;
import com.serena.springbootmall.server.ReturnOrderServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReturnOrderServerImpl implements ReturnOrderServer {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ReturnOrderDao returnOrderDao;
    @Autowired
    ProductDao productDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServerImpl.class);

    @Override
    public Integer createReturnOrder(Integer orderId, OrderRequest<ReturnItem> orderRequest) {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            log.warn("此筆訂單不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 退貨訂單金額
        int returnTotalAmount = 0;
        // 退貨細項
        List<ReturnItem> returnItemList = orderRequest.getItemList();
        // 創建退貨單
        List<ReturnOrderItem> returnOrderItemList = new ArrayList<>();

        boolean isFullyReturned = true;

        // 遍歷退貨細項
        for (ReturnItem returnItem : returnItemList) {
            // 獲取原訂單細項
            OrderItem originalOrderItem = orderDao.getOrderItemById(returnItem.getOrderItemId());
            int originalProductId = originalOrderItem.getProductId();
            int originalAmount = originalOrderItem.getAmount();
            int originalQuantity = originalOrderItem.getQuantity();
            // 退貨數量
            int returnQuantity = returnItem.getQuantity();
            if (originalOrderItem == null) {
                log.warn("此筆品項不存在於此訂單");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (returnQuantity > originalQuantity) {
                log.warn("退貨數量 大於原訂單數量");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (returnQuantity < originalQuantity){
                isFullyReturned = false; // 判斷是否全部退回
            }

            if (isFullyReturned == false) {
                orderDao.update(orderId, OrderStatus.PARTIALLY_RETURNED);
            } else {
                orderDao.update(orderId, OrderStatus.FULLY_RETURNED);
            }

            // 退回庫存
            Product product = productDao.getProductById(originalProductId);
            productDao.updateStock(originalProductId, product.getStock() + returnItem.getQuantity());

            // 計算總價錢
            int returnAmount = returnItem.getQuantity() * (originalAmount/originalQuantity);
            returnTotalAmount = returnTotalAmount + returnAmount;

            // 創建退貨訂單項目
            ReturnOrderItem returnOrderItem = new ReturnOrderItem();
            returnOrderItem.setProductId(originalProductId);
            returnOrderItem.setReturnQuantity(returnQuantity);
            returnOrderItem.setRefundAmount(returnAmount);

            returnOrderItemList.add(returnOrderItem);
        }
        // 創建退貨訂單
        Integer returnOrderId = returnOrderDao.createReturnOrder(order.getUserId(),orderId, returnTotalAmount); // order的totalAmount
        // 創建退貨訂單細項
        returnOrderDao.createReturnOrderItem(returnOrderId, returnOrderItemList);

        return returnOrderId;
    }

    @Override
    public Integer countReturnOrder(OrderQueryParams orderQueryParams) {
        return returnOrderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<ReturnOrder> getReturnOrdersByUserId(OrderQueryParams orderQueryParams) {
        // 遍歷此userId下的 - returnOrder
        List<ReturnOrder> returnOrderList = returnOrderDao.getReturnOrdersByUserId(orderQueryParams);
        List<ReturnOrder> detailedReturnOrderList = new ArrayList<>();
        // 遍歷此returnOrder下的 - returnOrderItem
        for (int i = 0; i < returnOrderList.size(); i++) {
            detailedReturnOrderList.add(getReturnOrderById(returnOrderList.get(i).getReturnOrderId()));
        }
        return detailedReturnOrderList;
    }


    @Override
    public ReturnOrder getReturnOrderById(Integer returnOrderId) {
        ReturnOrder returnOrder = returnOrderDao.getReturnOrderById(returnOrderId);
        List<ReturnOrderItem> returnOrderItemList = returnOrderDao.getReturnOrderItemsByOrderId(returnOrderId);
        returnOrder.setReturnOrderList(returnOrderItemList);
        return returnOrder;
    }
}
