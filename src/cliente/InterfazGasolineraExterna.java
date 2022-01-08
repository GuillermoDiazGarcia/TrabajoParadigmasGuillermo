/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.rmi.RemoteException;

/**
 * Interfaz para la gasolinera externa por RMI
 * @author Guillermo Díaz García
 */
public interface InterfazGasolineraExterna {
    String getOperario(int numOperario) throws RemoteException;
    String getVehiculo(int numVehiculo) throws RemoteException;
    String[] getOperarios() throws RemoteException;
    String[] getVehiculos() throws RemoteException;
    String getCola() throws RemoteException;
}