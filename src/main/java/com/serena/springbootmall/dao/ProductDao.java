package com.serena.springbootmall.dao;

import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import java.util.*;


public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void updateStock(Integer productId,Integer updatedStock);

    void deleteProduct(Integer productId);

    Integer getTotal(ProductQueryParams productQueryParams);

}
