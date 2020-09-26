/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public class GrabFood extends StepStrategy{
    int search_flag=0;
    public GrabFood(int num_of_turn) {
        super(num_of_turn);
    }
    @Override
    public void step(Environment e,Entity entity){
        super.step(e,entity);
        setTarget(e,entity);
        turn2Target(entity);
        moveEntity(e,entity);
    }
    public void setTarget(Environment e,Entity entity){
        sub_turn--;
        if(sub_turn<=0||search_flag==1){
            int index=-1;
            double distance=-1;
            for(int i=0;i<e.foodc;i++){
                if(entity.isInSight(e.food[i])){
                    double dist=entity.data.pos.getDistanceTo(e.food[i].data.pos);
                    if(distance==-1 || dist<distance){
                        index=i;
                        distance=dist;
                    }
                }
            }
            if(index!=-1){
                target_angle=entity.data.pos.getAngleTo(e.food[index].data.pos);
                sub_turn=2+ran.nextInt(6);
                search_flag=0;
            }else{
                if(sub_turn<=0){
                    target_angle=new Angle(ran.nextInt(360)*Math.PI/180);
                    sub_turn=6+ran.nextInt(10);
                    search_flag=1;
                }
            }
            x_inc=Math.cos(entity.data.angle.getAngle())*entity.data.speed;
            y_inc=Math.sin(entity.data.angle.getAngle())*entity.data.speed;
        }
    }
    public int findTarget(Environment e,Entity entity){
        int index=-1;
        double distance=-1;
        for(int i=0;i<e.foodc;i++){
            if(entity.isInSight(e.food[i])){
                double dist=entity.data.pos.getDistanceTo(e.food[i].data.pos);
                if(distance==-1 || dist<distance){
                    index=i;
                    distance=dist;
                }
            }
        }
        return index;
    }
    @Override
    public double getValue(Environment e,Entity entity){
        double value=0;
        int index=findTarget(e,entity);
        if(index!=-1){
            double dist=entity.data.pos.getDistanceTo(e.food[index].data.pos);
            value=20000*(e.food[index].getApparentMass(entity))/((1+dist-entity.data.radius)*entity.getMass()*e.scale.x);
            value=Math.max(value, 6);
        }
        return value;
    }
    @Override
    public int getStrategyType(){
        return StrategyList.GRAB_FOOD;
    }
    @Override
    public double getPreferredTurn(){
       return 10+ran.nextInt(10);
    }
}
