/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajoparadigmasguillermo;

import java.util.Random;

/**
 * Clase hilo que no se pedía pero que utilizo para ir creando los demás hilos. La razón por la que
 * es un hilo aparte en lugar de estar en el Main es que el bucle de 2000 vueltas monopolizaría la
 * ejecución de dicho Main y por tanto nada funcionaría como debe. Al separarlo en otro hilo
 * me aseguro de que no monopoliza nada.
 * @author Guillermo Díaz García
 */
public class ThreadStarter extends Thread{
    private final MainFrame.Gasolinera gasolinera;
    private final Random rand = new Random();
    
    /**
     * Constructor simple que recibe la gasolinera que van a utilizar todos los hilos creados
     * @param gasolinera 
     */
    public ThreadStarter (MainFrame.Gasolinera gasolinera){
        this.gasolinera = gasolinera;
    }
    
    /***
     * Este método run es el que primero crea los 3 operarios y luego va creando los
     * 2000 vehículos lentamente, esperando una cantidad de tiempo aleatoria entre
     * 0,5 segundos y 6 segundos
     */
    @Override
    public void run(){
        //Creamos los 3 operarios
        for(int i=0;i<3;i++){
            Operario operario = new Operario(i, gasolinera, 5, rand);
            MainFrame.log(" - Creado " + operario);
            operario.start();
        }
        
        //Creamos los 2000 vehículos
        for(int i=0;i<2000;i++){
            //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
            gasolinera.checkStopFlag();
            Vehiculo vehiculo = new Vehiculo(i,gasolinera);
            MainFrame.log(" - Creado " + vehiculo);
            vehiculo.start();
            try{
                Thread.sleep(500+(long)rand.nextInt(5500));
                //Thread.sleep(500+(long)rand.nextInt(500));
            }
            catch (InterruptedException ex) {
                MainFrame.log(" - Error in sleep after creating " + vehiculo);
            }
            //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
            gasolinera.checkStopFlag();
        }
    }
}
