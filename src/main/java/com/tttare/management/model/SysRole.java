package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ClassName: SysRole <br/>
 * Description: <br/>
 * date: 2019/9/3 12:03<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Data
@TableName(value = "sys_role")
public class SysRole {

    private String roleId; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Integer status; // 是否可用,如果不可用将不会添加给用户
    private Date createTime;//创建时间
    //角色 -- 菜单关系：多对多关系;
    @TableField(exist = false)
    private List<SysMenu> menus;
    // 用户 - 角色关系定义;
    @TableField(exist = false)
    private List<SysUser> users;// 一个角色对应多个用户
}
