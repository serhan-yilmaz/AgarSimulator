/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Serhan-PC
 */
public final class Environment {
    private final int FOOD_LIMIT=300;
    private final int CELL_LIMIT=50;
    public int windowWidth;
    public int windowHeight;
    public int mapWidth;
    public int mapHeight;
    public Vector scale=new Vector(1,1);
    protected Food[] food;
    protected int foodc;
    protected Cell[] cell;
    protected Cell[] ordered_cell;
    protected int cellc;
    protected int state;
    private Vector initial_scale;
    public double scaling;
    private double scaling2;
    private double total_mass;
    private double total_mass2;
    public int divx;
    public int divy;
    public int level;
    public static final int MAX_DECORATION_LEVEL=3;
    public boolean grid=true;
    public Environment(int windowWidth,int windowHeight){
        this.food = new Food[FOOD_LIMIT];
        this.cell = new Cell[CELL_LIMIT];
        this.windowWidth=windowWidth;
        this.windowHeight=windowHeight;
        reset();
    }
    public void reset(){
        foodc=0;
        cellc=0;
        scale.x=1;
        scale.y=1;
        total_mass2=49;
        total_mass=0;
        scaling=1;
        scaling2=1;
        divx=80;
        divy=80;
        level=1;
        state=0;
        mapWidth=(int) (windowWidth/scale.x);
        mapHeight=(int) (windowHeight/scale.y);
        for(int i=0;i<250;i++)addFood();
        for(int i=0;i<10;i++)addCell();
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.setColor(Color.gray);
        Graphics2D g2=(Graphics2D) g;
        
        AffineTransform nt=new AffineTransform();
        nt.concatenate(g2.getTransform());
        nt.scale(scale.x, scale.y);
        nt.translate((mapWidth)*0.5, (mapHeight)*0.5);
        if(grid){
            AffineTransform at=g2.getTransform();
            g2.setTransform(nt);
                for(int i=-1*mapWidth/(2*divx);i<0.5*mapWidth/divx;i++){
                    g.drawLine((int)(i*divx), -1*mapHeight/2, (int)(divx*i), mapHeight/2);
                  //  g.drawLine((int)(i*divx)+1, -1*mapHeight/2, (int)(divx*i)+1, mapHeight/2);
                }
                for(int i=-1*mapHeight/(2*divy);i<0.5*mapHeight/divy;i++){
                    g.drawLine(-1*mapWidth/2, (int)(i*divy), mapWidth/2, (int)(i*divy));
                //    g.drawLine(-1*mapWidth/2, (int)(i*divy)+1, mapWidth/2, (int)(i*divy)+1);
                }
            g2.setTransform(at);
        }
        for(int i=0;i<foodc;i++)food[i].draw(g,nt);
        for(int i=0;i<cellc;i++)cell[i].draw(g,nt);
        for(int i=0;i<cellc;i++)cell[i].drawText(g,this);
    }
    public void addFood(Food fo){
        if(foodc<FOOD_LIMIT){
            food[foodc++]=fo;
            if(state==3){
                total_mass2+=fo.getMass();
            }else{
                total_mass+=fo.getMass();
            }
        }
    }
    public void addFood(){
        Vector pos;
        if(state==3){
            pos=Vector.getBiggerRandomVector(mapWidth, mapHeight,scaling2);
        }else{
            pos=Vector.getRandomVector(mapWidth, mapHeight);
        }
        addFood(FoodFactory.createRandomFood(pos,scaling));
    }
    public void addCell(Cell ce){
        if(cellc<CELL_LIMIT){
            cell[cellc++]=ce;
            if(state==3){
                total_mass2+=ce.getMass();
            }else{
                total_mass+=ce.getMass();
            }
        }
    }
    public void addCell(int food_index){
        if(cellc<CELL_LIMIT){
            cell[cellc++]=CellFactory.createCell(food[food_index]);
            deleteFood(food_index);
        }
    }
    public void addCell(){
        Vector pos;
        if(state==3){
            pos=Vector.getBiggerRandomVector(mapWidth, mapHeight,scaling2);
        }else{
            pos=Vector.getRandomVector(mapWidth, mapHeight);
        }
        addCell(CellFactory.createCell(pos,scaling));
    }
    public void deleteFood(int index){
        if(index<foodc){
            food[index]=food[--foodc];
            //food[foodc]=null;
        }
    }
    public void deleteCell(int index){
        if(index<cellc){
            cell[index]=cell[--cellc];
            //cell[cellc]=null;
        }
    }
    public void orderCells(){
        for(int i=0;i<cellc;i++){
            double max_mass = -1;
            int index=-1;
            for(int j=i;j<cellc;j++){
                if(index==-1||max_mass<cell[j].getMass()){
                    max_mass=cell[j].getMass();
                    index=j;
                }
            }
            Cell temp=cell[i];
            cell[i]=cell[index];
            cell[index]=temp;
        }
    }
    public void stepAll(){
        if(state==0){
            for(int i=0;i<foodc;i++){
                food[i].step(this,food[i]);
                if(food[i].getFoodType()==FoodList.ORGANISM){
                    for(int j=0;j<foodc;j++){
                        if(food[i]!=food[j]){
                            if(food[i].isInContact(food[j])){
                                boolean b1=food[j].getFoodType()==FoodList.SUGAR;
                                boolean b2=food[j].getFoodType()==FoodList.ORGANISM&&food[i].getMass()>=food[j].getMass()*1.5;
                                if(b1||b2){
                                        food[i].data.energy+=food[j].data.energy;
                                        food[i].setMass(food[i].getMass()+food[j].getMass());
                                        deleteFood(j);
                                        j--;
                                }
                            }
                        }
                    }
                }
                if(food[i].getFoodType()==FoodList.ORGANISM&&food[i].getMass()>=30*scaling){
                    addCell(i);
                    i--;
                }
            }
            for(int i=0;i<cellc;i++){
                cell[i].step(this,cell[i]);
                for(int j=0;j<foodc;j++){
                    if(cell[i].isInContact(food[j])){
                        cell[i].data.energy+=food[j].data.energy;
                        cell[i].setMass(cell[i].getMass()+food[j].getMass());
                        cell[i].data.food_eaten++;
                        deleteFood(j);
                        j--;
                    }
                }
                for(int j=0;j<cellc;j++){
                    if(cell[j]!=null&&cell[i]!=null){
                        if(cell[i]!=cell[j]){
                            if(cell[i].isInContact(cell[j])){
                                if(cell[i].getMass()>=cell[j].getMass()){
                                    cell[i].data.energy+=cell[j].data.energy;
                                    cell[i].setMass(cell[i].getMass()+cell[j].getMass());
                                    cell[i].data.cell_eaten++;
                                    deleteCell(j);
                                    j--;
                                }else{
                                    cell[j].data.energy+=cell[i].data.energy;
                                    cell[j].setMass(cell[i].getMass()+cell[j].getMass());
                                    cell[j].data.cell_eaten++;
                                    deleteCell(i);
                                    j=cellc;
                                    i--;
                                }
                            }
                        }
                    }else{
                        System.out.println(cell[i]==null);
                        System.out.println(cell[j]==null);
                        System.out.println("i : "+i+" j : "+j);
                        System.out.println("cellc : "+cellc);
                        if(cell[i]==null)throw new NullPointerException("cell["+i+"] is NULL");
                        if(cell[j]==null)throw new NullPointerException("cell["+j+"] is NULL");
                    }
                }
                if(i<cellc){
                    if(cell[i].getLevel()<MAX_DECORATION_LEVEL&&cell[i].getMass()>=cell[i].getThreshold()){
                        cell[i]=CellDecoratorFactory.createDecorator(cell[i]);
                    }      
                }
            }
            orderCells();
            if(cellc==1){
                if(level<3){
                    state=1;
                }else {
                    state=4;
                }
            }
        }else 
        if(state==2){
            level++;
            initial_scale=new Vector(scale);
            state=3;
            scaling=total_mass/(43);
            scaling2=Math.sqrt(total_mass/(total_mass2));
            cell[0].data.base_speed*=scaling2;
            cell[0].data.base_energy*=scaling2*scaling2;
            cell[0].data.energy*=scaling2*scaling2;
            cell[0].data.max_energy*=scaling2*scaling2;
            total_mass2=0;
            for(int i=0;i<250;i++)addFood();
            for(int i=0;i<10;i++)addCell();
            for(int i=0;i<cellc;i++){
                boolean f=true;
                while(f){
                    if(cell[i].getLevel()<MAX_DECORATION_LEVEL&&cell[i].getMass()>=cell[i].getThreshold()){
                        cell[i]=CellDecoratorFactory.createDecorator(cell[i]);
                    }else f=false;
                }
            }
            double temp=total_mass;
            total_mass=total_mass2;
            total_mass2=temp;
        }else
        if(state==3){
            scale.x*=0.995;
            scale.y*=0.995;
            int t=0;
            if(scale.x<initial_scale.x/scaling2){t++;scale.x=initial_scale.x/scaling2;}
            if(scale.y<initial_scale.y/scaling2){t++;scale.y=initial_scale.y/scaling2;}
            if(divx*scale.x<=10){
                divx*=10;divy*=10;
            }
            mapWidth=(int) (windowWidth/scale.x);
            mapHeight=(int) (windowHeight/scale.y);
            if(t==2)state=0;
        }
    }
}
