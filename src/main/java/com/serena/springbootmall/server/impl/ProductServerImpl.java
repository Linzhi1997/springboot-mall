package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.ProductServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ProductServerImpl implements ProductServer {

    @Autowired
    ProductDao productDao;
    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    public Integer createProduct(ProductRequest productRequest){
        return productDao.createProduct(productRequest); // 把參數一路傳到dao，預設productDao回傳是Integer
    }

    public void updateProduct(Integer productId,ProductRequest productRequest){
        productDao.updateProduct(productId,productRequest);
    }

}
