package gasolineraProper;

import java.util.Random;

/**
 * Clase hilo que representa los operarios de la simulación
 * @author Guillermo Díaz García
 */
public class Operario extends Thread{
    private final int num;
    private final String nombre;
    private final MainFrame.Gasolinera gasolinera;
    private final int vehiculosPorDescanso;
    private final Random rand;
    private final int tiempoDescanso = 5000;
    
    /***
     * Constructor que pide un número para crear su nombre, la gasolinera común a todos los hilos,
     * el número de vehículos que operará antes de descansar y un objeto Random que utiliza para
     * generar los tiempos de repostaje. El objeto Random es común a todos para ahorrar en memoria
     * @param num
     * @param gasolinera
     * @param vehiculosPorDescanso
     * @param rand 
     */
    public Operario (int num, MainFrame.Gasolinera gasolinera, int vehiculosPorDescanso, Random rand){
        this.num = num;
        this.nombre = "Operario"+num;
        this.gasolinera = gasolinera;
        this.vehiculosPorDescanso = vehiculosPorDescanso;
        this.rand = rand;
    }
    
    /***
     * Este método run es un bucle infinito que va alternando entre servir a los vehículos que deba
     * y descansar 5 segundos.
     */
    @Override
    public void run(){
        int contador = 0;
        while(true){
            if(contador<vehiculosPorDescanso){
                //Empezamos a operar el surtidor que debamos y obtenemos qué número de surtidor es. En caso de que haya habido algún error
                //el surtidor obtenido será el -1, el cual ignoramos y volvemos al inicio del bucle
                int surtidor = gasolinera.operarSurtidor(num);
                if(surtidor != -1){
                    try{
                        //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
                        gasolinera.checkStopFlag();
                        //Generamos el tiempo aleatorio de espera entre 4 y 8 segundos
                        long tiempoOperar = 4000 + (long)rand.nextInt(4000);
                        
                        MainFrame.log(" - " + nombre + " operando surtidor " + (surtidor+1) + " durante " + (tiempoOperar/1000) + " segundos");
                        
                        sleep(tiempoOperar);
                        //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
                        gasolinera.checkStopFlag();
                    } catch(InterruptedException ex){
                        MainFrame.log(" - Error with " + nombre + " sleeping");
                    }
                    //Una vez que hemos terminado de esperar salimos del surtidor
                    gasolinera.surtidorTerminado(surtidor);
                    contador++;
                }
            }
            else{
                //En caso de que ya nos toque descansar esperamos 5 segundos sin operar ningún surtidor y volvemos al inicio del bucle con el contador a 0
                try{
                    //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
                    gasolinera.checkStopFlag();
                    MainFrame.log(" - " + nombre + " descansando " + (tiempoDescanso/1000) + " segundos");
                    
                    sleep(tiempoDescanso);
                } catch(InterruptedException ex){
                    MainFrame.log(" - Error with " + nombre + " sleeping");
                }
                //Comprobador para el botón de pausa. Están repartidos de forma bastante liberal para asegurarse de que se pausan todos los hilos correctamente
                gasolinera.checkStopFlag();
                contador = 0;
            }
        }
    }

    public int getVehiculosPorDescanso() {
        return vehiculosPorDescanso;
    }

    public int getNum() {
        return num;
    }

    public String getNombre() {
        return nombre;
    }

    public Random getRand() {
        return rand;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
