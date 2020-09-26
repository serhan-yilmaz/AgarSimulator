/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Serhan-PC
 */
public final class Sugar extends Food{
    public Sugar(double x, double y, Color color, double mass) {
        super(x, y, color, mass, 0,1000);
        adjustSize();
    }
    public Sugar(Vector pos, Color color, double mass) {
        super(pos, color, mass, 0,1000);
        adjustSize();
    }
    
    @Override
    public void draw(Graphics g,AffineTransform nt){
        transformGraphics(g,nt);
        g.setColor(data.color);
        g.fillRect(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        g.setColor(Color.black);
        g.drawRect(data.pos.getX()-data.radius, data.pos.getY()-data.radius, 2*data.radius, 2*data.radius);
        restoreGraphics();
    }
    @Override
    public int getFoodType(){
        return FoodList.SUGAR;
    }
    @Override
    public double getApparentSpeed(Entity e){
        return data.speed=0;
    }
    @Override
    public StrategyList getAvailableStrategies(){
        StrategyList result=new StrategyList();
        result.add(StrategyList.STAND_STILL);
        return result;
    }
}
