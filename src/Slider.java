/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author serhan
 */
public class Slider {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color backGroundColor;
    private Color sliderColor;
    int position;
    int maxposition;
    boolean enabled;
    boolean displayed;
    boolean selected;
    public Slider(int x,int y,int width,int height,Color backGroundColor,boolean displayed,int position){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.backGroundColor=backGroundColor;
        this.sliderColor=Color.black;
        this.position=position;
        this.maxposition=100;
        this.displayed=displayed;
        this.enabled=true;
        this.selected=false;
    }
    public void draw(Graphics g,int scrx,int scry){
        if(displayed){
            int xw=1600;
            int yh=900;
            int x=this.x*scrx/1280;int y=this.y*scry/720;
            int width=this.width*scrx/1280;
            int height=this.height*scry/720;
            g.setColor(backGroundColor);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
            g.drawRect(x+2, y+2, width-4, height-4);
            g.setColor(sliderColor);
            g.drawLine(x+8*scrx/1280, y+height/2, x+width-8*scrx/1280, y+height/2);
            int[] polyx=new int[3],polyy=new int[3];
            polyx[0]=x+8*scrx/1280+position*(width-16*scrx/1280)/maxposition;polyy[0]=y+height/2;
            polyx[1]=(int) (polyx[0]+15*Math.cos(Math.PI*0.333)*scrx/1280);polyy[1]=(int) (polyy[0]-15*Math.sin(Math.PI*0.333)*scry/720);
            polyx[2]=(int) (polyx[0]-15*Math.cos(Math.PI*0.333)*scrx/1280);polyy[2]=(int) (polyy[0]-15*Math.sin(Math.PI*0.333)*scry/720);
            g.setFont(new Font("Arial",0,15*scrx/1280));
            int st=1;
            for(int i=0;i<4;i++){
                g.drawLine(x+i*(width-16*scrx/1280)/3+8*scrx/1280,y+12*scry/720,x+i*(width-16*scrx/1280)/3+8*scrx/1280,y+height-12*scry/720);
                g.drawString(String.valueOf(st), x+i*(width-16*scrx/1280)/3+8*scrx/1280-i*18+(int)(i*i*2.4), y+height-10*scry/720);
                st*=10;
            }
            for(int i=0;i<31;i++){
                g.drawLine(x+i*(width-16*scrx/1280)/30+8*scrx/1280,y+20*scry/720,x+i*(width-16*scrx/1280)/30+8*scrx/1280,y+height-20*scry/720);
            }
            g.setColor(Color.gray);
            g.fillPolygon(polyx, polyy, 3);
            g.setColor(Color.black);
            g.drawPolygon(polyx, polyy, 3);
        }
    }
    public void setSliderPosition(int mx,int my,int scrx,int scry){
        if(enabled&&displayed){
            if(mx>=x&&mx<=x+width&&my>=y&&my<=y+height){
                selected=true;
                double portion=(0.0+mx-x-8)/(width-16);
                position=(int) (portion*maxposition);if(position<0)position=0;if(position>maxposition)position=maxposition;
            }
        }
    }
}
