package com.example.testcalculator.keyboard.datastruct;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveSign {
    public ArrayList<Integer> usedId=new ArrayList();
    public ArrayList<double[]> waves =new ArrayList<>();

    public WaveSign() {
    }

    public WaveSign(ArrayList usedId, ArrayList<double[]> stadWaves) {
        this.usedId = usedId;
        this.waves = stadWaves;
    }
    public WaveSign(Integer[] usedId, double[][] stadWaves) {
        for(int id:usedId){
            this.usedId.add(id);
        }
        for (double[] value:stadWaves){
            this.waves.add(Arrays.copyOf(value, value.length));
        }
    }
}
