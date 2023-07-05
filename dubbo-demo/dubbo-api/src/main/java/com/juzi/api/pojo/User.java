package com.juzi.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -5266734237094421690L;
    private String name;
}
