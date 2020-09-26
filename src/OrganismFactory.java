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
public abstract class OrganismFactory {
    
    public static Organism createOrganism(Vector pos,double scaling){
        Random rand=new Random();
        Color color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
        return new Organism(pos,color,scaling*(5*Math.random()+3),Math.sqrt(scaling)*(0.4+rand.nextInt(100)*0.02),scaling*1000);
    }
}
