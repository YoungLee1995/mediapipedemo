package com.example.testdemo.keyboard;

import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.ArrayList;
import java.util.List;

public class HandMark {
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
        for(int i=0;i<21;i++){
            result.jointPoint[i].setLocation_x(l.get(i).getX()*1000);
            result.jointPoint[i].setLocation_y(l.get(i).getY()*1000);
            result.jointPoint[i].setLocation_z(l.get(i).getZ()*1000);
            result.jointPoint[i].setPixel_x(nl.get(i).getX()*1080);
            result.jointPoint[i].setPixel_y(nl.get(i).getY()*1920);
        }
        return result;
    }
}
