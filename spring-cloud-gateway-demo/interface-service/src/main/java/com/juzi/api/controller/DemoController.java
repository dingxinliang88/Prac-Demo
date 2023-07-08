package com.juzi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
@Slf4j
@RestController
public class DemoController {

    @GetMapping("/get")
    public String testGet(String name, HttpServletRequest request) {
        String gatewayKey = request.getHeader("gateway-key");
        System.out.println("gatewayKey = " + gatewayKey);
        return "[Get] name = " + name;
    }
}
