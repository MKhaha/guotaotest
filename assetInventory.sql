SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;


USE asset_inventory;


-- ----------------------------
--  Table structure for `asset_inventory`
-- ----------------------------
DROP TABLE IF EXISTS `asset_inventory`;
CREATE TABLE `asset_inventory` (
  `asset_id` VARCHAR(16) NOT NULL COMMENT '固定资产编号',
  `asset_name` VARCHAR(100) DEFAULT NULL COMMENT '固定资产名称',
  `asset_category` VARCHAR(100) DEFAULT NULL COMMENT '资产类别',
  `brand_specification` VARCHAR(100) DEFAULT NULL COMMENT '品牌规格',
  `unit_measurement` VARCHAR(11) DEFAULT NULL COMMENT '计量单位',
  `book_amount` INT(11) NOT NULL COMMENT '资产账面数量',
  `inventory_amount` INT(11) DEFAULT -1 COMMENT '资产盘点数量',
  `storage_location` VARCHAR(100) DEFAULT NULL COMMENT '存放地点',
  `department_responsibility` VARCHAR(100) DEFAULT NULL COMMENT '责任部门',
  `person_in_charge` VARCHAR(11) DEFAULT NULL COMMENT '责任人',
  `status_usage` VARCHAR(11) DEFAULT NULL COMMENT '使用状况,在用,保管',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`asset_id`),
  KEY `asset_id_index` (`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `mmall_user`
-- ----------------------------
DROP TABLE IF EXISTS `asset_user`;
CREATE TABLE `asset_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_user`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_user` VALUES ('1', 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@happymmall.com', '13800138000', '问题', '答案', '1', '2016-11-06 16:56:45', '2017-04-04 19:27:36'), ('13', 'geely', '08E9A6EA287E70E7E3F7C982BF7923AC', 'geely@happymmall.com', '13800138000', '问题', '答案', '0', '2016-11-19 22:19:25', '2016-11-19 22:19:25'), ('17', 'rosen', '095AC193FE2212EEC7A93E8FEFF11902', 'rosen1@happymmall.com', '13800138000', '问题', '答案', '0', '2017-03-17 10:51:33', '2017-04-09 23:13:26'), ('21', 'soonerbetter', 'DE6D76FE7C40D5A1A8F04213F2BEFBEE', 'test06@happymmall.com', '13800138000', '105204', '105204', '0', '2017-04-13 21:26:22', '2017-04-13 21:26:22');
COMMIT;
