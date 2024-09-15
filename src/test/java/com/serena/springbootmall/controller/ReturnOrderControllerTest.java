package com.serena.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.serena.springbootmall.dto.OrderRequest;
import com.serena.springbootmall.dto.ReturnItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ReturnOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // 創建訂單
    @Transactional
    @Test
    public void createReturnOrder_success() throws Exception {

        OrderRequest returnOrderRequest = new OrderRequest();
        List<ReturnItem> returnItemList = new ArrayList<>();

        ReturnItem returnItem1 = new ReturnItem();
        returnItem1.setOrderItemId(1);
        returnItem1.setQuantity(3);
        returnItemList.add(returnItem1);

        ReturnItem returnItem2 = new ReturnItem();
        returnItem2.setOrderItemId(2);
        returnItem2.setQuantity(2);
        returnItemList.add(returnItem2);

        ReturnItem returnItem3 = new ReturnItem();
        returnItem3.setOrderItemId(3);
        returnItem3.setQuantity(1);
        returnItemList.add(returnItem3);

        returnOrderRequest.setItemList(returnItemList);

        String json = objectMapper.writeValueAsString(returnOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/{orderId}/return-orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.returnOrderId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.orderId", equalTo(1)))
                .andExpect(jsonPath("$.returnOrderList", hasSize(3)))
                .andExpect(jsonPath("$.refundTotalAmount",equalTo(500690)))
                .andExpect(jsonPath("$.createdDate", notNullValue()));
    }

    @Transactional
    @Test
    public void createReturnOrder_illegalArgument_emptyReturnItemList() throws Exception {
        OrderRequest returnOrderRequest = new OrderRequest();
        List<ReturnItem> returnItemList = new ArrayList<>();
        returnOrderRequest.setItemList(returnItemList);

        String json = objectMapper.writeValueAsString(returnOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/{orderId}/return-orders", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void createReturnOrder_orderNotExist() throws Exception {
        OrderRequest returnOrderRequest = new OrderRequest();
        List<ReturnItem> returnItemList = new ArrayList<>();

        ReturnItem returnItem = new ReturnItem();
        returnItem.setOrderItemId(1);
        returnItem.setQuantity(1);
        returnItemList.add(returnItem);

        returnOrderRequest.setItemList(returnItemList);

        String json = objectMapper.writeValueAsString(returnOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/{orderId}/return-orders", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void createReturnOrder_quantityExceed() throws Exception {
        OrderRequest returnOrderRequest = new OrderRequest();
        List<ReturnItem> returnItemList = new ArrayList<>();

        ReturnItem returnItem = new ReturnItem();
        returnItem.setOrderItemId(1);
        returnItem.setQuantity(10000);
        returnItemList.add(returnItem);

        returnOrderRequest.setItemList(returnItemList);

        String json = objectMapper.writeValueAsString(returnOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/orders/{orderId}/return-orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // 查詢訂單列表
    @Test
    public void getReturnOrders() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/return-orders", 2);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results[0].returnOrderId", equalTo(1)))
                .andExpect(jsonPath("$.results[0].userId", equalTo(2)))
                .andExpect(jsonPath("$.results[0].orderId", equalTo(2)))
                .andExpect(jsonPath("$.results[0].refundTotalAmount", equalTo(100000)))
                .andExpect(jsonPath("$.results[0].createdDate", notNullValue()));
    }

    @Test
    public void getReturnOrders_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/return-orders", 2)
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }

    @Test
    public void getReturnOrders_userHasNoReturnOrder() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/return-orders", 3);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }

    @Test
    public void getReturnOrders_userNotExist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/return-orders", 100);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(0)));
    }

}