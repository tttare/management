package com.tttare.management.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@TableName(value = "sys_user")
public class SysUser {

    @TableId(type = IdType.ASSIGN_UUID,value="userid")
    private String userId;
    //驼峰会被解析为 user_name
    @TableField(value = "username")
    private String userName; //登录用户名
    @TableField(value = "nickname")
    private String nickName;//名称（昵称或者真实姓名，根据实际情况定义）
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;//加密密码的盐
    @TableField(value = "status")
    private String state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    @TableField(exist = false)
    private List<SysRole> roleList;// 一个用户具有多个角色
    @TableField(value = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date birthDate;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createDate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date expiredDate;//过期日期
    @TableField(value = "lastLogin_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date lastLoginDate;//上次登录时间
    private String email;//邮箱
    @TableField(value = "avatar")
    private String iconPath;//头像路径
    /**
     * 密码盐. 重新对盐重新进行了定义，用户名+salt，这样就不容易被破解，可以采用多种方式定义加盐
     * @return
     */
    @JsonIgnore
    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    enum UserState{
        INIT("0","初始化"),
        ENABLE("1","已启用"),
        LOCKED("2","已锁定"),
        DELETED("3","已删除");

        public  String code;
        public String text;

        UserState(String code,String text){
            this.code = code;
            this.text = text;
        }

        public static UserState getByCode(String code){
            UserState[] values = UserState.values();
            for(UserState v:values){
                if(v.code.equals(code)){
                    return v;
                }
            }
            return null;
        }
    }


}
