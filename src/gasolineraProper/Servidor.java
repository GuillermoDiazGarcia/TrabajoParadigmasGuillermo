package gasolineraProper;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Esta clase Thread se ocupa de las peticiones del cliente. Externaliza el objeto remoto
 * y actualiza sus datos cada 1 segundo.
 * @author Guillermo Díaz García
 */
public class Servidor extends Thread{
    private final MainFrame.Gasolinera gasolinera;
    private String[][] datosSurtidores = new String[8][2];
    private String datosCola = "";
    private GasolineraExterna gasEx;
    
    /***
     * Constructor que crea y externaliza el objeto remoto
     * @param gasolinera 
     */
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
            gasEx = new GasolineraExterna(datosSurtidores, datosCola);
            Naming.rebind("//127.0.0.1:4000/GasEx", gasEx);
        } catch(Exception ex){
            MainFrame.log(" - Error inicializando objeto externo: " + ex.getMessage());
        }
    }
    
    /***
     * Método run con un bucle infinito que actualiza los datos del objeto
     * remoto cada 1 segundo
     */
    @Override
    public void run(){
        while(true){
            gasEx.setDatosSurtidores(gasolinera.getDatosSurtidores());
            gasEx.setDatosCola(gasolinera.getDatosCola());
            try{
                sleep(1000);
            } catch (Exception ex){
                MainFrame.log(" - Error sleeping in external object");
            }
        }
    }
}
