package com.juzi.search;

import com.juzi.search.esdao.ArticleRepository;
import com.juzi.search.mapper.ArticleMapper;
import com.juzi.search.pojo.Article;
import com.juzi.search.pojo.ArticleAssociateWords;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author codejuzi
 */
@SpringBootTest
class SearchApplicationTest {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void init() {
        List<Article> articleList = articleMapper.selectList(null);
        articleRepository.saveAll(articleList);
    }

    @Test
    void testMongoAdd() {
        for (int i = 0; i < 10; i++) {
            ArticleAssociateWords articleAssociateWords = new ArticleAssociateWords();
            articleAssociateWords.setAssociateWords("如何 demo" + i);
            articleAssociateWords.setCreatedTime(new Date());
            mongoTemplate.save(articleAssociateWords);
        }
    }


    @Test
    void testMongoFind() {
        ArticleAssociateWords articleAssociateWords
                = mongoTemplate.findById("64ab76892a4d8c3201388dad", ArticleAssociateWords.class);
        System.out.println("articleAssociateWords = " + articleAssociateWords);
    }

    @Test
    void testMongoQuery() {
        Query query = Query.query(Criteria.where("associateWords").is("如何"))
                .with(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<ArticleAssociateWords> associateWordsList = mongoTemplate.find(query, ArticleAssociateWords.class);
        System.out.println(associateWordsList);
    }

    @Test
    void testMongoDelete() {
        mongoTemplate.remove(Query.query(Criteria.where("associateWords").is("如何 demo9")),
                ArticleAssociateWords.class);
    }
}
