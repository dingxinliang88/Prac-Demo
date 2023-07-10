package com.juzi.search.controller;

import com.juzi.search.dto.ArticleSearchDTO;
import com.juzi.search.pojo.Article;
import com.juzi.search.pojo.ArticleAssociateWords;
import com.juzi.search.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author codejuzi
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/search")
    public List<Article> searchArticle(@RequestBody ArticleSearchDTO articleSearchDTO) {
        return articleService.searchArticle(articleSearchDTO);
    }

    @GetMapping("/load")
    public List<ArticleAssociateWords> loadHistory() {
        return articleService.loadHistory();
    }

    @DeleteMapping("del")
    public Boolean deleteHistory(@RequestParam String id) {
        return articleService.deleteHistory(id);
    }

}
