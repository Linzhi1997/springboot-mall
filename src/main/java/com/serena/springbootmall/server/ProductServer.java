package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;

public interface ProductServer
{
    public Product getProductById(Integer id);

    public Integer createProduct(ProductRequest productRequest);
}
