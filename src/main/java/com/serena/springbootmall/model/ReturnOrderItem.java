package com.serena.springbootmall.model;

public class ReturnOrderItem {
    private Integer ReturnOrderItemId;
    private Integer ReturnOrderId;
    private Integer productId;
    private Integer returnQuantity;
    private Integer refundAmount;
    // 擴充
    private String productName;
    private String imageUrl;

    public Integer getReturnOrderItemId() {
        return ReturnOrderItemId;
    }

    public void setReturnOrderItemId(Integer returnOrderItemId) {
        ReturnOrderItemId = returnOrderItemId;
    }

    public Integer getReturnOrderId() {
        return ReturnOrderId;
    }

    public void setReturnOrderId(Integer returnOrderId) {
        ReturnOrderId = returnOrderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
