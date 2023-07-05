package com.juzi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 6288658439134289960L;

    private Long id;
    private String name;
    private Integer age;

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
