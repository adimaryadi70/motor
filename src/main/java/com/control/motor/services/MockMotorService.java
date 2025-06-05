package com.control.motor.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("mock") // Activate this implementation when using the 'mock' profile
@Service
public class MockMotorService implements MotorService {

    @Override
    public void onMotor() {
        System.out.println("⚙️ Mock motor ON (no real GPIO triggered)");
    }
}
