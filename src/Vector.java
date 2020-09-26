/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;

/**
 *
 * @author Serhan-PC
 */
public class Vector {
    public double x;
    public double y;
    public Vector(double x,double y){
        this.x=x;
        this.y=y;
    }
    public Vector(Vector v){
        this.x=v.x;
        this.y=v.y;
    }
    public double getDistanceTo(Vector a){
        return Math.sqrt((x-a.x)*(x-a.x)+(y-a.y)*(y-a.y));
    }
    public Angle getAngleTo(Vector a){
        Angle result=new Angle(Math.atan2(a.y-y, a.x-x));
        return result;
    }
    public int getX(){
        return (int) Math.round(x);
    }
    public int getY(){
        return (int) Math.round(y);
    }
    public static Vector getRandomVector(int mapWidth,int mapHeight){
        Random rand=new Random();
        return new Vector(rand.nextInt(mapWidth)-mapWidth/2,rand.nextInt(mapHeight)-mapHeight/2);
    }
    public static Vector getBiggerRandomVector(int mapWidth,int mapHeight,double scaling){
        while(true){
            Vector result=getRandomVector((int)(scaling*mapWidth),(int)(scaling*mapHeight));
            if(Math.abs(result.x)>1.35*mapWidth/2||Math.abs(result.y)>1.35*mapHeight/2)return result;
        }
    }
}
