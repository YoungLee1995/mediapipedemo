package com.example.testdemo.keyboard;

import java.util.HashMap;
import java.util.Map;

public class KeyInformation {
}
class Position{
    Position(){};
    Position(double x,double y){
        pixel_x=x;
        pixel_y=y;
    }

    private double location_x;
    private double location_y;
    private double location_z;

    private double pixel_x;
    private double pixel_y;

    public double getLocation_x() {
        return location_x;
    }

    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }

    public double getLocation_y() {
        return location_y;
    }

    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }

    public double getLocation_z() {
        return location_z;
    }

    public void setLocation_z(double location_z) {
        this.location_z = location_z;
    }

    public double getPixel_x() {
        return pixel_x;
    }

    public void setPixel_x(double pixel_x) {
        this.pixel_x = pixel_x;
    }

    public double getPixel_y() {
        return pixel_y;
    }

    public void setPixel_y(double pixel_y) {
        this.pixel_y = pixel_y;
    }

}
class KeyShape{
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
class Keyboard{
    Keyboard(){}
    Keyboard(Position p,KeyShape k){
        position=p;
        keyShape=k;
    }
    Keyboard(double x,double y,double z,double width,double height,double depth){
        position.setLocation_x(x);
        position.setLocation_y(y);
        position.setLocation_z(z);
        keyShape.setKey_depth(depth);
        keyShape.setKey_height(height);
        keyShape.setKey_width(width);
    }

    private int id;
    private Position position=new Position();
    private KeyShape keyShape=new KeyShape();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public KeyShape getKeyShape() {
        return keyShape;
    }

    public void setKeyShape(KeyShape keyShape) {
        this.keyShape = keyShape;
    }
}
class TestKeyBoard{
    Map<Integer, Keyboard> testKeyMap = new HashMap<Integer, Keyboard>();
    void Init(){
        double y_first=85.0;
        double x=0.0;
        double z=300.0;
        for(int i=0;i<10;i++){
            double y=y_first-i*17.0;
            Keyboard k=new Keyboard(x,y,z,17,17,z);
            k.setId(i);
            this.testKeyMap.put(i,k);
        }
    }
}