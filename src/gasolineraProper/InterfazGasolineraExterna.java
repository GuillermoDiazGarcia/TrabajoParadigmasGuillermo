package gasolineraProper;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz para la gasolinera externa por RMI
 * @author Guillermo Díaz García
 */
public interface InterfazGasolineraExterna extends Remote{
    String getOperario(int numOperario) throws RemoteException;
    String getVehiculo(int numVehiculo) throws RemoteException;
    String[] getOperarios() throws RemoteException;
    String[] getVehiculos() throws RemoteException;
    String getCola() throws RemoteException;
}