package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Guillermo Díaz García
 */
public class Operario extends Thread{
    private final int num;
    private final String nombre;
//    private final Gasolinera gasolinera;
    private final MainFrame.Gasolinera gasolinera;
    private final int vehiculosPorDescanso;
    private final Random rand;
    private final int tiempoDescanso = 5000;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
//    public Operario (int num, Gasolinera gasolinera, int vehiculosPorDescanso, Random rand){
    public Operario (int num, MainFrame.Gasolinera gasolinera, int vehiculosPorDescanso, Random rand){
        this.num = num;
        this.nombre = "Operario"+num;
        this.gasolinera = gasolinera;
        this.vehiculosPorDescanso = vehiculosPorDescanso;
        this.rand = rand;
    }
    
    @Override
    public void run(){
        int contador = 0;
        while(true){
            if(contador<5){
                int surtidor = gasolinera.operarSurtidor(num);
                if(surtidor != -1){
                    try{
                        long tiempoOperar = 4000 + (long)rand.nextInt(4000);
                        Date now = new Date();
                        System.out.println(formatoFecha.format(now) + " - " + nombre + " operando surtidor " + (surtidor+1) + " durante " + (tiempoOperar/1000) + " segundos");
                        //Log here
                        sleep(tiempoOperar);
                    } catch(InterruptedException ex){
                        Date now = new Date();
                        System.out.println(formatoFecha.format(now) + " - Error with " + nombre + " sleeping");
                    }
                    gasolinera.surtidorTerminado(surtidor);
                    contador++;
                }
            }
            else{
                try{
                    while(MainFrame.checkStopFlag());
                    
                    System.out.println(nombre + " descansando " + (tiempoDescanso/1000) + " segundos");
                    //Log here
                    sleep(tiempoDescanso);
                    
                    while(MainFrame.checkStopFlag());
                } catch(InterruptedException ex){
                    Date now = new Date();
                    System.out.println(formatoFecha.format(now) + " - Error with " + nombre + " sleeping");
                    //Log error here
                }
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

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
