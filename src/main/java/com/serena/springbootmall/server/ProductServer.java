package com.serena.springbootmall.server;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import java.util.*;

public interface ProductServer
{
    public List<Product> getProducts(ProductCategory productCategory, String search);

    public Product getProductById(Integer id);

    public Integer createProduct(ProductRequest productRequest);

    public void updateProduct(Integer productId,ProductRequest productRequest);

    public void deleteProduct(Integer productId);
}
