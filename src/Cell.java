/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Serhan-PC
 */
public abstract class Cell extends Entity{
    String name;
    public Cell(Cell c){
        super(c);
        name=c.name;
    }
    public Cell(double x, double y, Color color, double mass, double speed,double base_energy) {
        super(x, y, color, mass, speed,base_energy);
        this.name = NameCreator.getRandomName();
        data.radius=(int) Math.round(5*Math.sqrt(mass/Math.PI));
    }
    public Cell(Vector pos, Color color, double mass, double speed,double base_energy) {
        super(pos, color, mass, speed,base_energy);
        this.name = NameCreator.getRandomName();
        data.radius=(int) Math.round(5*Math.sqrt(mass/Math.PI));
    }
    @Override
    public void draw(Graphics g,AffineTransform nt){
        transformGraphics(g,nt);
        g.setColor(new Color(255,255,0,100));
        g.fillOval(data.pos.getX()-data.sight_radius, data.pos.getY()-data.sight_radius, 2*data.sight_radius, 2*data.sight_radius);
        g.fillArc(data.pos.getX()-data.sight_cone_radius, data.pos.getY()-data.sight_cone_radius, 2*data.sight_cone_radius, 2*data.sight_cone_radius, (int)(-180*(data.angle.getAngle()+data.sight_cone_angle)/Math.PI),(int)(180*(2*data.sight_cone_angle)/Math.PI));
        g.setColor(data.color);
        g.fillOval(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        g.setColor(Color.black);
        g.drawOval(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        g.setColor(Color.black);
        g.drawLine(data.pos.getX(), data.pos.getY(), (int) (data.pos.getX()+Math.cos(data.angle.getAngle())*data.radius), (int) (data.pos.getY()+Math.sin(data.angle.getAngle())*data.radius));
        restoreGraphics();
    }
    @Override
    public void drawText(Graphics g,Environment e){
        g.setColor(Color.black);
        g.setFont(new Font("Arial",0,16));
        g.drawString(name, (int) (e.mapWidth*e.scale.x/2+data.pos.getX()*e.scale.x-4), (int) (e.mapHeight*e.scale.y/2+data.pos.getY()*e.scale.y-10));
        g.drawString(String.valueOf(data.strategy.getStrategyType()), (int) (e.mapWidth*e.scale.x/2+data.pos.getX()*e.scale.x-4), (int) (e.mapHeight*e.scale.y/2+data.pos.getY()*e.scale.y+7));
        g.drawString(String.format("%1$,.2f",100*data.energy/data.max_energy) , (int) (e.mapWidth*e.scale.x/2+data.pos.getX()*e.scale.x-4), (int) (e.mapHeight*e.scale.y/2+data.pos.getY()*e.scale.y+22));
        g.drawString(String.valueOf(data.strategy.num_of_turn) , (int) (e.mapWidth*e.scale.x/2+data.pos.getX()*e.scale.x-4), (int) (e.mapHeight*e.scale.y/2+data.pos.getY()*e.scale.y+40));
    }
    @Override
    public abstract StrategyList getAvailableStrategies();
    public abstract void addAvailableStrategies(StrategyList avail);
    public double getThreshold(){
        double a=125;
        return a;
    }
    public int getLevel(){
        int l=0;
        return l;
    }
    
}
