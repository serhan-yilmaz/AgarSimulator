/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Serhan-PC
 */
public class GUI {
  protected JFrame f;
  protected final Display a=new Display();
    
  protected void createAndShowGUI() {
     //   System.out.println("Created GUI on EDT? "+
      //  SwingUtilities.isEventDispatchThread());
        f = new JFrame("Agar Simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800,800);
        f.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                a.currentWindowWidth=f.getContentPane().getWidth();
                a.currentWindowHeight=f.getContentPane().getHeight();
               // System.out.println("a : "+a.currentWindowWidth+" b : "+a.currentWindowHeight);
            }

        });
        f.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                     int bx=e.getX()-f.getInsets().left;int by=e.getY()-f.getInsets().top;
                     bx=bx*a.windowWidth/a.currentWindowWidth;by=by*a.windowHeight/a.currentWindowHeight;
                     a.gameSpeedSlider.setSliderPosition(bx, by, 1280, 720);
                     a.gameSpeed=(int) Math.pow(10, 3*a.gameSpeedSlider.position/100.0);
                     for(int i=0;i<a.buttons.length;i++){
                        if(a.buttons[i].isClicked(bx,by)){
                            a.buttons[i].selected=true;
                           }
                     }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    int bx=e.getX()-f.getInsets().left;int by=e.getY()-f.getInsets().top;
                    bx=bx*a.windowWidth/a.currentWindowWidth;by=by*a.windowHeight/a.currentWindowHeight;
                    a.gameSpeedSlider.selected=false;
                    for(int i=0;i<a.buttons.length;i++){
                        if(a.buttons[i].istogglebutton){
                            a.buttons[i].toggle(bx, by);
                            if(a.buttons[i].isClicked(bx, by)){
                                switch(i){
                                    case 3:a.environment.grid=a.buttons[i].value;break;
                                    
                                }
                            }
                        }
                        if(a.buttons[i].isClicked(bx,by)&&a.buttons[i].selected){
                            switch(i){
                                case 0:a.environment.state=2;a.buttons[0].enabled=false;break;
                                case 1:a.environment.reset();a.gameSpeed=1;a.gameSpeedSlider.position=0;a.step_number=0;break;
                                case 2:f.pack();f.setLocationRelativeTo(null);break;
                            }
                        }
                        a.buttons[i].selected=false;
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
         f.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int bx=e.getX()-f.getInsets().left;int by=e.getY()-f.getInsets().top;
                bx=bx*a.windowWidth/a.currentWindowWidth;by=by*a.windowHeight/a.currentWindowHeight;
                if(a.gameSpeedSlider.selected){
                    a.gameSpeedSlider.setSliderPosition(bx, by, 1280, 720);
                    a.gameSpeed=(int) Math.pow(10, 3*a.gameSpeedSlider.position/100.0);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
         });
        f.setResizable(true);
        f.setUndecorated(false);
        f.add(a);
        f.pack();
        f.setLocationRelativeTo(null);
    }
  protected void setVisible(){
      f.setVisible(true);
  }
    
    
    
    
    
    
}
