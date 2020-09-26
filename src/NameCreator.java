/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

/**
 *
 * @author Serhan-PC
 */
public abstract class NameCreator {
    public static String[] names;
    public static int namesc;
    private static boolean file_read;
    private static int name_no=1;
    public static void initialize(){
        names=new String[500];
        namesc=0;
        BufferedReader reader = null;
        try {
            InputStream in = AgarRunner.class.getResourceAsStream("/extra/names.txt"); 
            if(in!=null){
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                System.out.println("Name list is Valid.");
                while ((line = reader.readLine()) != null) {
                   names[namesc++]=line;
                }
                file_read=true;
            }else{
                file_read=false;
            }
        } catch (IOException e) {
            System.out.println("Name list is Invalid.");
            file_read=false;
        }
    }
    private static String getRandomPrefix(){
        Random ran=new Random();
        int n=ran.nextInt(namesc);
        return names[n];
    }
    public static String getRandomName(){
        if(file_read){
            return getRandomPrefix().concat(getRandomPrefix());
        }else{
            return "NoName"+String.valueOf(name_no++);
        }
    }
}
