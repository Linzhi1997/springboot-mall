package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.OrderDao;
import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dao.UserDao;
import com.serena.springbootmall.dto.BuyItem;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.dto.ReturnItem;
import com.serena.springbootmall.model.*;
import com.serena.springbootmall.server.OrderServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Component
public class OrderServerImpl implements OrderServer {
    // 注入多個 bean
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServerImpl.class);

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);
        // 遍歷此userId下的所有order
        for(Order order:orderList){
            // 獲取每筆訂單詳細資訊，擴充返回order
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {

        return orderDao.countOrder(orderQueryParams);
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, OrderRequest<BuyItem> orderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getById(userId);

        if(user==null){
            log.warn("該user {}不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem buyItem : orderRequest.getItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
            // 檢查product是否存在
            // 檢查庫存數量是否足夠
            if(product==null){
                log.warn("該商品不存在");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(buyItem.getQuantity() > product.getStock()){
                log.warn("該商品{}，已達購買最高上限:{}，欲購買數量:{}",
                        product.getProductName(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            // 扣除庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount); // orderItem的Amount

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount); // order的totalAmount
        // 創建訂單細項
        orderDao.createOrderItem(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);
        // 加入購買商品資訊
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    public void returnOrder(Integer orderId,OrderRequest<ReturnItem> orderRequest) {
        Order order = orderDao.getOrderById(orderId);
        if (order == null) {
            log.warn("此筆訂單不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 原訂單金額
        int returnTotalAmount = order.getTotalAmount();
        // 退貨細項
        List<ReturnItem> returnItemList = orderRequest.getItemList();
        // 創建退貨單
        List<ReturnOrderItem> returnOrderItemList = new ArrayList<>();

        boolean isFullyReturned = false;

        // 遍歷退貨細項
        for (ReturnItem returnItem : returnItemList) {
            // 獲取原訂單細項
            OrderItem originalOrderItem = orderDao.getOrderItemById(returnItem.getOrderItemId());
            int originalOrderItemId = originalOrderItem.getOrderItemId();
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

            // 退回庫存
            Product product = productDao.getProductById(originalProductId);
            productDao.updateStock(originalProductId, product.getStock() + returnItem.getQuantity());

            // 計算總價錢
            int returnAmount = returnItem.getQuantity() * (originalAmount/originalQuantity);
            returnTotalAmount = returnTotalAmount + returnAmount;

            // 創建退貨訂單項目
            ReturnOrderItem returnOrderItem = new ReturnOrderItem();
            returnOrderItem.setProductId(originalProductId);
            returnOrderItem.setQuantity(returnQuantity);
            returnOrderItem.setRefundAmount(returnAmount);

            returnOrderItemList.add(returnOrderItem);
        }

        int Amount = order.getTotalAmount() - returnTotalAmount ;

        // 更改訂單註解
        Integer orderId = orderDao.createOrder(userId,totalAmount); // order的totalAmount
        // 創建訂單細項
        orderDao.createReturnOrderItem(orderId, returnOrderItemList);

    }
}
