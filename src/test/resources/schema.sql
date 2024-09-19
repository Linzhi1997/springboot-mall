-- product
CREATE TABLE IF NOT EXISTS product
(
    product_id         INT          NOT NULL    PRIMARY KEY     AUTO_INCREMENT,
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    create_date        TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );
-- user 不能使用user作為H2資料庫的命名
CREATE TABLE IF NOT EXISTS `user`
(
    user_id            INT          NOT NULL    PRIMARY KEY     AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL    UNIQUE,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);
CREATE TABLE IF NOT EXISTS `order`
(
    order_id           INT          NOT NULL    PRIMARY KEY     AUTO_INCREMENT,
    user_id            INT          NOT NULL,
    total_amount       INT          NOT NULL,
    order_status       VARCHAR(32)  NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL,
    amount        INT NOT NULL
);

CREATE TABLE IF NOT EXISTS return_order
(
    return_order_id           INT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id                   INT       NOT NULL,
    order_id                  INT       NOT NULL,
    refund_total_amount       INT       NOT NULL,
    created_date              TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS return_order_item
(
    return_order_item_id    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    return_order_id         INT NOT NULL,
    product_id              INT NOT NULL,
    return_quantity         INT NOT NULL,
    refund_amount           INT NOT NULL
);
