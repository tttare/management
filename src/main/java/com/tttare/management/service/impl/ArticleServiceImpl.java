package com.tttare.management.service.impl;

import com.tttare.management.mapper.ArticleMapper;
import com.tttare.management.mapper.CategoryMapper;
import com.tttare.management.mapper.CommonMapper;
import com.tttare.management.mapper.TagMapper;
import com.tttare.management.model.Article;
import com.tttare.management.model.Category;
import com.tttare.management.model.SysUser;
import com.tttare.management.model.Tag;
import com.tttare.management.service.ArticleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CommonMapper commonMapper;

    @Override
    @Transactional
    public void addOrUpdateArticle(Map<String, Object> param) {
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        Article article = new Article();
        Date date = new Date();
        if(param.get("id")==null){
            article.setCreateTime(date);
            article.setUpdateTime(date);
            article.setTitle((String)param.get("title"));
            article.setAbs((String)param.get("abs"));
            article.setContent((String)param.get("content"));
            article.setUserId(user.getUserId());
            articleMapper.insert(article);
        }else{
            article.setUpdateTime(date);
            article.setId((Integer) param.get("id"));
            article.setTitle((String)param.get("title"));
            article.setAbs((String)param.get("abs"));
            article.setContent((String)param.get("content"));
            articleMapper.updateById(article);
        }
        Integer articleId = article.getId();
        //清楚 文章与 种类 标签的关系，并重新建立
        commonMapper.deleteArticleCategory(articleId);
        //种类 只有管理员能够制作
        List<Category> categories = (List<Category>)param.get("categories");
        categoryMapper.buildArticleLink(articleId,categories);

        commonMapper.deleteArticleTag(articleId);
        //标签 标签可以用户自己定制
        List<Tag> tags = (List<Tag>)param.get("tags");
        tagMapper.buildArticleLink(articleId,tags);
    }




}
