package com.juzi.search.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author codejuzi
 */
@Data
@Document("article_associate_words")
@NoArgsConstructor
public class ArticleAssociateWords implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 联想词
     */
    private String associateWords;

    /**
     * 创建时间
     */
    private Date createdTime;

    public ArticleAssociateWords(String associateWords, Date createdTime) {
        this.associateWords = associateWords;
        this.createdTime = createdTime;
    }
}
