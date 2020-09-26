/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author serhan
 */
public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color backGroundColor;
    private Color textColor;
    private String text;
    private String text2;
    boolean enabled;
    boolean displayed;
    boolean selected;
    boolean value=true;
    boolean istogglebutton;
    public Button(int x,int y,int width,int height,Color backGroundColor,String text){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.backGroundColor=backGroundColor;
        this.textColor=Color.white;
        this.text=text;
        this.enabled=true;
        this.displayed=true;
        this.selected=false;
        this.istogglebutton=false;
    }
    public Button(int x,int y,int width,int height,Color backGroundColor,String text,boolean displayed){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.backGroundColor=backGroundColor;
        this.textColor=Color.white;
        this.text=text;
        this.enabled=true;
        this.displayed=displayed;
        this.selected=false;
        this.istogglebutton=false;
    }
       public Button(int x,int y,int width,int height,Color backGroundColor,String text,String text2,boolean displayed){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.backGroundColor=backGroundColor;
        this.textColor=Color.white;
        this.text=text;
        this.text2=text2;
        this.enabled=true;
        this.displayed=displayed;
        this.selected=false;
        this.istogglebutton=true;
    }
    
    public void draw(Graphics g,int scrx,int scry){
        if(displayed){
        int x=this.x*scrx/1280;int y=this.y*scry/720;
        int width=this.width*scrx/1280;
        int height=this.height*scry/720;
        if(enabled){
        if(!selected&&!istogglebutton||(istogglebutton&&!value))g.setColor(backGroundColor);else g.setColor(backGroundColor.brighter());
        }else g.setColor(Color.darkGray);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        if(!selected)g.drawRect(x+2, y+2, width-4, height-4);else g.drawRect(x+6, y+6, width-12, height-12);
        g.setColor(textColor);
        
        
        Font myfont=new Font("Arial",0,16*scrx/1280);
        g.setFont(myfont);
        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext frc = g2.getFontRenderContext();

        String text2write;
        
        if(!istogglebutton||value)text2write=text;else text2write=text2;
        
        GlyphVector gv = myfont.createGlyphVector(frc, text2write);
        Rectangle2D box = gv.getVisualBounds();
        
        g.drawString(text2write, (int) (x+width/2-(0.5*box.getWidth())), y+height/2+8);
            
        }
    }
    public boolean isClicked(int mx,int my){
        return enabled&&displayed&&mx>=x&&mx<x+width&&my>=y&&my<y+height;
    }
    public void toggle(int mx,int my){
      if(isClicked(mx,my))value=!value;   
    }
    
    
}
