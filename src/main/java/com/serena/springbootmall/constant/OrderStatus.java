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
    @JsonProperty("退貨申請 / 處理中")
    RETURNE_IN_PROGRESS,
    @JsonProperty("退貨完成")
    RETURNE_COMPLETED
}
