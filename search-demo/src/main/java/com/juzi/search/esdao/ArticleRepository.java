package com.juzi.search.esdao;

import com.juzi.search.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

/**
 * @author codejuzi
 */
@Service
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
}
