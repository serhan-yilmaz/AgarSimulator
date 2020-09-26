/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public class ChaseSmaller extends StepStrategy{
    int search_flag=0;
    public ChaseSmaller(int num_of_turn) {
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
            for(int i=0;i<e.cellc;i++){
                if(entity!=e.cell[i]){
                    if(entity.isInSight(e.cell[i])&&entity.getMass()>e.cell[i].getMass()*1.2){
                        double dist=entity.data.pos.getDistanceTo(e.cell[i].data.pos);
                        if(distance==-1 || dist<distance){
                            index=i;
                            distance=dist;
                        }
                    }
                }
            }
            if(index!=-1){
                target_angle=entity.data.pos.getAngleTo(e.cell[index].data.pos);
                Vector s=new Vector(e.cell[index].data.pos);
                double dis=s.getDistanceTo(entity.data.pos)-entity.data.radius;
                s.x+=dis*Math.cos(e.cell[index].data.angle.getAngle())*e.cell[index].data.radius*e.cell[index].getApparentSpeed(entity)/(entity.data.speed*entity.data.radius);
                s.y+=dis*Math.sin(e.cell[index].data.angle.getAngle())*e.cell[index].data.radius*e.cell[index].getApparentSpeed(entity)/(entity.data.speed*entity.data.radius);
                target_angle=entity.data.pos.getAngleTo(s);
             /*   double dx=entity.pos.x-e.cell[index].pos.x;
                double dy=entity.pos.y-e.cell[index].pos.y;
                double n=dx*dx+dy*dy-e.cell[index].getApparentRadius(entity)*e.cell[index].getApparentRadius(entity);
                n=Math.sqrt(n);
                double ang2=Math.atan2(e.cell[index].getApparentRadius(entity),n)%(2*Math.PI);
                if(ang2<0)ang2+=2*Math.PI;
              //  System.out.println(ang2);*/
                sub_turn=2+ran.nextInt(6);
                search_flag=0;
            }else{
                if(sub_turn<=0||target_angle.getAngle()==entity.data.angle.getAngle()){
                    target_angle=new Angle(entity.data.angle);
                    target_angle.add((ran.nextInt(2)*2-1)*Math.PI/2);
                    sub_turn=6000;
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
        for(int i=0;i<e.cellc;i++){
            if(entity!=e.cell[i]){
                if(entity.isInSight(e.cell[i])&&entity.getMass()>e.cell[i].getMass()*1.025){
                    double dist=entity.data.pos.getDistanceTo(e.cell[i].data.pos);
                    if(distance==-1 || dist<distance){
                        index=i;
                        distance=dist;
                    }
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
            double dist=entity.data.pos.getDistanceTo(e.cell[index].data.pos);
            double mass_ratio=e.cell[index].getApparentMass(entity)/entity.getMass();
            value=10000*(mass_ratio*(entity.data.speed))/((1+dist-entity.data.radius)*e.cell[index].getApparentSpeed(entity)*e.scale.x);
            value=Math.max(value, 7);
        }
        return value;
    }
    @Override
    public int getStrategyType(){
        return StrategyList.CHASE_SMALLER;
    }
    @Override
    public double getPreferredTurn(){
       return 15+ran.nextInt(20);
    }
}
