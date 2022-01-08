/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gasolineraProper;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Clase de datos para uso con RMI
 * @author Guillermo Díaz García
 */
public class GasolineraExterna extends UnicastRemoteObject implements InterfazGasolineraExterna{
    private String[] vehiculos = new String[8];
    private String[] operarios = new String[8];
    private String cola;
    
    public GasolineraExterna(String[][] datosSurtidores, String cola) throws RemoteException{
        for(int i=0;i<8;i++){
            this.vehiculos[i] = datosSurtidores[i][0];
            this.operarios[i] = datosSurtidores[i][1];
        }
        this.cola = cola;
    }

    @Override
    public String getVehiculo(int numVehiculo) throws RemoteException{
        return this.vehiculos[numVehiculo];
    }
    
    @Override
    public String[] getVehiculos() throws RemoteException{
        return this.vehiculos;
    }

    @Override
    public String getOperario(int numOperario) throws RemoteException{
        return this.operarios[numOperario];
    }
    
    @Override
    public String[] getOperarios() throws RemoteException{
        return this.operarios;
    }
    
    @Override
    public String getCola() throws RemoteException{
        return this.cola;
    }
}
