/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesVentanaPrincipal.TIPO_ADMIN;
import static constantes.constantesVentanaPrincipal.TIPO_CLIENTE;
import static constantes.constantesVentanaPrincipal.TIPO_OPERARIO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Persona;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesPanelCrearEnvio {
    public String[] devolverDatosAlmacenes() throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlBuscarAlmacenes = "";
        String[] datosAlmacen = new String[10];
        int cont=0;
        
        sqlBuscarAlmacenes = "SELECT ubicacion " + " FROM `almacen` ";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarAlmacen = st.executeQuery(sqlBuscarAlmacenes);
            
            while(resultadoBuscarAlmacen!=null && resultadoBuscarAlmacen.next()){
                datosAlmacen[cont] = resultadoBuscarAlmacen.getString(1); //Ciudad(ubicacion)
                cont++;
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return datosAlmacen;
    }
    
    public Persona BuscarClienteRegistrado(String Documento) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();//null
        Persona nuevo = new Persona();
        String sqlBuscarPersona = "SELECT * FROM `bdlp2_20090245`.`persona` WHERE DNI = '"+ Documento + "'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscar = st.executeQuery(sqlBuscarPersona);
            
            if(resultadoBuscar!=null){
                resultadoBuscar.next();
                
                nuevo.setNombre(resultadoBuscar.getString("Nombres"));
                nuevo.setApellidoP(resultadoBuscar.getString("ApellidoPaterno"));
                nuevo.setApellidoM(resultadoBuscar.getString("ApellidoMaterno"));
                nuevo.setCorreo(resultadoBuscar.getString("CorreoElectronico"));
                nuevo.setDocumento(resultadoBuscar.getString("DNI"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nuevo;
    }
}
