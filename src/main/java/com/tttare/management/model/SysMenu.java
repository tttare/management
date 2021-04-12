package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "sys_menu")
public class SysMenu {

    private String id;
    private String name;
    private String path;
    private Integer status; // 是否可用,如果不可用将不会添加给角色
    private String parentId;//父菜单id
    @TableField(exist = false)
    private List<SysMenu> children;//子节点
    @TableField(exist = false)
    private List<SysRole> roles;//菜单和角色多对多

}
