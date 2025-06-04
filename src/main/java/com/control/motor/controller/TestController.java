package com.control.motor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.control.motor.services.MotorService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
    
    private final MotorService motorService;

    public TestController(MotorService motorService) {
        this.motorService = motorService;
    }

    @GetMapping("/")
    public String hello() {
        return "Tester Spring";
    }

    @GetMapping("/on")
    public String nyalakan() {
        motorService.onMotor();
        return "Motor dinyalakan!";
    }
    
}
