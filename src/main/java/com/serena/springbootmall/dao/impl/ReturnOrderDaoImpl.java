package com.serena.springbootmall.dao.impl;

import com.serena.springbootmall.dao.ReturnOrderDao;
import com.serena.springbootmall.dto.OrderQueryParams;
import com.serena.springbootmall.model.ReturnOrder;
import com.serena.springbootmall.model.ReturnOrderItem;
import com.serena.springbootmall.rowmapper.ReturnOrderItemRowMapper;
import com.serena.springbootmall.rowmapper.ReturnOrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReturnOrderDaoImpl implements ReturnOrderDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Integer createReturnOrder(Integer userId,Integer orderId, Integer returnTotalAmount) {
        String sql = "INSERT INTO return_order (user_id, order_id, refund_total_amount, created_date) " +
                "VALUES (:userId,:orderId, :refundTotalAmount, :createdDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("orderId",orderId);
        map.put("refundTotalAmount",returnTotalAmount);
        map.put("createdDate",new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int returnOrderId = keyHolder.getKey().intValue();
        return returnOrderId;
    }

    @Override
    public void createReturnOrderItem(Integer returnOrderId, List<ReturnOrderItem> returnOrderItemList) {
        // 使用 batchUpdate 一次性加入數據
        String sql = "INSERT INTO return_order_item (return_order_id,product_id,return_quantity,refund_amount)" +
                " VALUES (:returnOrderId,:productId,:returnQuantity,:refundAmount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[returnOrderItemList.size()];

        for(int i=0;i<returnOrderItemList.size();i++){
            ReturnOrderItem returnOrderItem = returnOrderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("returnOrderId",returnOrderId);
            parameterSources[i].addValue("productId",returnOrderItem.getProductId());
            parameterSources[i].addValue("returnQuantity", returnOrderItem.getReturnQuantity());
            parameterSources[i].addValue("refundAmount", returnOrderItem.getRefundAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public ReturnOrder getReturnOrderById(Integer returnOrderId) {
        String sql ="SELECT return_order_id, order_id, user_id, refund_total_amount, created_date " +
                "FROM return_order WHERE return_order_id=:returnOrderId";
        Map<String,Object> map = new HashMap<>();

        map.put("returnOrderId",returnOrderId);

        List<ReturnOrder> returnOrderList = namedParameterJdbcTemplate.query(sql,map,new ReturnOrderRowMapper());

        if (returnOrderList.size() > 0) {
            return returnOrderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ReturnOrder> getReturnOrdersByUserId(OrderQueryParams orderQueryParams) {
        String sql = "SELECT return_order_id,order_id, user_id, refund_total_amount, created_date " +
                    "FROM return_order WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        // 查詢條件：ID
        sql = addFilterQuery(sql, map, orderQueryParams);
        // 排序（固定）
        sql = sql + " ORDER BY created_date DESC ";
        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset ";
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<ReturnOrder> returnOrderList = namedParameterJdbcTemplate.query(sql,map,new ReturnOrderRowMapper());
        return returnOrderList;
    }

    @Override
    public List<ReturnOrderItem> getReturnOrderItemsByOrderId(Integer returnOrderId) {
        String sql = "SELECT roi.return_order_item_id, roi.return_order_id, roi.product_id , roi.return_quantity, roi.refund_amount, p.product_name, p.image_url " +
                "FROM return_order_item as roi " +
                "LEFT JOIN product as p " +
                "ON roi.product_id = p.product_id " +
                "WHERE roi.return_order_id = :returnOrderId ";

        Map<String,Object> map = new HashMap<>();
        map.put("returnOrderId",returnOrderId);
        List<ReturnOrderItem> returnOrderItemList = namedParameterJdbcTemplate.query(sql, map, new ReturnOrderItemRowMapper());

        return returnOrderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        // 計算符合條件的總筆數
        String sql = "SELECT COUNT(*) FROM return_order WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilterQuery(sql,map,orderQueryParams);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return count;
    }

    private String addFilterQuery(String sql,Map<String,Object> map,OrderQueryParams orderQueryParams){
        if(orderQueryParams.getUsersId() != null) {
            sql = sql + "and user_id=:userId";
            map.put("userId", orderQueryParams.getUsersId());
        }
        return sql;
    }
}
