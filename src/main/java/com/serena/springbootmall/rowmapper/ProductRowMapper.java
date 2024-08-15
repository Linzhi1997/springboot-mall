package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;


public class ProductRowMapper implements RowMapper<Product> // 表示要轉換成 Product 的形式
{

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        // ResultSet去取controller傳入的參數，放到sql"目的名字"
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setCategory(rs.getString("category"));
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreateTime(rs.getTime("create_date "));
        product.setLastModifiedTime(rs.getTime("late_modified_date"));
        // set完畢 return product
        return product;
    }
}
