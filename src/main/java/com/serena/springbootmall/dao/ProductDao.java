package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import org.springframework.stereotype.Component;


public interface ProductDao {

    public Product getProductById(Integer id);

    public Integer createProduct(ProductRequest productRequest);
}
