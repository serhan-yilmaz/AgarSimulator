/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;

/**
 *
 * @author Serhan-PC
 */
public class MoveRandom extends StepStrategy{
    public MoveRandom(int num_of_turn) {
        super(num_of_turn);
    }
    @Override
    public void step(Environment e,Entity entity){
        super.step(e,entity);
        sub_turn--;
        if(sub_turn<=0){
            target_angle=new Angle(ran.nextInt(360)*Math.PI/180);
            x_inc=Math.cos(entity.data.angle.getAngle())*entity.data.speed;
            y_inc=Math.sin(entity.data.angle.getAngle())*entity.data.speed;
            sub_turn=6+ran.nextInt(10);
        }
        turn2Target(entity);
        moveEntity(e,entity);
    }
    @Override
    public int getStrategyType(){
        return StrategyList.MOVE_RANDOM;
    }
    @Override
    public double getValue(Environment e,Entity entity){
        double value;
        value=2+Math.random()*1;
        return value;
    }
    @Override
    public double getPreferredTurn(){
       return 20+ran.nextInt(25);
    }
}
