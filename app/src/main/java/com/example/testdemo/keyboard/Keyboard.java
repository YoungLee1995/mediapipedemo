package com.example.testdemo.keyboard;

public class Keyboard {
    /*Keyboard(){}
    Keyboard(Position p,KeyShape k){
        position=p;
        keyShape=k;
    }*/
    private int id;
    public Position position;
    public KeyShape keyShape;

    Keyboard(double x,double y,double z,double width,double height,double depth){
        position = new Position();
        keyShape = new KeyShape();
        position.setPixel_x(x);
        position.setPixel_y(y);
        position.setLocation_z(z);
        keyShape.setKey_depth(depth);
        keyShape.setKey_height(height);
        keyShape.setKey_width(width);
    }

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
