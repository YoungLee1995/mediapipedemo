package com.example.testcalculator.keyboard.datastruct;

public class HmGrat extends HandMark{
    public HmGrat() {
    }

    public HmGrat(HandMark h1, HandMark h2) {
        for (int i = 0; i < 21; i++){
            this.jointPoint[i].setLocation_x(h2.jointPoint[i].getLocation_x()-h1.jointPoint[i].getLocation_x());
            this.jointPoint[i].setLocation_y(h2.jointPoint[i].getLocation_y()-h1.jointPoint[i].getLocation_y());
            this.jointPoint[i].setLocation_z(h2.jointPoint[i].getLocation_z()-h1.jointPoint[i].getLocation_z());
        }
    }
}
