package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import java.util.*;

public interface ProductServer
{
    public List<Product> getProducts();

    public Product getProductById(Integer id);

    public Integer createProduct(ProductRequest productRequest);

    public void updateProduct(Integer productId,ProductRequest productRequest);

    public void deleteProduct(Integer productId);
}
