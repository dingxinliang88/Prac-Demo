package com.juzi.search.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class ArticleSearchDTO implements Serializable {

    private static final long serialVersionUID = 4819278807867077563L;

    private String searchText;
    /**
     * 当前页
     */
    private int pageNum = 1;
    /**
     * 分页条数
     */
    private int pageSize = 5;
}
