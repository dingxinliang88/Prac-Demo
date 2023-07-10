package com.juzi.search.service.impl;

import com.juzi.search.dto.ArticleSearchDTO;
import com.juzi.search.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author codejuzi
 */
@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private ArticleService articleService;

    @Test
    void searchArticle() {

        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO();
        articleSearchDTO.setSearchText("如何");
        articleService.searchArticle(articleSearchDTO);
    }
}