package com.example.testcalculator.keyboard.datastruct;

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
        //行数
        int lineNum=4;
        //列数
        int columnNUm=4;
        //宽高比
        double ratio=width/height;
        double keyWidth=width/(columnNUm+2)-20;
        double keyHeight=height/lineNum-20;
        double y_first=10.0;
        double x_first=20.0;
        double z=300.0;
        int id=0;

        //double y_top = (height-(keyHeight*lineNum+y_first*lineNum))/2;
        testKeyMap = new HashMap<>();
        for(int i = 0;i<lineNum;i++)
        {
            for (int j = 0; j < columnNUm; j++) {
                double x = x_first+j*(keyWidth+10);
                double y=y_first + i * (keyHeight+10);
                Keyboard k = new Keyboard(x, y, z, keyWidth, keyHeight, z);
                k.setId(id);
                testKeyMap.put(id, k);
                id++;
            }
        }
    }
}
