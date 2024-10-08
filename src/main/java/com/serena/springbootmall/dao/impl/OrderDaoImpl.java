package com.serena.springbootmall.dao.impl;


import com.serena.springbootmall.constant.OrderStatus;
import com.serena.springbootmall.dao.OrderDao;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.*;
import com.serena.springbootmall.rowmapper.OrderItemRowMapper;
import com.serena.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id,user_id, total_amount,order_status, created_date, last_modified_date FROM `order` WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 查詢條件：ID
        sql = addFilterQuery(sql, map, orderQueryParams);
        // 排序（固定）
        sql = sql + " ORDER BY created_date DESC ";
        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset ";
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        // 計算符合條件的總筆數
        String sql = "SELECT COUNT(*) FROM `order` WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilterQuery(sql,map,orderQueryParams);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return count;
    }

    @Override
    public void update(Integer orderId, OrderStatus orderStatus) {
        String sql = "UPDATE `order` SET order_status=:orderStatus WHERE order_id = :orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("orderStatus",orderStatus.toString());

        namedParameterJdbcTemplate.update(sql,map);
    }

    private String addFilterQuery(String sql,Map<String,Object> map,OrderQueryParams orderQueryParams){
        if(orderQueryParams.getUsersId() != null) {
            sql = sql + "and user_id=:userId";
            map.put("userId", orderQueryParams.getUsersId());
        }
        return sql;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql ="SELECT order_id,user_id, total_amount,order_status, created_date, last_modified_date " +
                "FROM `order` WHERE order_id=:orderId";
        Map<String,Object> map = new HashMap<>();

        map.put("orderId",orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public OrderItem getOrderItemById(Integer orderItemId) {
        String sql ="SELECT oi.order_item_id, oi.order_id, oi.product_id , oi.quantity, oi.amount,p.product_name,p.image_url " +
                    " FROM order_item as oi LEFT JOIN product as p ON oi.product_id = p.product_id " +
                    " WHERE order_item_id=:orderItemId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderItemId",orderItemId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

        if (orderItemList.size() > 0) {
            return orderItemList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id , oi.quantity, oi.amount,p.product_name,p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p " +
                "ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId ";

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    // 創建訂單
    @Override
    public Integer createOrder(Integer userId,Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id, total_amount,order_status, created_date, last_modified_date) " +
                "VALUES (:userId, :totalAmount, :orderStatus, :createdDate, :lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        map.put("orderStatus", OrderStatus.PENDING.toString());
        Date now= new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        // 使用 batchUpdate 一次性加入數據
        String sql = "INSERT INTO order_item (order_id,product_id,quantity,amount)" +
                " VALUES (:orderId,:productId,:quantity,:amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for(int i=0;i<orderItemList.size();i++){
            OrderItem orderItem =orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }



}

