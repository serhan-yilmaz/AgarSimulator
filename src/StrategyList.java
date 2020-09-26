/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Serhan-PC
 */
public class StrategyList {
    public static final int STAND_STILL=0;
    public static final int MOVE_RANDOM=1;
    public static final int GRAB_FOOD=2;
    public static final int CHASE_SMALLER=3;
    public static final int AVOID_LARGER=4;
    public static final int MOVE_LINEAR=5;
    
    public static final int STRATEGY_AMOUNT=6;
    
    public int[] list;
    public int listc;

    public StrategyList() {
        this.listc = 0;
        this.list = new int[STRATEGY_AMOUNT];
    }
    public void add(int type){
        if(type>=0&&type<STRATEGY_AMOUNT){
            for(int j=0;j<listc;j++){
                if(list[j]==type)return;
            }
            list[listc++]=type;
        }
    }
    public int getMaxValStrategy(Environment e,Entity entity){
            double max_val=0;
            int index=-1;
            for(int i=0;i<listc;i++){
                double val=StrategyFactory.getStrategyValue(list[i], e, entity);
                if(index==-1||max_val<val){
                    max_val=val;
                    index=i;
                }
            }
            return list[index];
    }
}
