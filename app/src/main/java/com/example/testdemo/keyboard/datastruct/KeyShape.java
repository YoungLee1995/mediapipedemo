package com.example.testdemo.keyboard.datastruct;

public class KeyShape {
    KeyShape(){}
    KeyShape(double x,double y,double z){
        key_width=x;
        key_height=y;
        key_depth=z;
    }
    private double key_width;
    private double key_height;
    private double key_depth;

    public double getKey_width() {
        return key_width;
    }

    public void setKey_width(double key_width) {
        this.key_width = key_width;
    }

    public double getKey_height() {
        return key_height;
    }

    public void setKey_height(double key_height) {
        this.key_height = key_height;
    }

    public double getKey_depth() {
        return key_depth;
    }

    public void setKey_depth(double key_depth) {
        this.key_depth = key_depth;
    }
}
