package com.example.testdemo.keyboard;

public class Keyboard {
    Keyboard(){}
    Keyboard(Position p,KeyShape k){
        position=p;
        keyShape=k;
    }
    Keyboard(double x,double y,double z,double width,double height,double depth){
        position.setPixel_x(x);
        position.setPixel_y(y);
        position.setLocation_z(z);
        keyShape.setKey_depth(depth);
        keyShape.setKey_height(height);
        keyShape.setKey_width(width);
    }

    private int id;
    public Position position=new Position();
    public KeyShape keyShape=new KeyShape();

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
