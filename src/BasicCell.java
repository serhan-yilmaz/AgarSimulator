
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Serhan-PC
 */
public class BasicCell extends Cell{

    public BasicCell(Vector pos, Color color, double mass, double speed, double base_energy) {
        super(pos, color, mass, speed, base_energy);
    }

    public BasicCell(double x, double y, Color color, double mass, double speed, double base_energy) {
        super(x, y, color, mass, speed, base_energy);
    }
    @Override
    public StrategyList getAvailableStrategies(){
        StrategyList result=new StrategyList();
        this.addAvailableStrategies(result);
        return result;
    }
    @Override
    public void addAvailableStrategies(StrategyList avail) {
        avail.add(StrategyList.STAND_STILL);
        avail.add(StrategyList.MOVE_RANDOM);
        avail.add(StrategyList.GRAB_FOOD);
    }
}
