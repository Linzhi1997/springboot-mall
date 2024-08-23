package com.serena.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.*;

public class CreateOrderRequest {
    // 一個訂單
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
