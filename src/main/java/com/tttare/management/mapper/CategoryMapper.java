package com.tttare.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tttare.management.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryMapper extends BaseMapper<Category> {

    void deleteArticleLink(Integer articleId);

    void buildArticleLink(Integer articleId, List<Category> categories);
}
