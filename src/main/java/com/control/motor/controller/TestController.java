package com.control.motor.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String hello() {
        return "Tester Spring";
    }
    
}
