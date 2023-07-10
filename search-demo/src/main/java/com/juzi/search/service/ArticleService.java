package com.juzi.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juzi.search.dto.ArticleSearchDTO;
import com.juzi.search.pojo.Article;
import com.juzi.search.pojo.ArticleAssociateWords;

import java.util.List;

/**
 * @author codejuzi
 */
public interface ArticleService extends IService<Article> {

    List<Article> searchArticle(ArticleSearchDTO articleSearchDTO);

    void insertHistory(String associateWords);

    List<ArticleAssociateWords> loadHistory();

    Boolean deleteHistory(String id);
}
