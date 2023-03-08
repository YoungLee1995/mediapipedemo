package com.example.testcalculator.keyboard.datastruct;

import com.example.testcalculator.keyboard.math.Vector3D;

public class HandPosture2D {
    public HandPosture2D() {
    }
    public HandPosture2D(HandMark handMark) {
        //init joint vectors
        for(int i=0;i<21;i++){
            jointPoint[i].setLocation_x(handMark.jointPoint[i].getLocation_x());
            jointPoint[i].setLocation_y(handMark.jointPoint[i].getLocation_y());
            jointPoint[i].setLocation_z(0.0);
            jointPoint[i].setPixel_x(handMark.jointPoint[i].getPixel_x());
            jointPoint[i].setPixel_y(handMark.jointPoint[i].getPixel_y());
        }
        for(int i=1;i<22;i++){
            fingerBones[i]=new Vector3D(handMark.jointPoint[i].getLocation_x()-handMark.jointPoint[i-1].getLocation_x(),
                    handMark.jointPoint[i].getLocation_y()-handMark.jointPoint[i-1].getLocation_y(),
                    0.0);
        }
        //init discontinuous joint
        for(int i:specialP){
            fingerBones[i]=new Vector3D(handMark.jointPoint[i].getLocation_x()-handMark.jointPoint[0].getLocation_x(),
                    handMark.jointPoint[i].getLocation_y()-handMark.jointPoint[0].getLocation_y(),
                    0.0);
        }
        //calculating joint length
        for(int i=1;i<22;i++){
            fingerLength[i]=fingerBones[i].lengthL2();
        }
        //key vectors for hand local axis
        for(int i=0;i<4;i++){
            specialBones[i]=new Vector3D(handMark.jointPoint[i*4+5].getLocation_x()-handMark.jointPoint[i*4+1].getLocation_x(),
                    handMark.jointPoint[i*4+5].getLocation_y()-handMark.jointPoint[i*4+1].getLocation_y(),
                    handMark.jointPoint[i*4+5].getLocation_z()-handMark.jointPoint[i*4+1].getLocation_z());
        }
        bone5to13=Vector3D.plus(bone5to9,bone9to13);
        //the relation between vectors and local axis should be: x:bone5to13 y:fingerBones[9] z:x crossProd y;
        handX=new Vector3D(bone5to13.getX(),bone5to13.getY(),0.0);
        handY=new Vector3D(fingerBones[9].getX(),fingerBones[9].getY(),0.0);
        handX=handX.normalized();
        handY=handY.normalized();
        for(int i=1;i<22;i++){
            fingerBonesLocal[i].setX(Vector3D.pointProd(handX,fingerBones[i]));
            fingerBonesLocal[i].setY(Vector3D.pointProd(handY,fingerBones[i]));
            fingerBonesLocal[i].setZ(0.0);
        }

    }
    //signs for posture and action judging
    public boolean isStandard = false;


    final public int[] specialP={1,5,9,13,17};
    //basic hand posture
    public Position[] jointPoint =new Position[21];
    public Vector3D[] fingerBones=new Vector3D[20];
    public Double[] fingerLength=new Double[20];
    public Double[] taps =new Double[20];
    public Double[] waves=new Double[20];
    //public double finger1rotate;
    //hand posture in local axis
    public Position[] jointPointLocal =new Position[21];
    public Vector3D[] fingerBonesLocal=new Vector3D[20];
    //calibrated joint lengths
    public Double[] calibratedLength=new Double[20];
    //parameter for local axis and its relation to global axis
    public Vector3D bone1to5;
    public Vector3D bone5to9;
    public Vector3D bone9to13;
    public Vector3D bone5to13;
    public Vector3D bone13to17;
    public Vector3D[] specialBones={bone1to5,bone5to9,bone9to13,bone13to17,bone5to13};
    public double handAlpha;
    public double handBeta;
    public double handGama;
    //local axis
    public Vector3D handX;
    public Vector3D handY;
    //optimized parameters
    public Position[] jointPointOpt =new Position[21];
    public Vector3D[] fingerBonesOpt=new Vector3D[20];
    public Position[] jointPointLocalOpt =new Position[21];
    public Vector3D[] fingerBonesLocalOpt=new Vector3D[20];
    public Double[] fingerLengthOpt=new Double[20];
    public Double[] tapsOpt =new Double[20];
    public Double[] wavesOpt=new Double[20];
    //parameter for local axis and its relation to global axis
    public Vector3D bone1to5Opt;
    public Vector3D bone5to9Opt;
    public Vector3D bone9to13Opt;
    public Vector3D bone5to13Opt;
    public Vector3D bone13to17Opt;
    public double handAlphaOpt;
    public double handBetaOpt;
    public double handGamaOpt;
    //local axis
    public Vector3D handXOpt;
    public Vector3D handYOpt;
    public Vector3D handZOpt;

}
