/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-09-25 14:07:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(1024) NOT NULL,
  `ipaddress` varchar(64) DEFAULT NULL,
  `remark1` varchar(255) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  `remark3` varchar(255) DEFAULT NULL,
  `remark4` varchar(255) DEFAULT NULL,
  `remark5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
