/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Serhan-PC
 */
public abstract class StrategyFactory {

    private static final StepStrategy[] strategies=new StepStrategy[StrategyList.STRATEGY_AMOUNT];
    static {
        for(int i=0;i<StrategyList.STRATEGY_AMOUNT;i++)strategies[i]=createStrategy(i,0);
    }
    public static StepStrategy createStrategy(int type,int turn_no){
        if(type==StrategyList.STAND_STILL){
            return new StandStill(turn_no);
        }
        if(type==StrategyList.MOVE_RANDOM){
            return new MoveRandom(turn_no);
        }
        if(type==StrategyList.GRAB_FOOD){
            return new GrabFood(turn_no);
        }
        if(type==StrategyList.CHASE_SMALLER){
            return new ChaseSmaller(turn_no);
        }
        if(type==StrategyList.AVOID_LARGER){
            return new AvoidLarger(turn_no);
        }
        if(type==StrategyList.MOVE_LINEAR){
            return new MoveLinear(turn_no);
        }
        throw new UnsupportedOperationException("Strategy Unknown");
     //  return null;
    }
    public static double getStrategyValue(int strat_no,Environment e,Entity entity){
        return strategies[strat_no].getValue(e, entity);
    }
    public static double getStrategyTime(int strat_no,Environment e,Entity entity){
        return strategies[strat_no].getPreferredTurn();
    }
}
