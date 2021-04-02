package com.tttare.management.service.impl;

import com.tttare.management.mapper.ArticleMapper;
import com.tttare.management.mapper.CommentMapper;
import com.tttare.management.model.Article;
import com.tttare.management.model.Comment;
import com.tttare.management.model.User;
import com.tttare.management.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public void addComment(Map<String, Object> param) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer articleId = (Integer)param.get("articleId");
        Article article =  articleMapper.selectById(articleId);
        article.setCommentCount(article.getCommentCount()+1);
        articleMapper.updateById(article);
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setCreateTime(new Date());
        comment.setContent((String)param.get("content"));
        comment.setReplyId((Integer)param.get("commentId"));
        comment.setArticleId(articleId);
        commentMapper.insert(comment);
    }
}
