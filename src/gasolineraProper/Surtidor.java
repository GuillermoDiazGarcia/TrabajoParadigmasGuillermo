package gasolineraProper;

import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import javax.swing.JTextField;

/**
 * Clase simple que se utiliza como contenedor de datos. Incluye los campos en los que
 * se escriben el vehículo y el operario, así como la condición asociada a cada surtidor
 * @author Guillermo Díaz García
 */
public class Surtidor {
    private boolean libre = true;
    private int operario = -1;
    private String vehiculo = null;
    private final int numero;
    private final Condition cond;
    private final JTextField campoVeh, campoOper;
    
    /***
     * CCon
     * @param numero
     * @param cond
     * @param campoVeh
     * @param campoOper 
     */
    public Surtidor (int numero, Condition cond, JTextField campoVeh, JTextField campoOper){
        this.numero = numero;
        this.cond = cond;
        this.campoVeh = campoVeh;
        this.campoOper = campoOper;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public int getOperario() {
        return operario;
    }

    public void setOperario(int operario) {
        this.operario = operario;
        if (operario == -1) campoOper.setText("");
        else campoOper.setText("Operario" + operario);
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
        if(vehiculo == null) campoVeh.setText("");
        else campoVeh.setText(vehiculo);
    }

    public int getNumero() {
        return numero;
    }
    
    public Condition getCond(){
        return cond;
    }
    
    public JTextField getCampoVeh(){
        return campoVeh;
    }
    
    public JTextField getCampoOper(){
        return campoOper;
    }
    
    /***
     * Datos para el acceso remoto por RMI
     * @return datosSurtidor
     */
    public String[] getDatosSurtidor(){
        String[] datosSurtidor = new String[2];
        datosSurtidor[0] = vehiculo;
        datosSurtidor[1] = "Operario" + operario;
        return datosSurtidor;
    }
}
