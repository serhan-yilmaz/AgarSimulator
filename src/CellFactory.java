/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Serhan-PC
 */
public abstract class CellFactory {
    
    public static Cell createCell(Vector pos,double scaling){
        Random rand=new Random();
        Color color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),100);
        return new BasicCell(pos,color,scaling*(26*Math.random()+36),Math.sqrt(scaling)*(4.5+Math.random()*2.5),1500*scaling);
    }
    public static Cell createCell(Food fo){
        Random rand=new Random();
        Color color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),100);
        return new BasicCell(fo.data.pos,color,fo.getMass(),fo.data.base_speed*6,fo.data.base_energy);
    }
}
