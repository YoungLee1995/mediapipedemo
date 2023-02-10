package com.example.testdemo.keyboard.math;

import com.example.testdemo.keyboard.datastruct.HandMark;

public class MoveAverage {
    public static Coordinate calc2Average(Coordinate p1,Coordinate p2){
        Coordinate p=new Coordinate();
        p.setCartesian_x((p1.getCartesian_x()+p2.getCartesian_x())/2);
        p.setCartesian_y((p1.getCartesian_y()+p2.getCartesian_y())/2);
        return p;
    }
    public static Coordinate calc3Average(Coordinate p1,Coordinate p2,Coordinate p3){
        Coordinate p=new Coordinate();
        p.setCartesian_x((p1.getCartesian_x()+p2.getCartesian_x()+p3.getCartesian_x())/3);
        p.setCartesian_y((p1.getCartesian_y()+p2.getCartesian_y()+p3.getCartesian_y())/3);
        return p;
    }
    public static Coordinate calc4Average(Coordinate p1,Coordinate p2,Coordinate p3,Coordinate p4){
        Coordinate p=new Coordinate();
        p.setCartesian_x((p1.getCartesian_x()+p2.getCartesian_x()+p3.getCartesian_x()+p4.getCartesian_x())/4);
        p.setCartesian_y((p1.getCartesian_y()+p2.getCartesian_y()+p3.getCartesian_y()+p4.getCartesian_y())/4);
        return p;
    }
    public static HandMark calc2Average(HandMark h1, HandMark h2){
        HandMark h=new HandMark();
        //h的各类标记还没有定义
        for(int i=0;i<h1.jointPoint.length;i++){
            h.jointPoint[i].setPixel_x((h1.jointPoint[i].getPixel_x()+h2.jointPoint[i].getPixel_x())/2);
            h.jointPoint[i].setPixel_y((h1.jointPoint[i].getPixel_y()+h2.jointPoint[i].getPixel_y())/2);
            h.jointPoint[i].setLocation_x((h1.jointPoint[i].getLocation_x()+h2.jointPoint[i].getLocation_x())/2);
            h.jointPoint[i].setLocation_y((h1.jointPoint[i].getLocation_y()+h2.jointPoint[i].getLocation_y())/2);
            h.jointPoint[i].setLocation_z((h1.jointPoint[i].getLocation_z()+h2.jointPoint[i].getLocation_z())/2);
        }
        return h;
    }
    public static HandMark calc3Average(HandMark h1,HandMark h2,HandMark h3){
        HandMark h=new HandMark();
        for(int i=0;i<h1.jointPoint.length;i++){
            h.jointPoint[i].setPixel_x((h1.jointPoint[i].getPixel_x()+h2.jointPoint[i].getPixel_x()+h3.jointPoint[i].getPixel_x())/3);
            h.jointPoint[i].setPixel_y((h1.jointPoint[i].getPixel_y()+h2.jointPoint[i].getPixel_y()+h3.jointPoint[i].getPixel_y())/3);
            h.jointPoint[i].setLocation_x((h1.jointPoint[i].getLocation_x()+h2.jointPoint[i].getLocation_x()+h3.jointPoint[i].getLocation_x())/3);
            h.jointPoint[i].setLocation_y((h1.jointPoint[i].getLocation_y()+h2.jointPoint[i].getLocation_y()+h3.jointPoint[i].getLocation_y())/3);
            h.jointPoint[i].setLocation_z((h1.jointPoint[i].getLocation_z()+h2.jointPoint[i].getLocation_z()+h3.jointPoint[i].getLocation_z())/3);
        }
        return h;
    }
    public static HandMark calc4Average(HandMark h1,HandMark h2,HandMark h3,HandMark h4){
        HandMark h=new HandMark();
        for(int i=0;i<h1.jointPoint.length;i++){
            h.jointPoint[i].setPixel_x((h1.jointPoint[i].getPixel_x()+h2.jointPoint[i].getPixel_x()+h3.jointPoint[i].getPixel_x()+h4.jointPoint[i].getPixel_x())/4);
            h.jointPoint[i].setPixel_y((h1.jointPoint[i].getPixel_y()+h2.jointPoint[i].getPixel_y()+h3.jointPoint[i].getPixel_y()+h4.jointPoint[i].getPixel_y())/4);
            h.jointPoint[i].setLocation_x((h1.jointPoint[i].getLocation_x()+h2.jointPoint[i].getLocation_x()+h3.jointPoint[i].getLocation_x()+h4.jointPoint[i].getLocation_x())/4);
            h.jointPoint[i].setLocation_y((h1.jointPoint[i].getLocation_y()+h2.jointPoint[i].getLocation_y()+h3.jointPoint[i].getLocation_y()+h4.jointPoint[i].getLocation_y())/4);
            h.jointPoint[i].setLocation_z((h1.jointPoint[i].getLocation_z()+h2.jointPoint[i].getLocation_z()+h3.jointPoint[i].getLocation_z()+h4.jointPoint[i].getLocation_z())/4);
        }
        return h;
    }

}

class Coordinate{
    public Coordinate() {
    }

    public Coordinate(double cartesian_x, double cartesian_y) {
        this.cartesian_x = cartesian_x;
        this.cartesian_y = cartesian_y;
    }

    private double cartesian_x;
    private double cartesian_y;

    public double getCartesian_x() {
        return cartesian_x;
    }

    public void setCartesian_x(double cartesian_x) {
        this.cartesian_x = cartesian_x;
    }

    public double getCartesian_y() {
        return cartesian_y;
    }

    public void setCartesian_y(double cartesian_y) {
        this.cartesian_y = cartesian_y;
    }
}
