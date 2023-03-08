package com.example.testcalculator.keyboard.parameters;

import static com.example.testcalculator.keyboard.headers.ConstPredefinedKt.pi;

import com.example.testcalculator.keyboard.datastruct.HandMark;
import com.example.testcalculator.keyboard.datastruct.HandPosture2D;
import com.example.testcalculator.keyboard.datastruct.HandPosture3D;
import com.example.testcalculator.keyboard.datastruct.Position;
import com.example.testcalculator.keyboard.math.TriangleFunc;
import com.example.testcalculator.keyboard.math.Vector3D;

public class StandardHand {
    HandMark stddHandCurve = new HandMark();
    HandMark straightHand = new HandMark();
    HandPosture2D straightFinger2D;
    HandPosture3D straightFinger3D;
    HandPosture3D curveFinger3D;
    Double[] specialAngles = new Double[5]; //方便计算，第五个永远是0
    Vector3D[] fingerBase=new Vector3D[5];
    public Double[] rawHand21Points = {
            0.02689357, -0.03279744, 0.07792561,
            -0.00793761, -0.02665891, 0.05558753,
            -0.03071143, -0.03535284, 0.06632005,
            -0.04880968, -0.04723868, 0.07740711,
            -0.06246286, -0.05927356, 0.08843022,
            0.02074999, -0.01323506, 0.05636061,
            0.00969039, -0.01247777, 0.03761269,
            -0.00224882, -0.01352845, 0.02417908,
            -0.01597558, -0.01961445, 0.01362697,
            -0.02705022, -0.0272398, 0.00756498,
            0.01328305, 0.00180577, 0.04758681,
            0.00333283, 0.00084229, 0.03036366,
            -0.00692114, -0.00038824, 0.01895402,
            -0.01662379, -0.00252536, 0.01093822,
            -0.02497451, -0.00474308, 0.00496907,
            0.0099487, 0.0193277, 0.03555845,
            0.0005057, 0.01564747, 0.02243427,
            -0.00800728, 0.01309624, 0.01371006,
            -0.0148636, 0.01041461, 0.00822048,
            -0.02018279, 0.00828547, 0.00454536,
    };

    public StandardHand() {
        for (int i = 0; i < 21; i++) {
            stddHandCurve.jointPoint[i] = new Position(rawHand21Points[i * 3] * 1000.0,
                    rawHand21Points[i * 3 + 1] * 1000.0, rawHand21Points[i * 3 + 2] * 1000.0,
                    0.0, 0.0);
        }
        curveFinger3D = new HandPosture3D(stddHandCurve);
        //shift curve hand to straight 2D hand
        Double[] fl = curveFinger3D.fingerLength;
        straightHand.jointPoint[0] = new Position(0.0, 0.0, 0.0);
        double l = 0.0;
        for (int i = 9; i < 13; i++) {
            l += fl[i];
            straightHand.jointPoint[i] = new Position(0.0, l, 0.0);
        }
        for (int i = 0; i < 4; i++) {
            specialAngles[i] = TriangleFunc.getAngle(curveFinger3D.specialBones[i].lengthL2(), fl[i * 4 + 5], fl[i * 4 + 1]);
        }
        double theta = specialAngles[0] + specialAngles[1];
        for (int i = 0; i < 5; i++) {
            fingerBase[i]=new Vector3D(Math.sin(theta),Math.cos(theta),0.0);
            theta=theta-specialAngles[i];
            double fingerLength=0.0;
            for (int j=0;j<4;j++){
                int id=i*4+j+1;
                fingerLength=fingerLength+fl[id];
                Vector3D fingerPoint=Vector3D.scalarProd(fingerLength,fingerBase[i]);
                straightHand.jointPoint[i]=new Position(fingerPoint);
            }
        }
        straightFinger2D=new HandPosture2D(straightHand);
        straightFinger3D=new HandPosture3D(straightHand);
    }

}
