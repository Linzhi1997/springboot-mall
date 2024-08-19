package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServerImpl implements ProductServer {

    @Autowired
    ProductDao productDao;
    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    public Integer createProduct(ProductRequest productRequest){
        return productDao.createProduct(productRequest);
    }
}
