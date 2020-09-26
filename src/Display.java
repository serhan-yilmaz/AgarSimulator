/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Serhan-PC
 */
public class Display extends JPanel{
    public final int ENVIRONMENT_WIDTH=1000;
    public final int LOGO_HEIGHT=360;
    public final int windowWidth=1600;
    public final int windowHeight=900;
    public Environment environment=new Environment(ENVIRONMENT_WIDTH,windowHeight);
    public int currentWindowWidth=1600;
    public int currentWindowHeight=windowHeight;
    BufferedImage env_img;
    Graphics2D g_env;
    public int step_number=0;
    public Slider gameSpeedSlider;
    public Button[] buttons;
    public int gameSpeed=1;
    public BufferedImage logo;
    public Display(){
        this.gameSpeedSlider = new Slider(1390,100,190,50,new Color(150,75,0),true,0);
         this.buttons = new Button[4];
         buttons[0]=new Button(1490,160,90,42,new Color(150,75,0),"Continue");    
         buttons[0].enabled=false;
         buttons[1]=new Button(1390,160,90,42,new Color(150,75,0),"Reset");   
         buttons[2]=new Button(1390,13,190,42,new Color(150,75,0),"Window Reset");
         buttons[3]=new Button(1490,210,90,42,new Color(150,75,0),"On","Off",true);
         GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
         env_img = gd.getDefaultConfiguration().createCompatibleImage(ENVIRONMENT_WIDTH, windowHeight, Transparency.OPAQUE);
         g_env = env_img.createGraphics();
        try {
            logo=ImageIO.read(AgarRunner.class.getResourceAsStream("/extra/Logo.bmp"));
        } catch (IOException ex) {
            logo=null;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        AffineTransform nt=new AffineTransform();
        nt.concatenate(g2.getTransform());
        nt.scale(currentWindowWidth/(windowWidth+0.0), currentWindowHeight/(windowHeight+0.0));
        AffineTransform at=g2.getTransform();
        g2.setTransform(nt);
        environment.draw(g_env);
        g.setColor(Color.gray);
        Environment e=this.environment;
        g.drawImage(env_img,360,0, ENVIRONMENT_WIDTH, windowHeight, this);
        if(e.state==1||e.state==4){
            g.setColor(Color.black);
            Font myfont=new Font("Arial",0,42);
            g.setFont(myfont);
            FontRenderContext frc = g2.getFontRenderContext();
            String text2write=e.cell[0].name+" WON LEVEL "+String.valueOf(e.level)+" !";
            GlyphVector gv = myfont.createGlyphVector(frc, text2write);
            Rectangle2D box = gv.getVisualBounds();
            g.drawString(text2write, (int) (360+ENVIRONMENT_WIDTH/2-box.getWidth()/2), windowHeight/2);
        }
        g.setColor(Color.white);
        g.fillRect(0, 0, 360, windowHeight-LOGO_HEIGHT);
        g.setColor(Color.white);
        g.fillRect(360+ENVIRONMENT_WIDTH, 0, windowHeight, windowHeight);
        g.setColor(Color.white);
        g.fillRect(0, windowHeight-LOGO_HEIGHT, 360, windowHeight);
        g.setColor(Color.black);
        g.drawLine(360, 0, 360, windowHeight);
        g.drawLine(360+ENVIRONMENT_WIDTH, 0, 360+ENVIRONMENT_WIDTH, windowHeight);
        g.drawLine(0, windowHeight-LOGO_HEIGHT, 360, windowHeight-LOGO_HEIGHT);
        g.drawLine(360+ENVIRONMENT_WIDTH, 0, 360+ENVIRONMENT_WIDTH, windowHeight);
        g.setFont(new Font("Arial",0,16));
        g.setColor(Color.black);
        g.drawString("Step : "+String.format("%1$,.0f", (double) step_number),10, 25);
        g.drawString("Cell Count : "+String.valueOf(environment.cellc), 235, 25);
        g.drawString("Division : "+String.valueOf((int)environment.divx)+" br", 235, 48);
        g.drawString("Level : "+String.format("%1$,.0f", (double) environment.level),150, 70);
        g.drawString("    Name          Mass        Spd     Engy%  CE   FE", 20, 100);
        g.setFont(new Font("Arial",0,12));
        for(int i=0;i<environment.cellc;i++){
            g.drawString(environment.cell[i].name, 10, 130+i*20);
            g.drawString(String.format("%1$,.1f",environment.cell[i].getMass()), 125, 130+i*20);
            g.drawString(String.format("%1$,.2f",environment.cell[i].data.speed/Math.sqrt(environment.scaling)), 190, 130+i*20);
           // g.drawString(String.format("%1$,.0f",environment.cell[i].energy/1000), 260, 130+i*20);
            g.drawString(String.format("%1$,.1f",100*environment.cell[i].data.energy/environment.cell[i].data.max_energy), 245, 130+i*20);
            g.drawString(String.valueOf(environment.cell[i].data.cell_eaten), 295, 130+i*20);
            g.drawString(String.valueOf(environment.cell[i].data.food_eaten), 330, 130+i*20);
        }
        g.setFont(new Font("Arial",0,20));
        g.drawString("Game Speed : ", 1390, 85);
        g.drawString("Grid : ", 1430, 240);
        g.drawString("Strategies", 1435, 320);
        for(int i=0;i<6;i++){
            g.drawString(String.valueOf(i)+" : ", 1390, 350+25*i);
        }
        g.drawString("Stand Still", 1450, 350);
        g.drawString("Move Random", 1450, 375);
        g.drawString("Grab Food", 1450, 400);
        g.drawString("Chase Smaller", 1450, 425);
        g.drawString("Avoid Larger", 1450, 450);
        g.drawString("Move Linear", 1450, 475);
        gameSpeedSlider.draw(g, 1280, 720);
        for(int i=0;i<buttons.length;i++)buttons[i].draw(g, 1280, 720);
        if(logo!=null){
            g.drawImage(logo,20, (int) (windowHeight-LOGO_HEIGHT*0.95), 320, (int) (LOGO_HEIGHT*0.9-20), this);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial",0,16));
        g.drawString("Agar Simulator by Serhan Yılmaz",5,windowHeight-15);
        g2.setTransform(at);
    }
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(windowWidth,windowHeight);
    }
    public void turn(int n){
        for(int s=0;s<n;s++){
            environment.stepAll();
            if(environment.state==0)step_number++;
            if(environment.state==1)buttons[0].enabled=true;
        }
        repaint();
    }
}
