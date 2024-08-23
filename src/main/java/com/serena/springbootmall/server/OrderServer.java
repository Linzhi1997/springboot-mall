package com.serena.springbootmall.server;

import com.serena.springbootmall.dto.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderServer {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
