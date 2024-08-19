package com.serena.springbootmall.controller;

import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.ProductServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductControllerImpl {
    @Autowired
    ProductServer productServer;
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productServer.getProductById(id);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // build() 用於構建並返回一個空的 ResponseEntity，僅僅包含 HTTP 狀態碼和可能的回應header，而沒有任何回應體
        }
    }
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) //要加 @Valid @NotNull才會生效
    {
        Integer productId = productServer.createProduct(productRequest); // 預期createProduct會去資料庫中創建商品出來 & 返回id
        Product product = productServer.getProductById(productId); // by 返回id，從資料庫取得商品數據

        return ResponseEntity.status(HttpStatus.CREATED).body(product);// (前端輸入的值+getId)=product 回傳給前端
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId
                                                ,@RequestBody @Valid ProductRequest productRequest){
                                                // 限制前端只能修改ProductRequest的值
        // 檢查商品是否存在
        Product product = productServer.getProductById(productId);
        if(product==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 修改商品數據
        productServer.updateProduct(productId, productRequest); // ProductServer的updateProduct 返回值是Integer
        // 回傳修改後的數據 server dao不使用return減少data流量
        Product updateProduct = productServer.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

    }
}
