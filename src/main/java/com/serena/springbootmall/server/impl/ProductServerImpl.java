package com.serena.springbootmall.server.impl;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ProductServerImpl implements ProductServer {

    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        return productDao.createProduct(productRequest); // 把參數一路傳到dao，預設productDao回傳是Integer
    }

    @Override
    public void updateProduct(Integer productId,ProductRequest productRequest){
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
