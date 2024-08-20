package com.serena.springbootmall.dto;

import com.serena.springbootmall.constant.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductQueryParams {
    ProductCategory productCategory;
    String search;
    String byOrder;
    String sort;
    Integer limit;
    Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getByOrder() {
        return byOrder;
    }

    public void setByOrder(String byOrder) {
        this.byOrder = byOrder;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

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
