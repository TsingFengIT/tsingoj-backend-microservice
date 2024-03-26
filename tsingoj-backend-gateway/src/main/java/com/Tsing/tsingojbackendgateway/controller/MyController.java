package com.Tsing.tsingojbackendgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TsingFeng
 * @date 2024/3/19 17:12
 */
@RestController
public class MyController {

    @GetMapping("/test")
    public String test(){

        return "OK";

    }

}
