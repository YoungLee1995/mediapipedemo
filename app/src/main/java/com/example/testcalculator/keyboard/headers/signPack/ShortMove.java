package com.example.testcalculator.keyboard.headers.signPack;

import com.example.testcalculator.keyboard.datastruct.WaveSign;

import java.util.ArrayList;
import java.util.Arrays;

public class ShortMove {
    public static Double[][] wave0001={{0.0,0.55,0.0},{0.0,1.0,0.0}};
    public static Integer[] id0001={3,4};

    public static Double[][] wave0002={{0.0,0.25,0.55,0.0},{0.0,0.5,1.0,0.0}};
    public static Integer[] id0002={3,4};

    public static Double[][] wave0003={{0.0,0.55,0.25,0.0},{0.0,1.0,0.5,0.0}};
    public static Integer[] id0003={3,4};

    public static Double[][] wave0004={{0.0,0.25,0.55,0.25,0.0},{0.0,0.5,1.0,0.5,0.0}};
    public static Integer[] id0004={3,4};
    public static Double[][] wave0005={{0.0,0.4,0.0},{0.0,0.7,0.0},{0.0,1.0,0.0}};
    public static Integer[] id0005={6+21,7+21,8+21};

    public static Double[][] wave0006={{0.0,0.2,0.4,0.0},{0.0,0.35,0.7,0.0},{0.0,0.5,1.0,0.0}};
    public static Integer[] id0006={6+21,7+21,8+21};

    public static Double[][] wave0007={{0.0,0.4,0.2,0.0},{0.0,0.7,0.35,0.0},{0.0,1.0,0.5,0.0}};
    public static Integer[] id0007={6+21,7+21,8+21};

    public static Double[][] wave0008={{0.0,0.2,0.4,0.2,0.0},{0.0,0.35,0.7,0.35,0.0},{0.0,0.5,1.0,0.5,0.0}};
    public static Integer[] id0008={7+21,8+21};

    public static Double[][] wave0009= {
            {0.0,0.4,0.0},{0.0,0.7,0.0},{0.0,1.0,0.0},
            {0.0,0.4,0.0},{0.0,0.7,0.0},{0.0,1.0,0.0},
    };
    public static Integer[] id0009={10+21,11+21,12+21,14+21,15+21,16+21};

    public static Double[][] wave0010={
            {0.0,0.2,0.4,0.0},{0.0,0.35,0.7,0.0},{0.0,0.5,1.0,0.0},
            {0.0,0.2,0.4,0.0},{0.0,0.35,0.7,0.0},{0.0,0.5,1.0,0.0},
    };
    public static Integer[] id0010={10+21,11+21,12+21,14+21,15+21,16+21};

    public static Double[][] wave0011={
            {0.0,0.4,0.2,0.0},{0.0,0.7,0.35,0.0},{0.0,1.0,0.5,0.0},
            {0.0,0.4,0.2,0.0},{0.0,0.7,0.35,0.0},{0.0,1.0,0.5,0.0},
    };
    public static Integer[] id0011={10+21,11+21,12+21,14+21,15+21,16+21};

    public static Double[][] wave0012={
            {0.0,0.2,0.4,0.2,0.0},{0.0,0.35,0.7,0.35,0.0},{0.0,0.5,1.0,0.5,0.0},
            {0.0,0.2,0.4,0.2,0.0},{0.0,0.35,0.7,0.35,0.0},{0.0,0.5,1.0,0.5,0.0},
    };
    public static Integer[] id0012={10+21,11+21,12+21,14+21,15+21,16+21};

    public static Double[][] wave0013= {
            {0.0,0.4,0.0},{0.0,0.7,0.0},{0.0,1.0,0.0},
            {0.0,0.24,0.0},{0.0,0.42,0.0},{0.0,0.6,0.0},
    };
    public static Integer[] id0013={18+21,19+21,20+21,14+21,15+21,16+21};

    public static Double[][] wave0014={
            {0.0,0.2,0.4,0.0},{0.0,0.35,0.7,0.0},{0.0,0.5,1.0,0.0},
            {0.0,0.12,0.24,0.0},{0.0,0.21,0.42,0.0},{0.0,0.3,0.6,0.0},
    };
    public static Integer[] id0014={18+21,19+21,20+21,14+21,15+21,16+21};

    public static Double[][] wave0015={
            {0.0,0.4,0.2,0.0},{0.0,0.7,0.35,0.0},{0.0,1.0,0.5,0.0},
            {0.0,0.24,0.12,0.0},{0.0,0.42,0.21,0.0},{0.0,0.6,0.3,0.0},
    };
    public static Integer[] id0015={18+21,19+21,20+21,14+21,15+21,16+21};

    public static Double[][] wave0016={
            {0.0,0.2,0.4,0.2,0.0},{0.0,0.35,0.7,0.35,0.0},{0.0,0.5,1.0,0.5,0.0},
            {0.0,0.12,0.24,0.12,0.0},{0.0,0.21,0.42,0.21,0.0},{0.0,0.3,0.6,0.3,0.0},
    };
    public static Integer[] id0016={18+21,19+21,20+21,14+21,15+21,16+21};

    public static WaveSign wave01=new WaveSign(id0001,wave0001);
    public static WaveSign wave02=new WaveSign(id0002,wave0002);
    public static WaveSign wave03=new WaveSign(id0003,wave0003);
    public static WaveSign wave04=new WaveSign(id0004,wave0004);
    public static WaveSign wave05=new WaveSign(id0005,wave0005);
    public static WaveSign wave06=new WaveSign(id0006,wave0006);
    public static WaveSign wave07=new WaveSign(id0007,wave0007);
    public static WaveSign wave08=new WaveSign(id0008,wave0008);
    public static WaveSign wave09=new WaveSign(id0009,wave0009);
    public static WaveSign wave10=new WaveSign(id0010,wave0010);
    public static WaveSign wave11=new WaveSign(id0011,wave0011);
    public static WaveSign wave12=new WaveSign(id0012,wave0012);
    public static WaveSign wave13=new WaveSign(id0013,wave0013);
    public static WaveSign wave14=new WaveSign(id0014,wave0014);
    public static WaveSign wave15=new WaveSign(id0015,wave0015);
    public static WaveSign wave16=new WaveSign(id0016,wave0016);

    public static ArrayList<WaveSign> f1tap=new ArrayList<>(Arrays.asList(wave01,wave02,wave03,wave04));
    public static ArrayList<WaveSign> f2tap=new ArrayList<>(Arrays.asList(wave05,wave06,wave07,wave08));
    public static ArrayList<WaveSign> f3tap=new ArrayList<>(Arrays.asList(wave09,wave10,wave11,wave12));
    public static ArrayList<WaveSign> f5tap=new ArrayList<>(Arrays.asList(wave13,wave14,wave15,wave16));

    public static ArrayList<ArrayList<WaveSign>> tapSigns=new ArrayList<>(Arrays.asList(f1tap,f2tap,f3tap,f5tap));
}
