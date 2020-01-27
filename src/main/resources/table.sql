create database stock;
user stock;
#用户表
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
)

#股票基础数据表
CREATE TABLE `basic_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stock_code` varchar(10) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(40) DEFAULT NULL COMMENT '股票名称',
  `appear_addr_code` varchar(40) DEFAULT NULL COMMENT '上市地方代码（sh：上海，sz:深圳，s_sh：大盘指数）',
  `appear_addr_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '上市地方名称',
  `add_date` timestamp NULL DEFAULT NULL COMMENT '录入系统时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
