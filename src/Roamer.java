
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Serhan-PC
 */
public class Roamer extends CellDecorator{

    public Roamer(Cell cd) {
        super(cd);
    }

    @Override
    public void draw(Graphics g, AffineTransform nt) {
        if(decorator!=null){
            decorator.draw(g, nt);
        }
        transformGraphics(g,nt);
        g.setColor(Color.green.darker());
        int radius=(int) (data.radius*0.6);
        g.drawOval(data.pos.getX()-radius, data.pos.getY()-radius, 2*radius, 2*radius);
        restoreGraphics();
    }

    @Override
    public double getThreshold() {
        double a=275;
        if(decorator!=null){
            a=Math.max(a, decorator.getThreshold());
        }
        return a;
    }

    @Override
    public void addAvailableStrategies(StrategyList avail) {
        if(decorator!=null){
            decorator.addAvailableStrategies(avail);
        }
        avail.add(StrategyList.MOVE_LINEAR);
    }
    
}
