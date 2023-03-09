package com.example.testcalculator.keyboard.signalcatch;

import static com.example.testcalculator.keyboard.headers.signPack.ShortMove.tapSigns;

import com.example.testcalculator.keyboard.datastruct.HandMarks;
import com.example.testcalculator.keyboard.datastruct.WaveSign;
import com.example.testcalculator.keyboard.headers.Enums;
import com.example.testcalculator.keyboard.math.Statistics;

import java.util.ArrayList;

public class ShortTapCatch {
    public static Enums.tapSign shortTapCatch(HandMarks handMarks){
        Enums.tapSign result= Enums.tapSign.noSignal;
        int signId=1;
        double baseThresh=0.5;
        for (ArrayList<WaveSign> waves:tapSigns){
            for (WaveSign wave:waves){
                int waveDuration=wave.waves.get(0).length;
                //conv of real wave
                double selfRealConv=Statistics.selfConv(handMarks,waveDuration);
                //conv of pack wave
                double selfPackConv=0.0;
                double coConv=0.0;
                int relatedPNum=wave.usedId.size();
                for(double[] pointWave:wave.waves){
                    for (int i=0;i<relatedPNum;i++){
                        selfPackConv+=Statistics.aveCorrelation(pointWave,pointWave);
                        double[] realWave=Statistics.lastNZeroMean(handMarks,waveDuration,wave.usedId.get(i));
                        coConv+=Statistics.aveCorrelation(realWave,pointWave);
                    }
                }
                double correlation=coConv/Math.sqrt(selfPackConv*selfRealConv);
                if (correlation>baseThresh){
                    baseThresh=correlation;
                    result.setCode(signId);
                }
            }
            signId++;
        }

        return result;
    }
}
