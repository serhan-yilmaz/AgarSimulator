/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Serhan-PC
 */
public final class Organism extends Food{

    public Organism(double x, double y, Color color, double mass, double speed,double base_energy) {
        super(x, y, color, mass, speed,base_energy);
        adjustSize();
    }
    public Organism(Vector pos, Color color, double mass, double speed,double base_energy) {
        super(pos, color, mass, speed,base_energy);
        adjustSize();
    }
    @Override
    public void draw(Graphics g,AffineTransform nt){
        transformGraphics(g,nt);
        g.setColor(data.color);
        g.fillOval(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        g.setColor(Color.black);
        g.drawOval(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        restoreGraphics();
    }
    @Override
    public int getFoodType(){
        return FoodList.ORGANISM;
    }
    @Override
    public StrategyList getAvailableStrategies(){
        StrategyList result=new StrategyList();
        result.add(StrategyList.STAND_STILL);
        result.add(StrategyList.MOVE_RANDOM);
        return result;
    }
}
