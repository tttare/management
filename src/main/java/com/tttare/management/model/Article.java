package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "tb_article")
public class Article {

    private Integer id;
    private String userId;
    private String title;
    private String abs;
    private String content;
    //分类
    private List<Category>  categories;
    //标签
    private List<Tag>  tags;
    private Date createTime;
    private Date updateTime;
    private int commentCount;
    private int likeCount;
    private int viewCount;
}
