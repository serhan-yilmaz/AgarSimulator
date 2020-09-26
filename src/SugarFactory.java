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
public abstract class SugarFactory {
    
    public static Sugar createSugar(Vector pos,double scaling){
        Random rand=new Random();
        Color color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
        return new Sugar(pos,color,scaling*(3*Math.random()+2));
    }
}
