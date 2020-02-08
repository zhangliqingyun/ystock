create database stock;
user stock;
#用户表
CREATE TABLE `basic_user` (
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

#股票数据表
CREATE TABLE `stock_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(10) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(40) DEFAULT NULL COMMENT '股票名称',
  `data_date` date DEFAULT NULL COMMENT '股票交易时间',
  `now_price` decimal(10,2) DEFAULT NULL COMMENT '当前价格',
  `last_price` decimal(10,2) DEFAULT NULL COMMENT '上次交易价格',
  `diff_price` decimal(10,2) DEFAULT NULL COMMENT '与上次的差值',
  `basic_stock_id` int(11) DEFAULT NULL COMMENT '股票基本信息主键id，目前用于删除数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

#股票持仓表
CREATE TABLE `hold_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stock_code` varchar(10) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(40) DEFAULT NULL COMMENT '股票名称',
  `bulid_date` timestamp NULL DEFAULT NULL COMMENT '建仓时间',
  `hold_num` int(11) DEFAULT NULL COMMENT '持仓数量',
  `buy_price` decimal(10,3) DEFAULT NULL COMMENT '成交价',
  `service_price` decimal(5,3) DEFAULT NULL COMMENT '手续费',
  `add_date` timestamp NULL DEFAULT NULL COMMENT '录入系统日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

#历史交易表
CREATE TABLE `history_deal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stock_code` varchar(10) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(40) DEFAULT NULL COMMENT '股票名称',
  `deal_date` timestamp NULL DEFAULT NULL COMMENT '成交日期',
  `deal_unit_price` decimal(10,3) DEFAULT NULL COMMENT '成交单价',
  `deal_num` int(11) DEFAULT NULL COMMENT '成交量',
  `buy_sell_flag` varchar(10) DEFAULT NULL COMMENT '买或卖标识',
  `deal_price_total` decimal(10,3) DEFAULT NULL COMMENT '成交额',
  `service_price` decimal(5,3) DEFAULT NULL COMMENT '手续费',
  `profit_loss` decimal(10,3) DEFAULT NULL COMMENT '盈亏（卖出股票才有值）',
  `add_date` timestamp NULL DEFAULT NULL COMMENT '录入系统日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
