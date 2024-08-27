package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.OrderDao;
import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dao.UserDao;
import com.serena.springbootmall.dto.BuyItem;
import com.serena.springbootmall.dto.CreateOrderRequest;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.Order;
import com.serena.springbootmall.model.OrderItem;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.model.User;
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
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getById(userId);

        if(user==null){
            log.warn("該user {}不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        // buyItem 是循環變量，在每次loop中，它會被賦值為 buyItemList 中的下一個 buyItem 對象
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            //
            Product product = productDao.getProductById(buyItem.getProductId());
            // 檢查product是否存在，庫存數量是否足夠
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
    public void deleteOrder(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        if(order==null){
            log.warn("此筆訂單不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        orderDao.deleteOrder(orderId);
    }
}
