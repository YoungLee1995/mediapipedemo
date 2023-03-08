package com.example.testcalculator.keyboard.rules.standardPosture;

import static com.example.testcalculator.keyboard.headers.ConstPredefinedKt.pi;

import com.example.testcalculator.keyboard.datastruct.HandPosture2D;
import com.example.testcalculator.keyboard.datastruct.Position;
import com.example.testcalculator.keyboard.math.Vector3D;

import java.util.jar.Manifest;

public class userHandLoad2D {
    final public double[] ratiosLib={
            0.5,0.9,1.0,0.9,0.7, //(wrist to finger tip)/standardLength

    };
    public static boolean isPosStandard(HandPosture2D currentPos){
        return false;
    }

    public static boolean staticCriterion(HandPosture2D currentPos){
        //distance from wrist to mid finger tip
        Position[] positions=currentPos.jointPoint;
        Vector3D[] bones=currentPos.fingerBones;
        Vector3D standardVector=new Vector3D(positions[0],positions[12]);
        double standardLength=standardVector.lengthL2();
        //judge:is finger curved or not
        for(int i=1;i<6;i++){
            int root=i*4-3;
            int num=i==1?1:2;
            for(int j=0;j<num;j++){
                double theta=Math.asin(Vector3D.pointProd(bones[root+j],bones[root+j+1])/
                        bones[root+j].lengthL2()/bones[root+j+1].lengthL2());
                double maxError=i==1?15.0:8.0;
                if(theta/pi*180.0>maxError){
                    currentPos.isStandard=false;
                    return false;
                }
            }
        }
        //judge:is lengths standard or not



        return false;
    }
}
