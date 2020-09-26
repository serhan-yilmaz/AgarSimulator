
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Serhan-PC
 */
public class EntityData {
    Vector pos;
    StepStrategy strategy=new StandStill(10);
    public Color color;
    protected double mass;
    public double speed;
    double base_speed;
    Angle angle;
    int radius;
    int sight_radius;
    int sight_cone_radius;
    double sight_cone_angle;
    double turn_rate=0.015;
    double base_energy=1000;
    double max_energy;
    double energy;
    Random ran=new Random();
    protected Graphics2D g2;
    protected AffineTransform at;
    int cell_eaten=0;
    int food_eaten=0;
}
