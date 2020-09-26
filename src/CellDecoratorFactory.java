/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Serhan-PC
 */
public abstract class CellDecoratorFactory {
    private static final int EVADER=1;
    private static final int HUNTER=2;
    private static final int ROAMER=3;
    
    private static CellDecorator createDecorator(int type,Cell cd){
        if(type==EVADER){
            return new Evader(cd);
        }
        if(type==HUNTER){
            return new Hunter(cd);
        }
        if(type==ROAMER){
            return new Roamer(cd);
        }
        throw new UnsupportedOperationException("Decorator Unknown");
  //      return null;
    }
    public static CellDecorator createDecorator(Cell c){
        int level=c.getLevel();
        CellDecorator cd=null;
        switch(level){
            case 0:cd=createDecorator(EVADER,c);break;
            case 2:cd=createDecorator(HUNTER,c);break;
            case 1:cd=createDecorator(ROAMER,c);break;
            default:throw new UnsupportedOperationException("Not supported yet.");
        }
        return cd;
    }
}
