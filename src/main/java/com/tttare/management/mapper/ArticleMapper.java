package com.tttare.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tttare.management.model.Article;
import com.tttare.management.model.Comment;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper extends BaseMapper<Article> {
}
