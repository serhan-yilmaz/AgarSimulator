/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public class Angle {
    private double angle;
    private final static double TWO_PI=2*Math.PI;
    public Angle(double angle){
        this.angle=angle;
        correctAngle();
    }
    public Angle(Angle a){
        this.angle=a.angle;
        correctAngle();
    }
    private void correctAngle(){
        this.angle=this.angle%(TWO_PI);
        if(this.angle<0)this.angle+=TWO_PI;
    }
    public static Angle getSum(Angle a1,Angle a2){
        Angle result=new Angle(a1);
        result.add(a2);
        return result;
    }
    public void add(Angle a){
        angle+=a.angle;
        correctAngle();
    }
    public void add(double a){
        angle+=a;
        correctAngle();
    }
    public void subtract(Angle a){
        angle-=a.angle;
        correctAngle();
    }
    public void subtract(double a){
        angle-=a;
        correctAngle();
    }
    public void absDifference(Angle a){
        angle-=a.angle;
        angle=angle%TWO_PI;
        if(angle>Math.PI)angle-=TWO_PI;
        if(angle<-1*Math.PI)angle+=TWO_PI;
        angle=Math.abs(angle);
    }
    public double getAngle(){
        return angle;
    }
    public void setAngle(Angle a){
        angle=a.angle;
        correctAngle();
    }
    public void invertHorizontal(){
        angle=Math.PI-angle;
        correctAngle();
    }
    public void invertVertical(){
        angle=-1*angle;
        correctAngle();
    }
}
