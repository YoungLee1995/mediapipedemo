package com.example.testdemo.keyboard;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class TestKeyBoard {
    public Map<Integer, Keyboard> testKeyMap;
    public void Init(){
        double y_first=0.0;
        double x=500.0;
        double z=300.0;
        for(int i=0;i<5;i++){
            double y=y_first+i*500;
            Keyboard k=new Keyboard(x,y,z,500,500,z);
            k.setId(i);
            this.testKeyMap.put(i,k);
        }
    }

    public void Init(double width,double height){
        double keyWidth=width/3-20;
        double keyHeight=500;
        double y_first=0.0;
        double x_first=20.0;
        double z=300.0;
        int id=0;
        testKeyMap = new HashMap<>();
        for(int i = 0;i<4;i++)
        {
            for (int j = 0; j < 3; j++) {
                double x = x_first+j*(keyWidth+20);
                double y = y_first + i * (keyHeight+20);
                Keyboard k = new Keyboard(x, y, z, keyWidth, keyHeight, z);
                k.setId(id);
                testKeyMap.put(id, k);
                id++;
            }
        }
    }
}
