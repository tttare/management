package com.tttare.management.controller;

import com.tttare.management.common.model.Contant;
import com.tttare.management.common.model.ResponseParam;
import com.tttare.management.mapper.CommentMapper;
import com.tttare.management.service.ArticleService;
import com.tttare.management.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 博文 评论 处理类
 * */
@Slf4j
@Controller
@RequestMapping(value="/article")
public class ArticleController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public ResponseParam addCommnet(@RequestBody Map<String,Object> param){
        ResponseParam rp;
        try{
            commentService.addComment(param);
            rp = new ResponseParam(Contant.SUCCESS,null);
        }catch (Exception e){
            rp = new ResponseParam(Contant.FAIL,e.getMessage());
        }
        return rp;
    }

    @RequestMapping(value = "/addArticle",method = RequestMethod.POST)
    public ResponseParam addArticle(@RequestBody Map<String,Object> param){
        ResponseParam rp;
        try{
            articleService.addArticle(param);
            rp = new ResponseParam(Contant.SUCCESS,null);
        }catch (Exception e){
            rp = new ResponseParam(Contant.FAIL,e.getMessage());
        }
        return rp;
    }

    @GetMapping(value = "/writeArticle")
    public String writeArticle(){
        return "/article/writeArticle";
    }



}
