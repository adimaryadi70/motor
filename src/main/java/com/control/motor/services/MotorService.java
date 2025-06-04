package com.control.motor.services;

import org.springframework.stereotype.Service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class MotorService {
    
    private Context pi4j;
    private DigitalOutput motorPin;

    @PostConstruct
    public void init() {
        pi4j = Pi4J.newAutoContext();

        DigitalOutputConfig config = DigitalOutput.newConfigBuilder(pi4j)
            .id("motor")
            .name("Motor Control")
            .address(17)
            .shutdown(DigitalState.LOW)
            .initial(DigitalState.LOW)
            .provider("pigpio-digital-output")
            .build();
        
            motorPin = pi4j.create(config);
    }

    public void onMotor() {
        motorPin.high();
    }

    @PreDestroy
    public void cleanup() {
        if (pi4j != null) {
            pi4j.shutdown();
        }
    }
}
