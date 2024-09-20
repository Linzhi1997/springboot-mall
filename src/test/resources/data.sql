-- product
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Apple (Australia)', 'FOOD', 'https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg', 30, 10, 'Fresh apples from Australia.', '2024-03-19 17:00:00', '2024-03-22 18:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Apple (Hokkaido)', 'FOOD', 'https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_1280.jpg', 300, 5, 'Premium apples from Hokkaido, Japan.', '2024-03-19 18:30:00', '2024-03-19 18:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Apple Orange', 'FOOD', 'https://cdn.pixabay.com/photo/2021/07/30/04/17/orange-6508617_1280.jpg', 10, 50, 'A delicious mix of apples and oranges.', '2024-03-20 09:00:00', '2024-03-24 15:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Toyota', 'CAR', 'https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg', 100000, 5, 'Reliable Toyota car.', '2024-03-20 09:20:00', '2024-03-20 09:20:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('BMW', 'CAR', 'https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg', 500000, 3, 'Turbocharged engine, 4 cylinders, 16 valves.', '2024-03-20 12:30:00', '2024-03-20 12:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Benz', 'CAR', 'https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg', 600000, 2, 'Luxurious Benz car.', '2024-03-21 20:10:00', '2024-03-22 10:50:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, create_date, last_modified_date)
VALUES ('Tesla', 'CAR', 'https://cdn.pixabay.com/photo/2021/01/15/16/49/tesla-5919764_1280.jpg', 450000, 5, 'Electric car.', '2024-03-21 23:30:00', '2024-03-21 23:30:00');
-- user
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user1@gmail.com', '202cb962ac59075b964b07152d234b70', '2024-06-30 10:30:00', '2024-06-30 10:30:00');
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user2@gmail.com', '202cb962ac59075b964b07152d234b70', '2024-06-30 10:40:00', '2024-06-30 10:40:00');
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user3@gmail.com', '202cb962ac59075b964b07152d234b70', '2024-06-30 10:50:00', '2024-06-30 10:50:00');
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user4@gmail.com', '202cb962ac59075b964b07152d234b70', '2024-06-30 10:50:00', '2024-06-30 10:50:00');

-- order, order_item
-- user1
INSERT INTO `order` (user_id, total_amount, order_status, created_date, last_modified_date) VALUES (1, 500690, 'PENDING', '2024-06-30 11:10:00', '2024-06-30 11:10:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 1, 3, 90);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 2, 2, 600);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 5, 1, 500000);

INSERT INTO `order` (user_id, total_amount, order_status, created_date, last_modified_date) VALUES (1, 100000, 'PENDING', '2024-06-30 12:03:00', '2024-06-30 12:03:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (2, 4, 1, 100000);
-- user2
INSERT INTO `order` (user_id, total_amount, order_status, created_date, last_modified_date) VALUES (2, 100000, 'PENDING', '2024-06-30 12:03:00', '2024-06-30 12:03:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (2, 4, 1, 100000);

INSERT INTO return_order (user_id, order_id, refund_total_amount, created_date) VALUES (2, 2, 100000, '2022-07-04 12:00:00');
INSERT INTO return_order_item (return_order_id, product_id, return_quantity, refund_amount) VALUES (1, 4, 1, 100000);

-- user3
INSERT INTO `order` (user_id, total_amount, order_status, created_date, last_modified_date) VALUES (3, 100000, 'PENDING', '2024-06-30 12:03:00', '2024-06-30 12:03:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (2, 4, 1, 100000);

-- user4 無訂單測試