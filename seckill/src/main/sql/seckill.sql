/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-01-19 09:49:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `seckill`
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `number` int(10) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone6', '293', '2018-01-16 10:10:00', '2018-01-17 14:51:03', '2018-01-17 11:05:12');
INSERT INTO `seckill` VALUES ('1001', '500元秒杀ipad2', '99', '2018-01-17 14:52:53', '2018-01-17 15:53:03', '2018-01-17 15:19:38');
INSERT INTO `seckill` VALUES ('1002', '300元秒杀小米4', '100', '2018-01-11 14:52:50', '2018-01-12 14:52:59', '2018-01-11 14:53:02');
INSERT INTO `seckill` VALUES ('1003', '200元秒杀红米note', '100', '2018-01-11 14:52:47', '2018-01-12 14:52:55', '2018-01-11 14:52:58');

-- ----------------------------
-- Table structure for `success_killed`
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(10) NOT NULL,
  `user_phone` bigint(11) NOT NULL,
  `state` tinyint(2) NOT NULL,
  `creage_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`seckill_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1001', '15386654615', '0', '0000-00-00 00:00:00');
