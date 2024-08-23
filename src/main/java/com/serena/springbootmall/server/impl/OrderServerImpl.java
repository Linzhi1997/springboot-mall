package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.OrderDao;
import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dto.BuyItem;
import com.serena.springbootmall.dto.CreateOrderRequest;
import com.serena.springbootmall.model.OrderItem;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.OrderServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Component
public class OrderServerImpl implements OrderServer {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        // buyItem 是循環變量，在每次迭代中，它會被賦值為 buyItemList 中的下一個 BuyItem 對象
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount +amount;

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
}
