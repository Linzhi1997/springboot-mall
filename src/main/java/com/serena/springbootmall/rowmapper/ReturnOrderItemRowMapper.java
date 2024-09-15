package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.model.OrderItem;
import com.serena.springbootmall.model.ReturnOrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnOrderItemRowMapper implements RowMapper<ReturnOrderItem> {

    @Override
    public ReturnOrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReturnOrderItem returnOrderItem = new ReturnOrderItem();
        returnOrderItem.setReturnOrderItemId(rs.getInt("return_order_item_id"));
        returnOrderItem.setReturnOrderId(rs.getInt("return_order_id"));
        returnOrderItem.setProductId(rs.getInt("product_id"));
        returnOrderItem.setReturnQuantity(rs.getInt("return_quantity"));
        returnOrderItem.setRefundAmount(rs.getInt("refund_amount"));

        returnOrderItem.setProductName(rs.getString("product_name"));
        returnOrderItem.setImageUrl(rs.getString("image_url"));

        return returnOrderItem;
    }
}
