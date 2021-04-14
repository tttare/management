package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_category")
public class Category {
    private Integer id;
    private String content;
    private Integer status;
}
