package com.control.motor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.control.motor.services.CameraServices;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/camera")
public class CameraController {
    
    private final CameraServices cameraServices;

    public CameraController(CameraServices cameraServices) {
        this.cameraServices = cameraServices;
    }

    @GetMapping("/snapshot")
    public ResponseEntity<byte[]> getSnapshot() {
        byte[] image = cameraServices.captureFrame();
        return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"snapshot.jpg\"")
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(image);
    }
    
    @GetMapping(value = "/video", produces = MediaType.IMAGE_JPEG_VALUE)
    public void streamVideo(HttpServletResponse response) {
        response.setContentType("multipart/x-mixed-replace;boundary=frame");
        try (OutputStream out = response.getOutputStream()) {
            while (true) {
                byte[] frame = cameraServices.captureFrame();
                if (frame.length > 0) {
                    out.write((
                            "--frame\r\n" +
                            "Content-Type: image/jpeg\r\n" +
                            "Content-Length: " + frame.length + "\r\n\r\n").getBytes());
                    out.write(frame);
                    out.write("\r\n".getBytes());
                    out.flush();
                    Thread.sleep(50); // 20 FPS
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
            // Optional: log this or clean up if needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Good practice
        }
    }

    
}
