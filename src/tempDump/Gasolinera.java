package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 *
 * @author Guillermo Díaz García
 */
public class Gasolinera {
    private final Queue<String> colaEntrada = new LinkedList<>();
    private final Queue<Integer> esperandoOperario = new LinkedList<>();
    private final Surtidor[] surtidores = new Surtidor[8];
    private final Semaphore semEntrada = new Semaphore(8,true);
    private final Semaphore semSurtVehiculos0 = new Semaphore(1);
    private final Semaphore semSurtVehiculos1 = new Semaphore(1);
    private final Semaphore semSurtVehiculos2 = new Semaphore(1);
    private final Semaphore semSurtVehiculos3 = new Semaphore(1);
    private final Semaphore semSurtVehiculos4 = new Semaphore(1);
    private final Semaphore semSurtVehiculos5 = new Semaphore(1);
    private final Semaphore semSurtVehiculos6 = new Semaphore(1);
    private final Semaphore semSurtVehiculos7 = new Semaphore(1);
    private final Semaphore semOperarios = new Semaphore(1);
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final MainFrame context;

    public Gasolinera (MainFrame context){
        //this.campoCola = campoCola;
        for(int i=0;i<8;i++){
            Surtidor surtidor = new Surtidor(i);
            surtidores[i] = surtidor;
        }
        this.context = context;
    }

    public void entrarGasolinera(String vehiculo){
        int surt = -1;
        try{
            colaEntrada.add(vehiculo);
            actualizarCola();

            semEntrada.acquire();
            surt = surtidorLibre();
        } catch(Exception ex){
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Error awaiting " + vehiculo + " while waiting at entrance");
            //Log here
        } try{
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - " + vehiculo + " entrando a surtidor " + surt);
            //Log here

            colaEntrada.remove(vehiculo);
            actualizarCola();
            surtidores[surt].setVehiculo(vehiculo);
            surtidores[surt].setLibre(false);
            esperandoOperario.add(surt);
            switch(surt){
                case 0:
                    semSurtVehiculos0.acquire();
                case 1:
                    semSurtVehiculos1.acquire();
                case 2:
                    semSurtVehiculos2.acquire();
                case 3:
                    semSurtVehiculos3.acquire();
                case 4:
                    semSurtVehiculos4.acquire();
                case 5:
                    semSurtVehiculos5.acquire();
                case 6:
                    semSurtVehiculos6.acquire();
                case 7:
                    semSurtVehiculos7.acquire();
            }
        } catch(Exception ex){
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Error while waiting for operator in surt " + surt);
            //Log error here
        } finally {
            semEntrada.release();
        }
    }

    public int operarSurtidor(int operario){
        int surt = -1;
        try{
            semOperarios.acquire();
            surt = surtidorEsperandoOperario();
            while(surt == -1){
                Thread.sleep(100);
                surt = surtidorEsperandoOperario();
            }
            surtidores[surt].setOperario(operario);
        } catch(Exception ex){
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Error while operating surt " + surt);
            //Log error here
        } finally {
            semOperarios.release();
        }
        return surt;
    }

    public void surtidorTerminado(int surt){
        try{
            semOperarios.acquire();
            surtidores[surt].setVehiculo(null);
            surtidores[surt].setOperario(-1);
            surtidores[surt].setLibre(true);
            switch(surt){
                case 0:
                    semSurtVehiculos0.release();
                case 1:
                    semSurtVehiculos1.release();
                case 2:
                    semSurtVehiculos2.release();
                case 3:
                    semSurtVehiculos3.release();
                case 4:
                    semSurtVehiculos4.release();
                case 5:
                    semSurtVehiculos5.release();
                case 6:
                    semSurtVehiculos6.release();
                case 7:
                    semSurtVehiculos7.release();
            }
        } catch (Exception ex){
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Error while finishing surt " + surt);
            //Log error here
        } finally {
            semOperarios.release();
        }
    }

    private void actualizarCola(){
        String textoCola = "";
        for(String nav : colaEntrada){
            textoCola += nav + ", ";
        }
        context.actualizarCola(textoCola);
    }

    private int surtidorLibre(){
        for(Surtidor nav : surtidores){
            if(nav.isLibre()) return nav.getNumero();
        }
        return -1;
    }
    private int surtidorEsperandoOperario(){
        if(!esperandoOperario.isEmpty()) return esperandoOperario.remove();
        else return -1;
    }
}