/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Serhan-PC
 */
public class AgarRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NameCreator.initialize();
        final GUI x=new GUI();
        x.createAndShowGUI();
        SwingUtilities.invokeLater(() -> {
            x.setVisible();
        });
        boolean f=true;
        int gc_counter=0;
        while(f){
            try {
                Thread.sleep(20);
                gc_counter++;
                if(gc_counter>=250){
                    gc_counter=0;
                    System.gc();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AgarRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
            x.a.turn(x.a.gameSpeed);
        }
    }
    
}
