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
public abstract class FoodFactory {

    public static Food createFood(int food_type,Vector pos,double scaling){
        if(food_type==FoodList.SUGAR){
            return SugarFactory.createSugar(pos,scaling);
        }
        if(food_type==FoodList.ORGANISM){
            return OrganismFactory.createOrganism(pos,scaling);
        }
        return null;
    }
    public static Food createRandomFood(Vector pos,double scaling){
        Random rand=new Random();
        Food food=createFood(rand.nextInt(2),pos,scaling);
        return food;
    }
//    public static void placeRandomFood(Environment e){
 //       e.addFood(createRandomFood(e.mapWidth,e.mapHeight));
 //   }
}
