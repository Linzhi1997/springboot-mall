package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;


public class ProductRowMapper implements RowMapper<Product>
{
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setCategory(ProductCategory.valueOf(rs.getString("category")));
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreateDate(rs.getTimestamp("create_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return product;
    }
}
