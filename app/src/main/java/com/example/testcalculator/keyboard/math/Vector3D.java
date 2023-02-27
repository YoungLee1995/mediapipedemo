package com.example.testcalculator.keyboard.math;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D plus(Vector3D v1,Vector3D v2){
        Vector3D result=new Vector3D(v1.getX()+ v2.getX(),v1.getY()+ v2.getY(),v1.getZ()+ v2.getZ());
        return result;
    }
    public static Vector3D minus(Vector3D v1,Vector3D v2){
        Vector3D result=new Vector3D(v1.getX()- v2.getX(),v1.getY()-v2.getY(),v1.getZ()- v2.getZ());
        return result;
    }
    public static double pointProd(Vector3D v1,Vector3D v2){
        double result=v1.getX()* v2.getX()+v1.getY()*v2.getY()+v1.getZ()*v2.getZ();
        return result;
    }
    public static Vector3D crossProd(Vector3D v1,Vector3D v2){
        Vector3D result=new Vector3D(v1.getY()*v2.getZ()- v2.getY()*v1.getZ(),v1.getZ()*v2.getX()- v2.getZ()*v1.getX(),
                v1.getX()*v2.getY()- v2.getX()*v1.getY());
        return result;
    }
    public static Vector3D scalarProd(double a,Vector3D v2){
        Vector3D result=new Vector3D(a*v2.getX(),a*v2.getY(),a*v2.getZ());
        return result;
    }

    public double lengthL2(){
        return Math.sqrt(Vector3D.pointProd(this,this));
    }
    
    public Vector3D normalized(){
        double x=this.getX()/this.lengthL2();
        double y=this.getY()/this.lengthL2();
        double Z=this.getZ()/this.lengthL2();
        Vector3D result=new Vector3D(x,y,z);
        return result;
    }







    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
