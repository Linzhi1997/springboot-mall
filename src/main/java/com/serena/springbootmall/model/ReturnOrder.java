package com.serena.springbootmall.model;

import java.util.Date;
import java.util.List;

public class ReturnOrder {
    private Integer returnOrderId;
    private Integer userId;
    private Integer orderId;
    private Integer refundTotalAmount;
    private Date    createdDate;

    private List<ReturnOrderItem> returnOrderList;

    public Integer getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(Integer returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(Integer refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<ReturnOrderItem> getReturnOrderList() {
        return returnOrderList;
    }

    public void setReturnOrderList(List<ReturnOrderItem> returnOrderList) {
        this.returnOrderList = returnOrderList;
    }
}
