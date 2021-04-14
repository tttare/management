package com.tttare.management.mapper;

import org.springframework.stereotype.Component;
/**
 * 一些公共表，比如关系表的操作由此Mapper来执行
 * */
@Component
public interface CommonMapper {

    void deleteArticleTag(Integer aId);

    void deleteArticleCategory(Integer aId);
}
