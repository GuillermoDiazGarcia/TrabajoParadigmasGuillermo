/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Guillermo
 */
public class ThreadStarter extends Thread{
//    private final Gasolinera gasolinera;
    private final MainFrame.Gasolinera gasolinera;
    private final Random rand = new Random();
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
//    public ThreadStarter (Gasolinera gasolinera){
    public ThreadStarter (MainFrame.Gasolinera gasolinera){
        this.gasolinera = gasolinera;
    }
    
    @Override
    public void run(){
        for(int i=0;i<3;i++){
            Operario operario = new Operario(i, gasolinera, 5, rand);
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Creado " + operario);
            operario.start();
        }
        
        for(int i=0;i<2000;i++){
            //while(MainFrame.checkStopFlag());
            Vehiculo vehiculo = new Vehiculo(i,gasolinera);
            //Log vehicle creation here
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Creado " + vehiculo);
            vehiculo.start();
            try{
                Thread.sleep(500+(long)rand.nextInt(5500));
                //Thread.sleep(500+(long)rand.nextInt(500));
            }
            catch (InterruptedException ex) {
                System.out.println("Error in sleep after creating " + vehiculo);
                //Log error here
            }
            //while(MainFrame.checkStopFlag());
        }
    }
}
