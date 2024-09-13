package com.serena.springbootmall.dto;

import jakarta.validation.constraints.NotNull;

public class BuyItem {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;

    public @NotNull Integer getProductId() {
        return productId;
    }

    public void setProductId(@NotNull Integer productId) {
        this.productId = productId;
    }

    public @NotNull Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull Integer quantity) {
        this.quantity = quantity;
    }
}
