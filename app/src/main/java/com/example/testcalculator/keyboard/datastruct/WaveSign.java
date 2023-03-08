package com.example.testcalculator.keyboard.datastruct;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveSign {
    public ArrayList<Integer> usedId=new ArrayList();
    public ArrayList<Double[]> waves =new ArrayList<>();

    public WaveSign() {
    }

    public WaveSign(ArrayList usedId, ArrayList<Double[]> stadWaves) {
        this.usedId = usedId;
        this.waves = stadWaves;
    }
    public WaveSign(Integer[] usedId, Double[][] stadWaves) {
        for(int id:usedId){
            this.usedId.add(id);
        }
        for (Double[] value:stadWaves){
            this.waves.add(Arrays.copyOf(value, value.length));
        }
    }
}
