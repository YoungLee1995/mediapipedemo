package com.example.testdemo.keyboard;

import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.ArrayList;
import java.util.List;

public class HandMark {
    public HandMark() {
        for(int i=0;i<jointPoint.length;i++){
            Position p=new Position();
            jointPoint[i]=p;
        }
    }

    public Position[] jointPoint =new Position[21];
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static HandMark lm2hm(List<LandmarkProto.Landmark> l, List<LandmarkProto.NormalizedLandmark> nl){
        HandMark result=new HandMark();
        for(int i=0;i<l.size();i++){
            Position position=new Position();
            position.setLocation_x(l.get(i).getX()*1000);
            position.setLocation_y(l.get(i).getY()*1000);
            position.setLocation_z(l.get(i).getZ()*1000);
            position.setPixel_x(nl.get(i).getX()*1080);
            position.setPixel_y(nl.get(i).getY()*1920);
            result.jointPoint[i]=position;
        }
        return result;
    }
}
