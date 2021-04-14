package com.tttare.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tttare.management.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagMapper extends BaseMapper<Tag> {

    void deleteArticleLink(Integer articleId);

    void buildArticleLink(Integer articleId, List<Tag> tags);
}
