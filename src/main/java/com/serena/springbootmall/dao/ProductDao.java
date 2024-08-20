package com.serena.springbootmall.dao;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import java.util.*;


public interface ProductDao {

    public List<Product> getProducts(ProductQueryParams productQueryParams);

    public Product getProductById(Integer id);

    public Integer createProduct(ProductRequest productRequest);

    public void updateProduct(Integer productId,ProductRequest productRequest);

    public void deleteProduct(Integer productId);

}
