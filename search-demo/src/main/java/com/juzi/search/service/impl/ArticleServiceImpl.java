package com.juzi.search.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juzi.search.dto.ArticleSearchDTO;
import com.juzi.search.mapper.ArticleMapper;
import com.juzi.search.pojo.Article;
import com.juzi.search.pojo.ArticleAssociateWords;
import com.juzi.search.service.ArticleService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author codejuzi
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<Article> searchArticle(ArticleSearchDTO articleSearchDTO) {
        String searchText = articleSearchDTO.getSearchText();
        if (searchText.isEmpty()) {
            return null;
        }

        // 异步保存历史记录
        CompletableFuture.runAsync(() -> insertHistory(searchText));


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));

        int pageNum = articleSearchDTO.getPageNum();
        int pageSize = articleSearchDTO.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<font style='color: red; font-size: inherit;'>");
        highlightBuilder.postTags("</font>");

        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withHighlightBuilder(highlightBuilder).build();

        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery, Article.class);

        // 解析结果
        List<Article> res = new ArrayList<>();
        if (!searchHits.hasSearchHits()) {
            return res;
        }
        List<SearchHit<Article>> hitList = searchHits.getSearchHits();

        for (SearchHit<Article> hit : hitList) {
            Article article = hit.getContent();
            // 做一些处理
            res.add(article);
        }
        return res;
    }

    @Override
    public void insertHistory(String associateWords) {
        // 1. 搜索关键词
        Query query = Query.query(Criteria.where("associateWords").is(associateWords));
        ArticleAssociateWords articleAssociateWords = mongoTemplate.findOne(query, ArticleAssociateWords.class);
        // 2. 存在，更新时间
        if (!Objects.isNull(articleAssociateWords)) {
            articleAssociateWords.setCreatedTime(new Date());
            mongoTemplate.save(articleAssociateWords);
            return;
        }
        // 3. 不存在，只保留最新的10条存储
        ArticleAssociateWords newArticleAssociateWords = new ArticleAssociateWords(associateWords, new Date());
        query = new Query().with(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<ArticleAssociateWords> articleAssociateWordsList = mongoTemplate.find(query, ArticleAssociateWords.class);

        // 记录小于10
        if (articleAssociateWordsList.size() < 10) {
            mongoTemplate.save(newArticleAssociateWords);
            return;
        }
        // 记录大于10，获取最后一条记录，替换
        ArticleAssociateWords oldWord = articleAssociateWordsList.get(articleAssociateWordsList.size() - 1);
        mongoTemplate.findAndReplace(
                Query.query(Criteria.where("id").is(oldWord.getId())),
                newArticleAssociateWords
        );
    }

    @Override
    public List<ArticleAssociateWords> loadHistory() {
        return mongoTemplate.find(new Query().with(Sort.by(Sort.Direction.DESC, "createdTime")),
                ArticleAssociateWords.class);
    }

    @Override
    public Boolean deleteHistory(String id) {
        if (id.isEmpty()) {
            return Boolean.FALSE;
        }

        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), ArticleAssociateWords.class);

        return Boolean.TRUE;
    }
}




