package com.serena.springbootmall.dao.impl;

import com.serena.springbootmall.dao.ProductDao;
import com.serena.springbootmall.dto.ProductQueryParams;
import com.serena.springbootmall.dto.ProductRequest;
import com.serena.springbootmall.model.Product;
import com.serena.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer getTotal(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();
        // 查詢條件 使用此方法前一定有個sql
        // 查詢條件不同 總筆數也不同
        sql = addFilterSql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, create_date, last_modified_date  " +
                "FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        // 查詢條件 使用此方法前一定有個sql
        sql = addFilterSql(sql,map,productQueryParams);
        // 預設非null
        // 排序
        sql = sql + " ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();
        // 分頁
        sql = sql+" LIMIT :limit OFFSET :offset"; // 在order by後面
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        // 查詢一筆資料 返回object 查詢多筆資料 返回List<Object>
        // RowMapper內有的資料 sql必需也要有
        List<Product> products = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return products;
    }

    @Override
    public Product getProductById(Integer id) {
        String sql ="SELECT product_id, product_name, category, image_url, price, stock, description, create_date, last_modified_date " +
                "FROM product " +
                "WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",id); // put(key, value)

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        String sql ="INSERT INTO product (product_name, category, image_url, price, stock, description,create_date, last_modified_date ) " +
                "VALUES (:productName,:category,:imageUrl,:price,:stock,:description,:createDate,:lastModifiedDate)";
        Map<String,Object> map =new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder =new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map),keyHolder);
        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId,ProductRequest productRequest){
        String sql = "UPDATE product " +
                "SET product_name=:productName, category=:category, image_url=:imageUrl, price=:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";
        // 要更新lastModifiedDate的值
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void updateStock(Integer productId, Integer updatedStock) {
        String sql = "UPDATE product SET stock=:updatedStock, last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        map.put("updatedStock",updatedStock);
        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilterSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        // 查詢條件
        if (productQueryParams.getProductCategory() != null) {
            sql = sql + " AND category = :productCategory";
            map.put("productCategory", productQueryParams.getProductCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }
        return sql;
    }
}
