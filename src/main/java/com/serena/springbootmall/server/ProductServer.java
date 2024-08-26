package com.serena.springbootmall.server;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import java.util.*;

public interface ProductServer {
    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProduct(Integer productId);

    Integer getTotal(ProductQueryParams productQueryParams);
}
