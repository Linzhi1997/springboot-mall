package com.serena.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.*;

public class OrderRequest<T> {
    @NotEmpty
    private List<T> itemList;

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }
}
