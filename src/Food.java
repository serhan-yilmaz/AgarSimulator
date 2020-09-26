/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;

/**
 *
 * @author Serhan-PC
 */
public abstract class Food extends Entity{
    public Food(double x, double y, Color color, double mass, double speed,double base_energy) {
        super(x, y, color, mass, speed,base_energy);
    }
    public Food(Vector pos, Color color, double mass, double speed,double base_energy) {
        super(pos, color, mass, speed,base_energy);
    }
    public abstract int getFoodType();
}
