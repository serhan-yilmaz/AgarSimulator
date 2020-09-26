/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public class StandStill extends StepStrategy{

    public StandStill(int num_of_turn) {
        super(num_of_turn);
    }
    @Override
    public void step(Environment e,Entity entity){
        super.step(e,entity);
    }
    @Override
    public int getStrategyType(){
        return StrategyList.STAND_STILL;
    }
    @Override
    public void stepEnergy(Entity e){
        e.adjustSpeed();
        e.data.energy+=e.data.max_energy*0.003;
        if(e.data.energy>e.data.max_energy)e.data.energy=e.data.max_energy;
    }
    @Override
    public double getValue(Environment e,Entity entity){
        double value=0;
        double ratio=(entity.data.energy)/(entity.data.max_energy);
        if(ratio==0)return 1000000;
        if(ratio>0.5)value=1/(ratio*ratio*ratio*ratio);
        if(ratio<=0.5)value=32/(ratio*ratio*ratio*ratio)-16*31;
        return value;
    }
    @Override
    public double getPreferredTurn(){
       return 10+ran.nextInt(20);
    }
}
