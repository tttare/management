package com.tttare.management.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 评论及回复
 * */
@Data
@TableName(value = "tb_comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String content;

    private Date createTime;

    private Integer articleId;

    private Integer replyId;

    private Integer type;

    enum CommentState{
        comment("0","评论"),
        reply("1","回复");

        public String code;
        public String text;

        CommentState(String code,String text){
            this.code = code;
            this.text = text;
        }

        public static CommentState getByCode(String code){
            CommentState[] values = CommentState.values();
            for(CommentState v:values){
                if(v.code.equals(code)){
                    return v;
                }
            }
            return null;
        }
    }





}
