package com.serena.springbootmall.constant;

public class MyTest {
    public static void main(String[] args) {
        ProductCategory category = ProductCategory.FOOD;
        // .name()
        // 把category轉String類型
        String s = category.name();
        System.out.println(s);

        String s2 = "Car";
        // .valueOf()
        // 去Enum裡面找對應值
        ProductCategory category2 = ProductCategory.valueOf(s2);

    }
}
