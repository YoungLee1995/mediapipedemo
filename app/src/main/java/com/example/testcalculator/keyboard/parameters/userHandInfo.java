package com.example.testcalculator.keyboard.parameters;

import com.example.testcalculator.keyboard.datastruct.HandMark;
import com.example.testcalculator.keyboard.datastruct.Position;
import com.example.testcalculator.keyboard.math.Vector3D;

public class userHandInfo {
    //need a hand with standard gesture
    public userHandInfo(HandMark userHand){
        for(int i=0;i<21;i++){
            fingerPoint[i].setLocation_x(userHand.jointPoint[i].getLocation_x());
            fingerPoint[i].setLocation_y(userHand.jointPoint[i].getLocation_y());
            fingerPoint[i].setLocation_z(0.0);
        }
        for(int i=1;i<21;i++){
            fingerBones[i]=new Vector3D(fingerPoint[i-1],fingerPoint[i]);
        }

    }

    private Position[] fingerPoint=new Position[21];
    private Vector3D[] fingerBones=new Vector3D[21];
    private Double[] fingerBonesLength=new Double[21];
    private Double[] specialLength=new Double[4];
}
