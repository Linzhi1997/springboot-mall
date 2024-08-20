package com.serena.springbootmall.dto;

import com.serena.springbootmall.constant.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductQueryParams {
    ProductCategory productCategory;
    String search;

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
