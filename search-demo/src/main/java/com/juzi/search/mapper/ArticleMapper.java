package com.juzi.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juzi.search.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author codejuzi
 * @description 针对表【tb_article(文章表)】的数据库操作Mapper
 * @createDate 2023-07-09 21:55:18
 * @Entity generator.domain.Article
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




