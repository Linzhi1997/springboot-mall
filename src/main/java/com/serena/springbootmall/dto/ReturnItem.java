package com.serena.springbootmall.dto;

import jakarta.validation.constraints.NotNull;

public class ReturnItem {
    @NotNull
    private Integer orderItemId;
    @NotNull
    private Integer quantity;

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
