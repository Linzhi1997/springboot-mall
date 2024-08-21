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
        sql = sql + " ORDER BY "+productQueryParams.getByOrder()+" "+productQueryParams.getSort();
        // 分頁
        sql = sql+" LIMIT :limit OFFSET :offset"; // 在order by後面
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        // 查詢一筆資料 返回object 查詢多筆資料 返回List<Object>
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
            return productList.get(0); // productList是搜尋後的結果
        }
        else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        // sql 記得帶上時間
        // sql product(table row_name) value(:變數) map.put("變數",productRequest get前端對應的json參數)
        // productRequest get的參數對應到變數 變數寫入table (而ProductCategory無法對應varchar,所以需轉換成String)
        String sql ="INSERT INTO product (product_name, category, image_url, price, stock, description,create_date, last_modified_date ) " +
                "VALUE (:productName,:category,:imageUrl,:price,:stock,:description,:createDate,:lastModifiedDate)";
        Map<String,Object> map =new HashMap<>();
        map.put("productName",productRequest.getProductName()); // 輸出{productName="Toyota"}
        map.put("category",productRequest.getCategory().toString()); // 輸出{category="CAR"}
        // (String類,String類) productRequest是ProductCategory類()enum
        // 需要.toString() 轉換
        // 輸出{category=CAR}
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());// 輸出{price=28000}
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        // 記錄當下時間點
        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);
        // 維持順序
        KeyHolder keyHolder =new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map),keyHolder);

        int productId = keyHolder.getKey().intValue();
        // 回傳id
        return productId;
    }

    @Override
    public void updateProduct(Integer productId,ProductRequest productRequest){
        String sql = "UPDATE product " +
                "SET product_name=:productName, category=:category, image_url=:imageUrl, price=:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";
        // 要更新lastModifiedDate的值
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId); //id也要設

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
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
    // private 只有這個class可使用
    // Filter 一個method時優先使用private
    // 限制方法"使用範圍"，方便"後續維護" !!!!!!!!!
    private String addFilterSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        // 查詢條件
        if (productQueryParams.getProductCategory() != null) {
            sql = sql + " AND category = :productCategory"; // AND前面要空格
            map.put("productCategory", productQueryParams.getProductCategory().name()); // {sql變數productCategory,前端傳入具體值}
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%"); // "%"要加在map這裡，JdbcTemplate的限制
        }
        return sql;
    }
}
