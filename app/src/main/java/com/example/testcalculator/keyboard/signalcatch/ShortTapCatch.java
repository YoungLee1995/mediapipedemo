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
        int listSize=handMarks.markList.size();
        for (int i=0;i<10;i++){
            if(!(handMarks.markList.get(listSize-10+i).historySign==Enums.tapSign.noSignal)){
                return Enums.tapSign.noSignal;
            }
        }
        //Enums.tapSign result= Enums.tapSign.noSignal;
        int signId=1;

        double baseThresh=0.1;

        int result=0;
        for (ArrayList<WaveSign> waves:tapSigns){
            for (WaveSign wave:waves){
                int waveDuration=wave.waves.get(0).length;
                //conv of real wave
                double selfRealConv=Statistics.selfConv(handMarks,waveDuration);
                if(selfRealConv<5000.0){
                    selfRealConv = 50000000000.0;

                }
                //conv of pack wave
                double selfPackConv=0.0;
                double coConv=0.0;
                int relatedPNum=wave.usedId.size();
                for (int i=0;i<relatedPNum;i++){
                    selfPackConv+=Statistics.aveConv(wave.waves.get(i),wave.waves.get(i));
                    double[] realWave=Statistics.lastNZeroMean(handMarks,waveDuration,wave.usedId.get(i));
                    coConv+=Statistics.aveConv(realWave,wave.waves.get(i));
                }
                double correlation=coConv/Math.sqrt(selfPackConv*selfRealConv);
                if (correlation>baseThresh){
                    Log.v("ShortTapCatch","自卷积："+selfRealConv+" 库卷积："+selfPackConv+" 协方差："+coConv+" 相关性："+correlation);
                    baseThresh=correlation;
                    result = signId;
                    //result.setCode(signId);
                }
            }
            signId++;
        }


        handMarks.markList.getLast().historySign=Enums.tapSign.values()[result];
        return Enums.tapSign.values()[result];
    }
}
