/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Serhan-PC
 */
public class MoveLinear extends StepStrategy{
    public MoveLinear(int num_of_turn) {
        super(num_of_turn);
    }
    @Override
    public void step(Environment e,Entity entity){
        super.step(e,entity);
        if(sub_turn<=0){
            target_angle=new Angle(entity.data.angle.getAngle()+(1-2*Math.random())*Math.PI*0.05);
            adjust2Angle(entity);
        }
        turn2Target(entity);
        moveEntity(e,entity);
    }
    public void adjust2Angle(Entity entity){
            x_inc=Math.cos(entity.data.angle.getAngle())*entity.data.speed;
            y_inc=Math.sin(entity.data.angle.getAngle())*entity.data.speed;
            sub_turn=1;
    }
    @Override
    public void moveEntity(Environment e,Entity entity){
        entity.data.pos.x+=x_inc;
        entity.data.pos.y+=y_inc;
        if(entity.data.pos.x<=-1*e.mapWidth/2){
            entity.data.pos.x=-1*e.mapWidth/2;
            if(Math.cos(target_angle.getAngle())<0){
             //   sub_turn=0;
                target_angle.invertHorizontal();
                adjust2Angle(entity);
            }
        }
        if(entity.data.pos.x>=e.mapWidth/2){
            entity.data.pos.x=e.mapWidth/2;
            if(Math.cos(target_angle.getAngle())>0){
              //  sub_turn=0;
                target_angle.invertHorizontal();
                adjust2Angle(entity);
            }
        }
        if(entity.data.pos.y<=-1*e.mapHeight/2){
            entity.data.pos.y=-1*e.mapHeight/2;
            if(Math.sin(target_angle.getAngle())<0){
                //sub_turn=0;
                target_angle.invertVertical();
                adjust2Angle(entity);
            }
        }
        if(entity.data.pos.y>=e.mapHeight/2){
            entity.data.pos.y=e.mapHeight/2;
            if(Math.sin(target_angle.getAngle())>0){
              //  sub_turn=0;
                target_angle.invertVertical();
                adjust2Angle(entity);
            }
        }
    }
    @Override
    public int getStrategyType(){
        return StrategyList.MOVE_LINEAR;
    }
    @Override
    public double getValue(Environment e,Entity entity){
        double value;
        value=2.5+Math.random()*1.5;
        return value;
    }
    @Override
    public double getPreferredTurn(){
       return 15+ran.nextInt(25);
    }
}
