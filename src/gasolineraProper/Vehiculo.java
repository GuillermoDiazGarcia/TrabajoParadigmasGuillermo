package gasolineraProper;

/**
 * Clase hilo que representa los vehículos de la simulación
 * @author Guillermo Díaz García
 */
public class Vehiculo  extends Thread{
    private final String nombre;
    private final MainFrame.Gasolinera gasolinera;
    
    /***
     * Constructor simple que pide la gasolinera que utilizan todos los hilos y un número para crear su nombre
     * @param num
     * @param gasolinera 
     */
    public Vehiculo (int num, MainFrame.Gasolinera gasolinera){
        this.nombre = "Vehiculo"+num;
        this.gasolinera = gasolinera;
    }
    
    /***
     * Método run bastante simple en el que entramos a la gasolinera y luego avisamos de que hemos salido
     */
    @Override
    public void run(){
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
