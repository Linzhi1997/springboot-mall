package com.serena.springbootmall.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("待處理")
    PENDING,
    @JsonProperty("已支付")
    PAID,
    @JsonProperty("訂單正在處理中")
    PROCESSING,
    @JsonProperty("訂單被取消")
    CANCELLED,
    @JsonProperty("已發貨")
    SHIPPED,
    @JsonProperty("已送達")
    DELIVERED,
    @JsonProperty("退貨/部分退貨")
    PARTIALLY_RETURNED,
    @JsonProperty("退貨/全部退貨")
    FULLY_RETURNED,
    @JsonProperty("退款已完成")
    REFUNDED
}
