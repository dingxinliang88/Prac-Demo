package com.juzi.jok.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
@RestController
public class JokeController {

    @GetMapping("/")
    public String joke(HttpServletRequest request) {

        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        System.out.println("remoteAddr = " + remoteAddr);
        System.out.println("remoteHost = " + remoteHost);
        return "<h1>我是你爹！</h1>";
    }
}
