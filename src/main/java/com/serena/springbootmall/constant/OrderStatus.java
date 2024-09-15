package com.serena.springbootmall.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("待處理")
    PENDING,
    @JsonProperty("已支付")
    PAID,
    @JsonProperty("已發貨")
    SHIPPED,
    @JsonProperty("已送達")
    DELIVERED,
    @JsonProperty("退貨申請 / 部分退貨")
    PARTIALLY_RETURNED,
    @JsonProperty("退貨申請 / 全部退貨")
    FULLY_RETURNED
}
