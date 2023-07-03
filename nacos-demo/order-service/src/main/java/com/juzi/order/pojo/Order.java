package com.juzi.order.pojo;

import com.juzi.feign.pojo.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 4664615801647959716L;
    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    private User user;
}