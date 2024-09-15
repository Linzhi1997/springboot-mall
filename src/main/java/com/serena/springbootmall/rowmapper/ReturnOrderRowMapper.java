package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.model.ReturnOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnOrderRowMapper implements RowMapper<ReturnOrder> {
    @Override
    public ReturnOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setReturnOrderId(rs.getInt("return_order_id"));
        returnOrder.setOrderId(rs.getInt("order_id"));
        returnOrder.setUserId(rs.getInt("user_id"));
        returnOrder.setRefundTotalAmount(rs.getInt("refund_total_amount"));
        returnOrder.setCreatedDate(rs.getDate("created_date"));

        return returnOrder;
    }
}
