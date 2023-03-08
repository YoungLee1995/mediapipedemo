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
                int relatedPNum=wave.usedId.size();
                for (int i=0;i<relatedPNum;i++){
                    int picNum=wave.waves.get(i).length;
                    int valueId=wave.usedId.get(i);
                    Double[] realWave= Statistics.lastNZeroMean(handMarks,picNum,valueId);
                    double correlation=Statistics.correlationCalc(realWave,wave.waves.get(i));
                    if (correlation>baseThresh){
                        baseThresh=correlation;
                        result.setCode(signId);
                    }
                }
            }
            signId++;
        }

        return result;
    }
}
