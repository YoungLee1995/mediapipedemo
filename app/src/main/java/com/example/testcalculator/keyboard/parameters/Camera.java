package com.example.testcalculator.keyboard.parameters;

public class Camera {
    public double widthAngle;
    public double heightAngle;

    public Camera(double widthAngle, double heightAngle) {
        this.widthAngle = widthAngle;
        this.heightAngle = heightAngle;
    }

    public double getWidthAngle() {
        return widthAngle;
    }

    public void setWidthAngle(double widthAngle) {
        this.widthAngle = widthAngle;
    }

    public double getHeightAngle() {
        return heightAngle;
    }

    public void setHeightAngle(double heightAngle) {
        this.heightAngle = heightAngle;
    }
}
