/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Serhan-PC
 */
public abstract class CellDecorator extends Cell{
    protected Cell decorator;
    
    public CellDecorator(Cell cd){
        super(cd);
        this.decorator=cd;
    }
    
    @Override
    public abstract void draw(Graphics g,AffineTransform nt);
    
    @Override
    public abstract double getThreshold();
    
    @Override
    public final StrategyList getAvailableStrategies(){
        StrategyList list=new StrategyList();
        this.addAvailableStrategies(list);
        if(decorator!=null){
            //System.out.println("Here we are");
            decorator.addAvailableStrategies(list);
        }
        return list;
    }
    
    @Override
    public abstract void addAvailableStrategies(StrategyList avail);
    
    @Override
    public int getLevel(){
        int l=1;
        if(decorator!=null){
            l+=decorator.getLevel();
        }
        return l;
    }
    
}
