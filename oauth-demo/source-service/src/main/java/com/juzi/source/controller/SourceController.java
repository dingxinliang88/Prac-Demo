package com.juzi.source.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    @GetMapping()
    public String source() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);

        return "<h1>source</h1>";
    }
}
