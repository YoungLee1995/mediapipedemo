package com.example.testdemo.keyboard;

import java.util.HashMap;
import java.util.Map;

public class TestKeyBoard {
    public Map<Integer, Keyboard> testKeyMap = new HashMap<Integer, Keyboard>();
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
}
