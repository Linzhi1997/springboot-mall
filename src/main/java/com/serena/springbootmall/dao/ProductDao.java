package com.serena.springbootmall.dao;

import com.serena.springbootmall.model.Product;
import org.springframework.stereotype.Component;


public interface ProductDao {

    public Product getProductById(Integer id);
}
