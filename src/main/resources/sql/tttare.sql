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

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(1) DEFAULT '1' COMMENT '是否可用,0:不可用，1：可用',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `url` varchar(50) DEFAULT NULL COMMENT '菜单URL',
  `perms` text COMMENT '权限标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `type` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` bigint(20) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `available` int(11) DEFAULT '1' COMMENT '0：不可用，1：可用',
  `open` int(1) DEFAULT '1' COMMENT '0:不展开，1：展开',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_role_menu`;

CREATE TABLE `tb_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单/按钮ID'
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