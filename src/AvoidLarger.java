/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public class AvoidLarger extends StepStrategy{
    int search_flag=1;
    double[] dir_values;
    public AvoidLarger(int num_of_turn) {
        super(num_of_turn);
        this.dir_values = new double[360];
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
            double dir=getDirection(e,entity);
            if(dir!=-1){
                //target_angle=(Math.PI+entity.pos.getAngleTo(e.cell[index].pos))%(2*Math.PI);
                target_angle=new Angle(dir);
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
    public double getDirection(Environment e,Entity entity){
        double direction=0;
        int index=-1;
        double maxval=-1;
        double distance=-1;
        for(int i=0;i<e.cellc;i++){
            if(entity!=e.cell[i]){
                if(entity.isInSight(e.cell[i])&&entity.getMass()<e.cell[i].getMass()){
                    double dist=entity.data.pos.getDistanceTo(e.cell[i].data.pos);
                    double xval=(e.mapWidth/2-Math.abs(entity.data.pos.x))/(e.mapWidth/2+0.0);
                    double yval=(e.mapHeight/2-Math.abs(entity.data.pos.y))/(e.mapHeight/2+0.0);
                    double xcons=400;
                    xval=Math.log(xval*xcons+1);
                    yval=Math.log(yval*xcons+1);
                    for(int j=0;j<360;j++){
                        double dir=j*Math.PI/180;
                        double dcos=Math.cos(dir);
                        double dsin=Math.sin(dir);
                        double dx=dist*dcos+entity.data.pos.x-e.cell[i].data.pos.x;
                        double dy=dist*dsin+entity.data.pos.y-e.cell[i].data.pos.y;
                        double val=Math.sqrt(dx*dx+dy*dy)/(dist);
                       // val=Math.pow(10, val)/(dist+1);
                        val/=(dist+1-e.cell[i].getApparentRadius(entity))*e.scale.x; // go exp
                        double mx=entity.data.pos.x+dcos/e.scale.x;
                        double my=entity.data.pos.y+dsin/e.scale.y;
                        if(my<=-1*e.mapHeight/2)my=-1*e.mapHeight/2+0.0001;
                        if(my>=e.mapHeight/2)my=e.mapHeight/2-0.0001;
                        if(mx<=-1*e.mapWidth/2)mx=-1*e.mapWidth/2+0.0001;
                        if(mx>=e.mapWidth/2)mx=e.mapWidth/2-0.0001;
                        double xval2=(e.mapWidth/2-Math.abs(mx))/(e.mapWidth/2+0.0);
                        double yval2=(e.mapHeight/2-Math.abs(my))/(e.mapHeight/2+0.0);
                        xval2=Math.log(xval2*xcons+1);
                        yval2=Math.log(yval2*xcons+1);
                        double valcx=Math.exp(125*(xval2/xval-1));
                        double valcy=Math.exp(125*(yval2/yval-1));
                        if(index==-1||true)val*=valcx*valcy;
                        if(index==-1||true){
                            double a1=(dir-entity.data.angle.getAngle());//-pi
                            double sa1=Math.signum(a1);
                            if(Math.abs(a1)>Math.PI){
                                a1-=2*sa1*Math.PI;
                                sa1*=-1;
                            }
                            val*=2*Math.pow(2, -0.5*Math.abs(a1)/(2*Math.PI*entity.data.turn_rate/(0.015)));
                        }
                        if(index==-1){
                            dir_values[j]=val;
                        }else{
                            dir_values[j]+=val;
                        }
                    }
                    if(distance==-1 || dist<distance){
                        index=i;
                        distance=dist;
                    }
                }
            }
        }
        if(index==-1)return -1;
        for(int j=0;j<360;j++){
            if(maxval==-1||maxval<dir_values[j]){
                maxval=dir_values[j];
                direction=j*Math.PI/180;
            }
        }
        return direction;
    }
    public int findTarget(Environment e,Entity entity){
        int index=-1;
        double distance=-1;
        for(int i=0;i<e.cellc;i++){
            if(entity!=e.cell[i]){
                if(entity.isInSight(e.cell[i])&&entity.getMass()<e.cell[i].getMass()){
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
            double speed_ratio=e.cell[index].getApparentSpeed(entity)/entity.data.speed;
            value=(12500*Math.sqrt(mass_ratio)*speed_ratio)/((1+dist-e.cell[index].getApparentRadius(entity))*e.scale.x);
        }
        return value;
    }
    @Override
    public int getStrategyType(){
        return StrategyList.AVOID_LARGER;
    }
    @Override
    public double getPreferredTurn(){
       return 30+ran.nextInt(40);
    }
}
