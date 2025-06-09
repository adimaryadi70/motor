package com.control.motor.services;

import java.util.logging.Logger;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.springframework.stereotype.Service;

@Service
public class CameraServices {
    
    private static final Logger logger = Logger.getLogger(CameraServices.class.getName());
    private final VideoCapture camera;

    public CameraServices() {
        camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            throw new RuntimeException("Kamera Tidak Terdeteksi");
        }
    }

    public byte[] captureFrame() {
        Mat frame = new Mat();
        if (camera.read(frame)) {
            BytePointer buf = new BytePointer();
            opencv_imgcodecs.imencode(".jpg", frame, buf);

            byte[] imageData = new byte[(int) buf.limit()];
            buf.get(imageData);
            return imageData;
        }
        return new byte[0];
    }

    // Optional: close camera on app shutdown
    @Override
    protected void finalize() throws Throwable {
        camera.release();
        super.finalize();
    }
}
