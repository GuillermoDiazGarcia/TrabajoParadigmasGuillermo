/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import interfazRMI.InterfazGasolineraExterna;
import cliente.MainFrameExt;
import java.rmi.Naming;

/**
 * Clase para importar los datos de la gasolinera principal y exponerlos en la interfaz gráfica
 * @author Guillermo Díaz García
 */
public class ClienteGasolineraExt extends Thread{
    private final MainFrameExt.GasolineraExt gasolinera;
    
    /***
     * Constructor simple
     * @param gasolinera 
     */
    public ClienteGasolineraExt(MainFrameExt.GasolineraExt gasolinera){
        this.gasolinera = gasolinera;
    }
    
    /***
     * Recibe los datos del objeto remoto cada 1 segundo y se los pasa a la gasolinera.
     */
    @Override
    public void run(){
        //Bucle infinito
        while(true){
            try{
                //Esperamos un segundo
                sleep(1000);
                
                MainFrameExt.log(" - Actualizando datos");
                
                //Accedemos al objeto remoto
                interfazRMI.InterfazGasolineraExterna gasEx = (interfazRMI.InterfazGasolineraExterna) Naming.lookup("//127.0.0.1:4000/GasEx");
                
                //Accedemos a los datos mediante RMI y se los pasamos a la gasolinera
                gasolinera.actualizarVehiculos(gasEx.getVehiculos());
                gasolinera.actualizarOperarios(gasEx.getOperarios());
                gasolinera.actualizarCola(gasEx.getCola());
            } catch (Exception ex){
                MainFrameExt.log(" - Error recibiendo datos: " + ex.getMessage());
            }
        }
    }
}
