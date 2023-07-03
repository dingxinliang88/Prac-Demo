package com.juzi.user.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -2984584148641782539L;

    private Long id;
    private String username;
    private String address;
}