CREATE DATABASE /*!32312 IF NOT EXISTS*/`management` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `management`;


DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `sys_user` (
  `userid` varchar(32) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `nickname` varchar(16) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `expired_date` timestamp NULL DEFAULT NULL,
  `lastLogin_date` timestamp NULL DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `avatar` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `location` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_comment`;

CREATE TABLE `tb_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `article_id` int NOT NULL,
  `reply_id` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_article`;

CREATE TABLE `tb_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL COMMENT '作者',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `abs` varchar(256) DEFAULT NULL COMMENT '摘要',
  `content` blob COMMENT '内容',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `comment_count` int(10) unsigned zerofill DEFAULT NULL,
  `like_count` int(10) unsigned zerofill DEFAULT NULL,
  `view_count` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `role` varchar(100) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '1' COMMENT '是否可用,0:不可用，1：可用',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '菜单ID',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `path` varchar(128) NOT NULL COMMENT '菜单URl',
  `create_time` datetime NOT NULL COMMENT '创建时间',
	`status` int(1) DEFAULT '1' COMMENT '是否可用,0:不可用，1：可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';


DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

DROP TABLE IF EXISTS `tb_image`;

CREATE TABLE `tb_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path` varchar(1023) DEFAULT NULL COMMENT '图片路径',
  `size` bigint(20) DEFAULT NULL COMMENT '图片大小',
  `media_type` varchar(20) DEFAULT NULL COMMENT '图片类型',
  `suffix` varchar(50) DEFAULT NULL COMMENT '图片后缀',
  `height` int(20) DEFAULT NULL COMMENT '图片高度',
  `width` int(20) DEFAULT NULL COMMENT '图片宽度',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tb_category`;

DROP TABLE IF EXISTS `tb_tag`;


