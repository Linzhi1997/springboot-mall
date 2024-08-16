package com.serena.springbootmall.rowmapper;

import com.serena.springbootmall.constant.ProductCategory;
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

        String categoryStr = rs.getString("category"); // 從database取出來是String類型的字串
        ProductCategory category = ProductCategory.valueOf(categoryStr); // Stirng轉化成Enum類型
        product.setCategory(category); // 到set方法內做設定
//        product.setCategory(ProductCategory.valueOf(rs.getString("category")));

        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreateDate(rs.getTime("create_date"));
        product.setLastModifiedDate(rs.getTime("last_modified_date"));
        // set完畢 return product
        return product;
    }
}
