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
public abstract class StepStrategy {
    Random ran=new Random();
    protected int num_of_turn=15;
    protected int sub_turn=0;
    protected double x_inc;
    protected double y_inc;
    protected Angle target_angle=new Angle(0);
    public StepStrategy(int num_of_turn){
        this.num_of_turn=num_of_turn;
    }
    public void step(Environment e,Entity entity){
        num_of_turn--;
    }
    public boolean isFinished(){
        return num_of_turn<=0;   
    }
    public final void turn2Target(Entity entity){
        if(target_angle.getAngle()<0||target_angle.getAngle()>2*Math.PI)throw new IllegalArgumentException("Target_angle : "+target_angle);
        if(entity.data.angle.getAngle()<0||entity.data.angle.getAngle()>2*Math.PI)throw new IllegalArgumentException("angle : "+entity.data.angle);
        if(target_angle.getAngle()!=entity.data.angle.getAngle()){
            //target_angle=new Angle(Math.PI);            //double a4=(target_angle-entity.data.angle+Math.PI*2);
            double a1=(target_angle.getAngle()-entity.data.angle.getAngle());//-pi
            double sa1=Math.signum(a1);
            if(Math.abs(a1)>Math.PI){
                a1-=2*sa1*Math.PI;
                sa1*=-1;
            }
            double turn_rate=2*Math.PI*entity.data.turn_rate;
            if(Math.abs(a1)<turn_rate){
                entity.data.angle=new Angle(target_angle);
            }else{
                entity.data.angle.add(sa1*turn_rate);
            }
            x_inc=Math.cos(entity.data.angle.getAngle())*entity.data.speed;
            y_inc=Math.sin(entity.data.angle.getAngle())*entity.data.speed;
        }
    }
    public void moveEntity(Environment e,Entity entity){
        entity.data.pos.x+=x_inc;
        entity.data.pos.y+=y_inc;
        if(entity.data.pos.x<=-1*e.mapWidth/2){
            entity.data.pos.x=-1*e.mapWidth/2;
            if(Math.cos(target_angle.getAngle())<0)sub_turn=0;
        }
        if(entity.data.pos.x>=e.mapWidth/2){
            entity.data.pos.x=e.mapWidth/2;
            if(Math.cos(target_angle.getAngle())>0)sub_turn=0;
        }
        if(entity.data.pos.y<=-1*e.mapHeight/2){
            entity.data.pos.y=-1*e.mapHeight/2;
            if(Math.sin(target_angle.getAngle())<0)sub_turn=0;
        }
        if(entity.data.pos.y>=e.mapHeight/2){
            entity.data.pos.y=e.mapHeight/2;
            if(Math.sin(target_angle.getAngle())>0)sub_turn=0;
        }
    }
    public abstract int getStrategyType();
    
    public void stepEnergy(Entity e){
        e.adjustSpeed();
        e.data.energy-=0.5*e.getMass()*e.data.speed*e.data.speed;
        if(e.data.energy<0)e.data.energy=0;
    }
    public abstract double getValue(Environment e,Entity entity);
    public abstract double getPreferredTurn();
}
