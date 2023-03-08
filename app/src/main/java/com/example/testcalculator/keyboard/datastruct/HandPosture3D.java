package com.example.testcalculator.keyboard.datastruct;

import com.example.testcalculator.keyboard.math.Vector3D;

public class HandPosture3D {
    final double pi = 3.141592654;
    public HandPosture3D() {
    }
    public HandPosture3D(HandMark handMark) {
        //init joint vectors
        for(int i=0;i<21;i++){
            jointPoint[i].setLocation_x(handMark.jointPoint[i].getLocation_x());
            jointPoint[i].setLocation_y(handMark.jointPoint[i].getLocation_y());
            jointPoint[i].setLocation_z(handMark.jointPoint[i].getLocation_z());
            jointPoint[i].setPixel_x(handMark.jointPoint[i].getPixel_x());
            jointPoint[i].setPixel_y(handMark.jointPoint[i].getPixel_y());
        }
        for(int i=1;i<22;i++){
            fingerBones[i]=new Vector3D(handMark.jointPoint[i].getLocation_x()-handMark.jointPoint[i-1].getLocation_x(),
                    handMark.jointPoint[i].getLocation_y()-handMark.jointPoint[i-1].getLocation_y(),
                    handMark.jointPoint[i].getLocation_z()-handMark.jointPoint[i-1].getLocation_z());
        }
        //init discontinuous joint
        for(int i:specialP){
            fingerBones[i]=new Vector3D(handMark.jointPoint[i].getLocation_x()-handMark.jointPoint[0].getLocation_x(),
                    handMark.jointPoint[i].getLocation_y()-handMark.jointPoint[0].getLocation_y(),
                    handMark.jointPoint[i].getLocation_z()-handMark.jointPoint[0].getLocation_z());
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
        handX=new Vector3D(bone5to13.getX(),bone5to13.getY(),bone5to13.getZ());
        handY=new Vector3D(fingerBones[9].getX(),fingerBones[9].getY(),fingerBones[9].getZ());
        handZ=Vector3D.crossProd(handX,handY);
        handX=handX.normalized();
        handY=handY.normalized();
        handZ=handZ.normalized();
        for(int i=1;i<22;i++){
            fingerBonesLocal[i].setX(Vector3D.pointProd(handX,fingerBones[i]));
            fingerBonesLocal[i].setY(Vector3D.pointProd(handY,fingerBones[i]));
            fingerBonesLocal[i].setZ(Vector3D.pointProd(handZ,fingerBones[i]));
        }

    }
    public void angleCalc(HandPosture3D standard) {
        //save calibrated length
        calibratedLength=standard.calibratedLength.clone();

        for(int i=2;i<21;i++){
            taps[i]=Math.acos(Vector3D.pointProd(fingerBones[i],fingerBones[i-1])/fingerBones[i].lengthL2()/fingerBones[i-1].lengthL2());
        }
        for(int i:specialP){
            waves[i]=Math.asin(fingerBonesLocal[i+1].getX()/calibratedLength[i+1]);
            Vector3D normVec=Vector3D.crossProd(fingerBones[i],handZ);
            Vector3D optBone=Vector3D.minus(fingerBones[i+1],Vector3D.scalarProd(Vector3D.pointProd(fingerBones[i],normVec),normVec));
            taps[i]=Math.acos(Vector3D.pointProd(optBone,fingerBones[i-1]));
        }
        taps[1]=Math.asin(fingerBonesLocal[1].getZ()/fingerBones[1].lengthL2());
        //local angles of finger1 need further optimizing
        Vector3D f1xy=Vector3D.minus(fingerBones[1],Vector3D.scalarProd(fingerBonesLocal[1].getZ(),handZ));
        waves[1]=Math.acos(Vector3D.pointProd(handX,f1xy)/f1xy.lengthL2());
        for(int i=1;i<21;i++){
            taps[i]=Math.acos(Vector3D.pointProd(fingerBones[i+1],fingerBones[i])/(fingerLength[i+1]*fingerLength[i]));
        }
    }
    //rules for optimize hand posture based on joint mechanical structure should be specified later
    public void angleOptForMp() {


        for(int i=1;i<21;i++){
            taps[i]=Math.acos(Vector3D.pointProd(fingerBones[i+1],fingerBones[i])/(fingerLength[i+1]*fingerLength[i]));
        }
    }

    //special points needing deal
    private int[] specialP={1,5,9,13,17};
    //basic hand posture
    public Position[] jointPoint =new Position[21];
    public Vector3D[] fingerBones=new Vector3D[20];
    public Double[] fingerLength=new Double[20];
    public Double[] taps =new Double[20];
    public Double[] waves=new Double[20];
    public double finger1rotate;
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
    public Vector3D handZ;
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
