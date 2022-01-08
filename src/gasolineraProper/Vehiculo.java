package gasolineraProper;

import java.util.Random;

/**
 * Clase hilo que representa los vehículos de la simulación
 * @author Guillermo Díaz García
 */
public class Vehiculo  extends Thread{
    private final String nombre;
    private final MainFrame.Gasolinera gasolinera;
    private final Random rand;
    
    /***
     * Constructor simple que pide la gasolinera que utilizan todos los hilos y un número para crear su nombre
     * @param num
     * @param gasolinera 
     */
    public Vehiculo (int num, MainFrame.Gasolinera gasolinera, Random rand){
        this.nombre = "Vehiculo"+num;
        this.gasolinera = gasolinera;
        this.rand = rand;
    }
    
    /***
     * Método run bastante simple en el que entramos a la gasolinera y luego avisamos de que hemos salido
     */
    @Override
    public void run(){
        //Esperamos el tiempo aleatorio especificado antes de entrar
        try{
            Thread.sleep(500+(long)rand.nextInt(5500));
            //Thread.sleep(500+(long)rand.nextInt(500));
        }
        catch (InterruptedException ex) {
            MainFrame.log(" - Error in sleep after creating " + nombre);
        }
        //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
        gasolinera.checkStopFlag();
        
        MainFrame.log(" - " + nombre + " entrando en gasolinera");
        
        gasolinera.entrarGasolinera(nombre);
        
        MainFrame.log(" - " + nombre + " saliendo de gasolinera");
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
