package com.juzi.feign.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 789495768042365070L;

    private Long id;
    private String username;
    private String address;
}