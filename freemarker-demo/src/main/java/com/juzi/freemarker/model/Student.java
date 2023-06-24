package com.juzi.freemarker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author codejuzi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private Integer age;
    private Double money;

}
