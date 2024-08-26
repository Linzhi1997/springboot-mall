package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order>{

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        // ResultSet取得SELECT出來的那些數據
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCreatedDate(rs.getDate("created_date"));
        order.setLastModifiedDate(rs.getDate("last_modified_date"));

        return order;
    }
}
