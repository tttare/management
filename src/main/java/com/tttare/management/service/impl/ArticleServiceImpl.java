package com.tttare.management.service.impl;

import com.tttare.management.mapper.ArticleMapper;
import com.tttare.management.model.Article;
import com.tttare.management.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Map<String, Object> param) {
        Article article = new Article();
        article.setTitle((String)param.get("title"));
        article.setAbs((String)param.get("abs"));
        article.setContent((String)param.get("content"));
        //种类

        //标签

    }

}
