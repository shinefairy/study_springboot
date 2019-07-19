/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : eth

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-07-19 22:07:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('3', '大boss', '40', 'boss@baomidou.com', null, '2019-01-11 14:20:20');
INSERT INTO `tb_user` VALUES ('4', '王天风', '25', 'wtf@baomidou.com', '3', '2019-02-05 11:12:22');
INSERT INTO `tb_user` VALUES ('5', '李艺伟', '28', 'lyw@baomidou.com', '4', '2019-02-14 08:31:16');
INSERT INTO `tb_user` VALUES ('6', '张雨琪', '31', 'zjq@baomidou.com', '4', '2019-01-14 09:15:15');
INSERT INTO `tb_user` VALUES ('7', '刘红雨', '32', 'lhm@baomidou.com', '4', '2019-01-14 09:48:16');
