package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "tb_tag")
@Data
public class Tag {
    private Integer id;
    private String color;
    private String content;
    private Integer status;
    private String userId;
    private Date createTime;
}
