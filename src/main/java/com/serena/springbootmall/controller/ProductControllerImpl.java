package com.serena.springbootmall.controller;

import com.serena.springbootmall.constant.ProductCategory;
import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.server.ProductServer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated //使@Max(100) @Min(0)生效
@RestController
public class ProductControllerImpl {

    @Autowired
    ProductServer productServer;
    @Autowired
    ProductQueryParams productQueryParams;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            // 查詢條件 filtering
            @RequestParam (required = false) ProductCategory productCategory, //前端自動String轉換Enum
            @RequestParam (required = false) String search,
            // 排序 Soring
            @RequestParam (defaultValue = "create_date") String byOrder,
            @RequestParam (defaultValue = "desc") String sort,
            // 分頁 Paging
            @RequestParam (defaultValue = "5") @Max(100) @Min(0) Integer limit,
            @RequestParam (defaultValue = "0") @Min(0) Integer offset
    ) {
        productQueryParams.setProductCategory(productCategory);
        productQueryParams.setSearch(search);
        productQueryParams.setByOrder(byOrder);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> products = productServer.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(products);
        // Reatful API特色
        // 查詢的物件是List<Product> 就算列表為空 HttpStatus=OK
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productServer.getProductById(id);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // build() 用於構建並返回一個空的 ResponseEntity，僅僅包含 HTTP 狀態碼和可能的回應header，而沒有任何回應體
        }
        // 查詢的物件是Product 要去判斷是否為空
    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) //要加 @Valid @NotNull才會生效
    {
        Integer productId = productServer.createProduct(productRequest); // 預期createProduct會去資料庫中創建商品出來 & 返回id
        Product product = productServer.getProductById(productId); // by 返回id，從資料庫取得商品數據

        return ResponseEntity.status(HttpStatus.CREATED).body(product);// (前端輸入的值+getId)=product 回傳給前端
    }

    @PutMapping("/products/{productId}")
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
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
        productServer.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
