
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
public class Hunter extends CellDecorator{

    public Hunter(Cell cd) {
        super(cd);
    }

    @Override
    public void draw(Graphics g,AffineTransform nt) {
        if(decorator!=null){
            decorator.draw(g, nt);
        }
        transformGraphics(g,nt);
        g.setColor(Color.red);
        int radius=(int) (data.radius*0.8);
        g.drawOval(data.pos.getX()-radius, data.pos.getY()-radius, 2*radius, 2*radius);
        restoreGraphics();
    }
    @Override
    public void addAvailableStrategies(StrategyList avail) {
        if(decorator!=null){
            decorator.addAvailableStrategies(avail);
        }
        avail.add(StrategyList.CHASE_SMALLER);
    }
    @Override
    public double getThreshold() {
        double a=650;
        if(decorator!=null){
            a=Math.max(a, decorator.getThreshold());
        }
        return a;
    }

    

    
}
