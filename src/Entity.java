/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 *
 * @author Serhan-PC
 */
public abstract class Entity {
    EntityData data=new EntityData();
    public Entity(Entity e){
        this.data=e.data;
    }
    public Entity(Vector pos,Color color,double mass){
        this.data.angle = new Angle(data.ran.nextInt(360)*Math.PI/180);
        this.data.pos=pos;
        this.data.color=color;
        this.data.mass=mass;
        this.data.speed=0;
        this.data.base_speed=0;
        setMass(mass);
        this.data.energy=data.max_energy*Math.random();
    }
    public Entity(Vector pos,Color color,double mass,double base_speed,double base_energy){
        this.data.angle = new Angle(data.ran.nextInt(360)*Math.PI/180);
        this.data.pos=pos;
        this.data.color=color;
        this.data.mass=mass;
        this.data.base_speed=base_speed;
        this.data.base_energy=base_energy;
        setMass(mass);
        this.data.energy=data.max_energy*Math.random();
    }
    public Entity(double x,double y,Color color,double mass,double base_speed,double base_energy){
        this.data.angle = new Angle(data.ran.nextInt(360)*Math.PI/180);
        this.data.pos=new Vector(x,y);
        this.data.color=color;
        this.data.mass=mass;
        this.data.base_speed=base_speed;
        this.data.base_energy=base_energy;
        setMass(mass);
        this.data.energy=data.max_energy*Math.random();
    }
    public abstract void draw(Graphics g,AffineTransform nt);
    
    public void drawText(Graphics g,Environment e){
        
    }
    public void transformGraphics(Graphics g,AffineTransform nt){
        Graphics2D g2=(Graphics2D) g;
        AffineTransform at=g2.getTransform();
        g2.setTransform(nt);
        this.data.at=at;
        this.data.g2=g2;
    }
    public void restoreGraphics(){
        data.g2.setTransform(data.at);
    }
    public void step(Environment e,Entity entity){
        data.strategy.stepEnergy(entity);
        data.strategy.step(e, entity);
        if(data.strategy.isFinished()){
            StrategyList avail=entity.getAvailableStrategies();
            int strat=avail.getMaxValStrategy(e, entity);
           // data.strategy=StrategyFactory.createStrategy(avail[ran.nextInt(avail.length)],ran.nextInt(100)+50);
           // data.strategy=StrategyFactory.createStrategy(strat,data.ran.nextInt(20)+20);
            data.strategy=StrategyFactory.createStrategy(strat, (int) StrategyFactory.getStrategyTime(strat,e,entity));
        }
    }
    public final void setMass(double mass){
        this.data.mass=mass;
        adjustSize();
        adjustSpeed();
        adjustSight();
        adjustMaxEnergy();
    }
    public double getMass(){
        return data.mass;
    }
    public double getApparentMass(Entity e){
        return data.mass;
    }
    public double getApparentSpeed(Entity e){
        return data.speed;
    }
    public double getApparentRadius(Entity e){
        return data.radius;
    }
    public void adjustSize(){
        data.radius=(int) Math.round(5*Math.sqrt(data.mass/Math.PI));
    }
    public void adjustSpeed(){
        data.speed=data.base_speed/Math.pow(data.mass, 0.25);
        double ratio=(data.energy)/(data.max_energy);
        if(ratio<0.5){
            data.speed*=ratio*2;
        }
    }
    public void adjustSight(){
        data.sight_radius=(int) (50+data.radius*3);
        data.sight_cone_radius=(int)(150+data.radius*6);
        data.sight_cone_angle=Math.PI/6;
    }
    public void adjustMaxEnergy(){
        data.max_energy=data.base_energy*data.mass;
    }
    public abstract StrategyList getAvailableStrategies();
    
    public boolean isInContact(Entity e){
        return data.pos.getDistanceTo(e.data.pos)<=data.radius;
    }
    public boolean isInSight(Entity e){
        if(data.pos.getDistanceTo(e.data.pos)<=data.sight_radius+1*e.data.radius)return true;
        Angle angle_dif=data.pos.getAngleTo(e.data.pos);
        angle_dif.absDifference(data.angle);
        if(data.pos.getDistanceTo(e.data.pos)<=data.sight_cone_radius+1*e.data.radius&&angle_dif.getAngle()<=data.sight_cone_angle)return true;
        return false;
    }
}
