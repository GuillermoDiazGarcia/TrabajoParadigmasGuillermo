package gasolineraProper;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Esta clase Thread se ocupa de las peticiones del cliente y envía los datos de la
 * gasolinera cada 1 segundo
 * @author Guillermo Díaz García
 */
public class Servidor extends Thread{
    private final MainFrame.Gasolinera gasolinera;
    private String[][] datosSurtidores = new String[8][2];
    private String datosCola = "";
    
    public Servidor(MainFrame.Gasolinera gasolinera){
        this.gasolinera = gasolinera;
        for(int i=0;i<8;i++){
            for(int j=0;j<2;j++){
                this.datosSurtidores[i][j] = "";
            }
        }
        try{
            MainFrame.log(" - Externalizado gasEx");
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            Registry registry = LocateRegistry.createRegistry(4000);
            GasolineraExterna gasEx = new GasolineraExterna(datosSurtidores, datosCola);
            Naming.rebind("//127.0.0.1/GasEx", gasEx);
        } catch(Exception ex){
            MainFrame.log(" - Error inicializando objeto externo: " + ex.getMessage());
        }
    }
    
    @Override
    public void run(){
        while(true){
            datosSurtidores = gasolinera.getDatosSurtidores();
            datosCola = gasolinera.getDatosCola();
            try{
                sleep(1000);
            } catch (Exception ex){
                MainFrame.log(" - Error sleeping in external object");
            }
        }
    }
}
