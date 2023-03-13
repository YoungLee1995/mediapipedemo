package com.example.testcalculator.keyboard.signalcatch;

import static com.example.testcalculator.keyboard.headers.signPack.ShortMove.tapSigns;

import android.util.Log;

import com.example.testcalculator.keyboard.datastruct.HandMarks;
import com.example.testcalculator.keyboard.datastruct.WaveSign;
import com.example.testcalculator.keyboard.headers.Enums;
import com.example.testcalculator.keyboard.math.Statistics;

import java.util.ArrayList;

public class ShortTapCatch {
    public static Enums.tapSign shortTapCatch(HandMarks handMarks){
        //Enums.tapSign result= Enums.tapSign.noSignal;
        int signId=1;
        double baseThresh=0.5;
        int result=0;
        for (ArrayList<WaveSign> waves:tapSigns){
            for (WaveSign wave:waves){
                int waveDuration=wave.waves.get(0).length;
                //conv of real wave
                double selfRealConv=Statistics.selfConv(handMarks,waveDuration);
                if(selfRealConv<100.0){
                    selfRealConv = 5000.0;
                }
                //conv of pack wave
                double selfPackConv=0.0;
                double coConv=0.0;
                int relatedPNum=wave.usedId.size();
                for(double[] pointWave:wave.waves){
                    for (int i=0;i<relatedPNum;i++){
                        selfPackConv+=Statistics.aveConv(pointWave,pointWave);
                        double[] realWave=Statistics.lastNZeroMean(handMarks,waveDuration,wave.usedId.get(i));
                        coConv+=Statistics.aveConv(realWave,pointWave);
                    }
                }
                double correlation=coConv/Math.sqrt(selfPackConv*selfRealConv);
                if (correlation>baseThresh){
                    baseThresh=correlation;
                    result = signId;
                    Log.v("signId",signId+"");
                    //result.setCode(signId);
                }

                Log.v("MainActivity",selfRealConv+":"+selfPackConv+":"+coConv+":"+correlation);
            }
            signId++;
        }

        return Enums.tapSign.values()[result];
    }
}
