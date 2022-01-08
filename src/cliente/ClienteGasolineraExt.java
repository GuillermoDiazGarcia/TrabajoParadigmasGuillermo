/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.rmi.Naming;

/**
 * Clase para importar los datos de la gasolinera principal y exponerlos en la interfaz gráfica
 * @author Guillermo Díaz García
 */
public class ClienteGasolineraExt extends Thread{
    private final MainFrameExt.GasolineraExt gasolinera;
    
    public ClienteGasolineraExt(MainFrameExt.GasolineraExt gasolinera){
        this.gasolinera = gasolinera;
    }
    
    @Override
    public void run(){
        while(true){
            try{
                MainFrameExt.log(" - Actualizando datos");
                
                InterfazGasolineraExterna gasEx = (InterfazGasolineraExterna) Naming.lookup("//127.0.0.1/GasolineraExterna");
                
                gasolinera.actualizarVehiculos(gasEx.getVehiculos());
                gasolinera.actualizarOperarios(gasEx.getOperarios());
                gasolinera.actualizarCola(gasEx.getCola());
                
                sleep(1000);
            } catch (Exception ex){
                MainFrameExt.log(" - Error recibiendo datos");
            }
        }
    }
}
