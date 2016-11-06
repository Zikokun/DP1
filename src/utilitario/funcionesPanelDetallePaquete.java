/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constanteEstadoPaquete;
import static constantes.constantesGenerales.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Ciudad;
import modelo.Paquete;
import utilitario.renderizadoTabla.render;

/**
 *
 * @author gerson
 */
public class funcionesPanelDetallePaquete {
    public String devolverEstadoPaquete(int estadoPaquete){
        constanteEstadoPaquete c[] = constanteEstadoPaquete.values();
        if(estadoPaquete >= c.length) return ESTADO_PAQUETE_NO_VALIDO;
        return c[estadoPaquete].getDetalle();
    }
    
    public List<String[]> devolverDetallePaquete(String numeroRastreo) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        List<String[]> paqueteDetalle = new ArrayList<>();
        String SqlBuscarDetallePaquete = "";
        SqlBuscarDetallePaquete = " SELECT P.numeroRastreo, P.estado, M.Nombres, M.ApellidoPaterno, A.ubicacion, P.descripcion " + 
                                  " FROM paquete P, avion_has_paquete H, vuelo V, almacen A, persona M " +
                                  " WHERE P.numeroRastreo = '" + numeroRastreo + "' AND H.Paquete_idPaquete = P.idPaquete AND H.Avion_idAvion = V.idVuelo AND V.idLugarDestino = A.idAlmacen AND P.Persona_idPersona = M.idPersona;";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarPaqueteDetalle = st.executeQuery(SqlBuscarDetallePaquete);
            
            while(resultadoBuscarPaqueteDetalle!=null && resultadoBuscarPaqueteDetalle.next()){
                String[] datosPaquete = new String[5];
                datosPaquete[0] = resultadoBuscarPaqueteDetalle.getString(1);
                datosPaquete[1] = devolverEstadoPaquete(resultadoBuscarPaqueteDetalle.getInt(2));
                datosPaquete[2] = resultadoBuscarPaqueteDetalle.getString(3) + " " + resultadoBuscarPaqueteDetalle.getString(4);
                datosPaquete[3] = resultadoBuscarPaqueteDetalle.getString(5);
                datosPaquete[4] = resultadoBuscarPaqueteDetalle.getString(6);
                paqueteDetalle.add(datosPaquete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paqueteDetalle;
    }
    
    public Paquete devolverDescripcionGeneralPaquete(String numeroRastreo) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        Paquete paquete = new Paquete();
        
        String sqlBuscarDescripcionPaquete = "";
        
        sqlBuscarDescripcionPaquete = " SELECT A.ubicacion, B.ubicacion, P.fechaEnvio, P.fechaRecepcion, P.estado, P.descripcion " +
                                      " FROM paquete P, almacen A, almacen B " +
                                      " WHERE P.numeroRastreo = '" + numeroRastreo + "' AND P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen;";

        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarDescripcionPaquete = st.executeQuery(sqlBuscarDescripcionPaquete);
            
            while(resultadoBuscarDescripcionPaquete!=null && resultadoBuscarDescripcionPaquete.next()){
                String sLugarOrigen = resultadoBuscarDescripcionPaquete.getString(1);
                String sLugarDestino = resultadoBuscarDescripcionPaquete.getString(2);
                
                Date fechaEnvio = (Date)resultadoBuscarDescripcionPaquete.getObject(3);
                Date fechaRecepcion = (Date)resultadoBuscarDescripcionPaquete.getObject(4);
                
                int estado = resultadoBuscarDescripcionPaquete.getInt(5);
                String sDescripcion = resultadoBuscarDescripcionPaquete.getString(6);
                
                paquete.setDescripcion(sDescripcion);
                paquete.setEstado(estado);
                paquete.setFechaEnvio(fechaEnvio);
                paquete.setFechaRecepcion(fechaRecepcion);
                
                Ciudad ciudadOrigen = new Ciudad();
                Ciudad ciudadDestino = new Ciudad();
                
                ciudadOrigen.setCiudad(sLugarOrigen);
                ciudadDestino.setCiudad(sLugarDestino);
                
                paquete.setAlmacenOrigen(ciudadOrigen);
                paquete.setAlamcenDestino(ciudadDestino);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paquete;
    }
    
    public void colocarCampoComoNoEditable(JTextField campo){
        campo.setEditable(false);
    }
    
    public String convertirStringFecha(Date fecha){
        String sFecha = "";
        
        DateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        sFecha = fechaFormato.format(fecha);
        
        return sFecha;
    }
    
     public void mostrarDetallePaquete(List<String[]> listadoDetallePaquete, JTable tablaDetallePaquete){
        
         tablaDetallePaquete.setDefaultRenderer(Object.class, new render());
        
        DefaultTableModel modelo=(DefaultTableModel) tablaDetallePaquete.getModel();
        
        for(int i = 0; i < listadoDetallePaquete.size(); i++){
            modelo.addRow(listadoDetallePaquete.get(i));
        }
        
        tablaDetallePaquete.setModel(modelo); 
     }
}
