package com.serena.springbootmall.dao.impl;

import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer id) {
        String sql ="SELECT product_name, category, image_url, price, stock, description, create_date, late_modified_date " +
                "FROM product " +
                "WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",id); // put(key, value)

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0); // productList是搜尋後的結果
        }
        else {
            return null;
        }
    }
}
