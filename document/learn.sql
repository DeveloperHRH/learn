/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.26 : Database - learn
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`learn` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `learn`;

/*Table structure for table `learn_sys_user` */

CREATE TABLE `learn_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `head_img` varchar(100) NOT NULL DEFAULT '' COMMENT '头像地址',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实名称',
  `age` int(3) NOT NULL DEFAULT '0' COMMENT '年龄',
  `sex` int(1) NOT NULL DEFAULT '1' COMMENT '性别 1.男 2.女',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '删除标识 0.未删除 1.已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `learn_sys_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
