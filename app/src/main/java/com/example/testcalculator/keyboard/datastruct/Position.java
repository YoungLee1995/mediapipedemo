package com.example.testcalculator.keyboard.datastruct;

import com.example.testcalculator.keyboard.math.Vector3D;

public class Position {
    public Position() {
    }
    public Position(double location_x, double location_y, double location_z) {
        location[0]=location_x;
        location[1]=location_y;
        location[2]=location_z;
    }

    public Position(double location_x, double location_y, double location_z, double pixel_x, double pixel_y) {
        location[0]=location_x;
        location[1]=location_y;
        location[2]=location_z;
        location[3]=pixel_x;
        location[4]=pixel_y;
    }
    public Position(Vector3D finger) {
        location[0]=finger.getX();
        location[1]=finger.getY();
        location[2]=finger.getZ();
    }

    public double[] location = new double[5];


    public double getLocation_x() {
        return location[0];
    }

    public void setLocation_x(double location_x) {
        location[0]=location_x;
    }

    public double getLocation_y() {
        return location[1];
    }

    public void setLocation_y(double location_y) {
        location[1] = location_y;
    }

    public double getLocation_z() {
        return location[2];
    }

    public void setLocation_z(double location_z) {
        location[2] = location_z;
    }

    public double getPixel_x() {
        return location[3];
    }

    public void setPixel_x(double pixel_x) {
        location[3] = pixel_x;
    }

    public double getPixel_y() {
        return location[4];
    }

    public void setPixel_y(double pixel_y) {
        location[4] = pixel_y;
    }
}
