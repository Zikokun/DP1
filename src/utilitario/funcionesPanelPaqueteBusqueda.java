/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constanteEstadoPaquete;
import static constantes.constantesGenerales.*;
import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Paquete;
import modelo.Persona;
import utilitario.renderizadoTabla.render;
import vista.panelDetallePaquete;

public class funcionesPanelPaqueteBusqueda {
    public String devolverEstadoPaquete(int estadoPaquete){
        constanteEstadoPaquete c[] = constanteEstadoPaquete.values();
        if(estadoPaquete >= c.length) return ESTADO_PAQUETE_NO_VALIDO;
        return c[estadoPaquete].getDetalle();
    }
    
    public List<Paquete> devolverPaquetesAsociadosNumeroRastreo(String usuario, String contrasenha, String tipoUsuario, String sNumeroRastreoBuscar) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        String nombreUsuario = " AND U.nombreUsuario = '" + usuario + "' ";
        String numRastreo = " AND P.numeroRastreo = '" + sNumeroRastreoBuscar + "' ";
        List<Paquete> lstPaquetes = new ArrayList<>();
            
        if(tipoUsuario.equals(TIPO_OPERARIO)) nombreUsuario = "";
        if(sNumeroRastreoBuscar.equals("")) numRastreo = "";
        
        sqlBuscarPaquetes = " SELECT P.numeroRastreo, P.estado, U.nombreUsuario, M.Nombres, M.ApellidoPaterno , A.ubicacion, B.ubicacion, P.descripcion, M.DNI " +
                " FROM cliente C, usuario U, paquete P, almacen A, almacen B, persona M " +
                " WHERE P.Cliente_idCliente = C.idCliente AND C.Usuario_idUsuario = U.idUsuario " + nombreUsuario + numRastreo
                + " AND P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen AND M.idPersona = P.Persona_idPersona AND (P.estado=0 OR P.estado=4); ";
        
        try {   
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarPaquetes = st.executeQuery(sqlBuscarPaquetes);
            
            while(resultadoBuscarPaquetes!=null && resultadoBuscarPaquetes.next()){
                Paquete paquete = new Paquete();
                Ciudad ciudadOrigen = new Ciudad();
                Ciudad ciudadDestino = new Ciudad();
                Cliente remitente = new Cliente();
                Persona personaRemi = new Persona();
                Persona receptor = new Persona();
                
                String sNumeroRastreo = resultadoBuscarPaquetes.getString(1);
                int estado = resultadoBuscarPaquetes.getInt(2);
                String sNombreUsuario = resultadoBuscarPaquetes.getString(3);
                String sNombre = resultadoBuscarPaquetes.getString(4);
                String sApellidoPaterno = resultadoBuscarPaquetes.getString(5);
                String sUbicacionOrigen = resultadoBuscarPaquetes.getString(6);
                String sUbicacionDestino = resultadoBuscarPaquetes.getString(7);
                String sDescripcion = resultadoBuscarPaquetes.getString(8);
                
                paquete.setNumeroRastreo(sNumeroRastreo);
                paquete.setEstado(estado);
                
                personaRemi.setUsuario(sNombreUsuario);
                remitente.setPersona(personaRemi);
                paquete.setRemitente(remitente);
                
                receptor.setNombre(sNombre);
                receptor.setApellidoP(sApellidoPaterno);
                paquete.setReceptor(receptor);
                
                ciudadOrigen.setCiudad(sUbicacionOrigen);
                paquete.setAlmacenOrigen(ciudadOrigen);
                
                ciudadDestino.setCiudad(sUbicacionDestino);
                paquete.setAlamcenDestino(ciudadDestino);
                
                paquete.setDescripcion(sDescripcion);
                
                lstPaquetes.add(paquete);
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaquetes;
    }
    
    public List<Paquete> devolverPaquetesAsociadosDNI(String usuario, String contrasenha, String tipoUsuario, String sDNI) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        String nombreUsuario = " AND U.nombreUsuario = '" + usuario + "' ";
        String numDNI = " AND M.DNI = '" + sDNI + "' ";
        List<Paquete> lstPaquetes = new ArrayList<>();
            
        if(tipoUsuario.equals(TIPO_OPERARIO)) nombreUsuario = "";
        if(sDNI.equals("")) numDNI = "";
        
        sqlBuscarPaquetes = " SELECT P.numeroRastreo, P.estado, U.nombreUsuario, M.Nombres, M.ApellidoPaterno , A.ubicacion, B.ubicacion, P.descripcion, M.DNI" +
                " FROM cliente C, usuario U, paquete P, almacen A, almacen B, persona M " +
                " WHERE P.Cliente_idCliente = C.idCliente AND C.Usuario_idUsuario = U.idUsuario " + nombreUsuario + numDNI
                + " AND P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen AND M.idPersona = P.Persona_idPersona; ";
        
        try {   
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarPaquetes = st.executeQuery(sqlBuscarPaquetes);
            
            while(resultadoBuscarPaquetes!=null && resultadoBuscarPaquetes.next()){
                Paquete paquete = new Paquete();
                Ciudad ciudadOrigen = new Ciudad();
                Ciudad ciudadDestino = new Ciudad();
                Cliente remitente = new Cliente();
                Persona personaRemi = new Persona();
                Persona receptor = new Persona();
                
                String sNumeroRastreo = resultadoBuscarPaquetes.getString(1);
                int estado = resultadoBuscarPaquetes.getInt(2);
                String sNombreUsuario = resultadoBuscarPaquetes.getString(3);
                String sNombre = resultadoBuscarPaquetes.getString(4);
                String sApellidoPaterno = resultadoBuscarPaquetes.getString(5);
                String sUbicacionOrigen = resultadoBuscarPaquetes.getString(6);
                String sUbicacionDestino = resultadoBuscarPaquetes.getString(7);
                String sDescripcion = resultadoBuscarPaquetes.getString(8);
                
                paquete.setNumeroRastreo(sNumeroRastreo);
                paquete.setEstado(estado);
                
                personaRemi.setUsuario(sNombreUsuario);
                remitente.setPersona(personaRemi);
                paquete.setRemitente(remitente);
                
                receptor.setNombre(sNombre);
                receptor.setApellidoP(sApellidoPaterno);
                paquete.setReceptor(receptor);
                
                ciudadOrigen.setCiudad(sUbicacionOrigen);
                paquete.setAlmacenOrigen(ciudadOrigen);
                
                ciudadDestino.setCiudad(sUbicacionDestino);
                paquete.setAlamcenDestino(ciudadDestino);
                
                paquete.setDescripcion(sDescripcion);
                
                lstPaquetes.add(paquete);
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaquetes;
    }
    
    public List<Paquete> devolverPaquetesAsociados(String usuario, String contrasenha, String tipoUsuario) throws InstantiationException, IllegalAccessException{
        
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        String nombreUsuario = " AND U.nombreUsuario = '" + usuario + "' ";
        List<Paquete> lstPaquetes = new ArrayList<>();
            
        if(tipoUsuario.equals(TIPO_OPERARIO)) nombreUsuario = "";
            
        sqlBuscarPaquetes = " SELECT P.numeroRastreo, P.estado, U.nombreUsuario, M.Nombres, M.ApellidoPaterno , A.ubicacion, B.ubicacion, P.descripcion, M.DNI " +
                " FROM cliente C, usuario U, paquete P, almacen A, almacen B, persona M " +
                " WHERE P.Cliente_idCliente = C.idCliente AND C.Usuario_idUsuario = U.idUsuario " +  nombreUsuario
                + " AND P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen AND M.idPersona = P.Persona_idPersona AND (P.estado=0 OR P.estado=4); ";
        
        try {   
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarPaquetes = st.executeQuery(sqlBuscarPaquetes);
            
            while(resultadoBuscarPaquetes!=null && resultadoBuscarPaquetes.next()){
                Paquete paquete = new Paquete();
                Ciudad ciudadOrigen = new Ciudad();
                Ciudad ciudadDestino = new Ciudad();
                Cliente remitente = new Cliente();
                Persona personaRemi = new Persona();
                Persona receptor = new Persona();
                
                String sNumeroRastreo = resultadoBuscarPaquetes.getString(1);
                int estado = resultadoBuscarPaquetes.getInt(2);
                String sNombreUsuario = resultadoBuscarPaquetes.getString(3);
                String sNombre = resultadoBuscarPaquetes.getString(4);
                String sApellidoPaterno = resultadoBuscarPaquetes.getString(5);
                String sUbicacionOrigen = resultadoBuscarPaquetes.getString(6);
                String sUbicacionDestino = resultadoBuscarPaquetes.getString(7);
                String sDescripcion = resultadoBuscarPaquetes.getString(8);
                String sDNI = resultadoBuscarPaquetes.getString(9);
                
                paquete.setNumeroRastreo(sNumeroRastreo);
                paquete.setEstado(estado);
                
                personaRemi.setUsuario(sNombreUsuario);
                //personaRemi.setDocumento(sDNI);
                remitente.setPersona(personaRemi);
                paquete.setRemitente(remitente);
                
                receptor.setNombre(sNombre);
                receptor.setDocumento(sDNI);
                receptor.setApellidoP(sApellidoPaterno);
                paquete.setReceptor(receptor);
                
                ciudadOrigen.setCiudad(sUbicacionOrigen);
                paquete.setAlmacenOrigen(ciudadOrigen);
                
                ciudadDestino.setCiudad(sUbicacionDestino);
                paquete.setAlamcenDestino(ciudadDestino);
                
                paquete.setDescripcion(sDescripcion);
                
                lstPaquetes.add(paquete);
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaquetes;
    }
    
    public List<String[]> transformarListadoPaquetes(List<Paquete> listadoPaquetes){
        List<String[]> lstPaquetes = new ArrayList<>();
        
        for(int i = 0; i < listadoPaquetes.size(); i++){
            String[] datosPaquete = new String[7];
            Paquete paquete = listadoPaquetes.get(i);
            datosPaquete[0] = paquete.getNumeroRastreo();
            datosPaquete[1] = devolverEstadoPaquete(paquete.getEstado());
            datosPaquete[2] = paquete.getReceptor().getDocumento();
            datosPaquete[3] = paquete.getReceptor().getNombre() + " " + paquete.getReceptor().getApellidoP();
            datosPaquete[4] = paquete.getAlmacenOrigen().getCiudad();
            datosPaquete[5] = paquete.getAlamcenDestino().getCiudad();
            datosPaquete[6] = paquete.getDescripcion();
            lstPaquetes.add(datosPaquete);
        }
        return lstPaquetes;
    }
    
    public Object[] listadoMasBotonDetalle(String[] datosPaquetes){
        Object datos[] = new Object[8];

        for(int i = 0; i < datosPaquetes.length; i++){
            datos[i] = datosPaquetes[i]; 
        }
        datos[7] = new JButton("Ver Detalle");
        return datos;
    }    
    
    private void limpiarJTABLE(JTable tablaPaquetes){
        DefaultTableModel modelo=(DefaultTableModel) tablaPaquetes.getModel();
        for (int i = 0; i < tablaPaquetes.getRowCount(); i++) {
           modelo.removeRow(i);
           i-=1;
       }
    }
    
    public void mostrarPaquetes(List<String[]> listadoPaquetes, JTable tablaPaquetes){
        
        limpiarJTABLE(tablaPaquetes);
        
        tablaPaquetes.setDefaultRenderer(Object.class, new render());
                
        DefaultTableModel modelo=(DefaultTableModel) tablaPaquetes.getModel();

        for(int i = 0; i < listadoPaquetes.size(); i++){
            Object datos[] = listadoMasBotonDetalle(listadoPaquetes.get(i));
            modelo.addRow(datos); 
        }
        tablaPaquetes.setModel(modelo); 
    }
}
