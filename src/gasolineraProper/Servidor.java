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
    private Registry registry;
    
    public Servidor(MainFrame.Gasolinera gasolinera){
        this.gasolinera = gasolinera;
        for(int i=0;i<8;i++){
            for(int j=0;i<2;j++){
                this.datosSurtidores[i][j] = "";
            }
        }
        try{
            registry = LocateRegistry.createRegistry(4000);
        } catch(Exception ex){
            MainFrame.log(" - Error inicializando servidor");
        }
    }
    
    @Override
    public void run(){
        while(true){
            datosSurtidores = gasolinera.getDatosSurtidores();
            datosCola = gasolinera.getDatosCola();
            GasolineraExterna gasEx = new GasolineraExterna(datosSurtidores, datosCola);
            
            try{
                Naming.rebind("//127.0.0.1/GasolineraExterna", gasEx);
                MainFrame.log(" - Externalizado gasEx");
                sleep(1000);
            } catch (Exception ex){
                MainFrame.log(" - Error externalizando gasEx");
            }
        }
    }
}
