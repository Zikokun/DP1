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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Paquete;
import modelo.Persona;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesPanelCrearEnvio {
    public ArrayList<String> devolverDatosAlmacenes() throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlBuscarAlmacenes = "";
        ArrayList<String> datosAlmacen = new ArrayList<>();
        int cont=0;
        
        sqlBuscarAlmacenes = "SELECT ubicacion " + " FROM `almacen`;";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarAlmacen = st.executeQuery(sqlBuscarAlmacenes);
            //System.out.println(resultadoBuscarAlmacen.toString());
            while(resultadoBuscarAlmacen!=null && resultadoBuscarAlmacen.next()){
                //System.out.println(resultadoBuscarAlmacen.getString("ubicacion"));
                datosAlmacen.add(resultadoBuscarAlmacen.getString("ubicacion"));
                cont++;
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //datosAlmacen.sort();
        return datosAlmacen;
    }
    
    public Persona BuscarClienteRegistrado(String Documento) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();//null
        Persona nuevo = new Persona();
        String sqlBuscarPersona = "SELECT * FROM `persona` WHERE DNI = '"+ Documento + "'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscar = st.executeQuery(sqlBuscarPersona);
            
            while(resultadoBuscar!=null && resultadoBuscar.next()){
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
    
    public int GetComboCiudad(String NombreCiudad) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        int id=0;
        
        String sqlBuscarCiudad = "SELECT * FROM `almacen` WHERE Ubicacion = '"+ NombreCiudad + "'";
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscar = st.executeQuery(sqlBuscarCiudad);
            
            while(resultadoBuscar!=null && resultadoBuscar.next()){
                id = resultadoBuscar.getInt("idAlmacen");
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public int GetLastNumeroRastreo() throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        int id=0;
        
        String sqlBuscarCiudad = "SELECT MAX(numeroRastreo) FROM `paquete`";
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscar = st.executeQuery(sqlBuscarCiudad);
            
            while(resultadoBuscar!=null && resultadoBuscar.next()){
                id = resultadoBuscar.getInt("NumeroRastreo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public String CrearEnvio(Paquete nuevo) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        String cadena="";
        int llaveGeneradaPersona = -1;
        
        Persona Aux = new Persona();
        Aux = BuscarClienteRegistrado(nuevo.getRemitente().getPersona().getDocumento());
        if(Aux.getDocumento().equals("")){
            funcionesPanelMantUsuario utilitarioPanelMantUsuario = new funcionesPanelMantUsuario();
            Aux = nuevo.getRemitente().getPersona();
            utilitarioPanelMantUsuario.RegistrarUsuario(Aux, TIPO_CLIENTE);
        }  
        Persona Aux2 = new Persona();
        Aux2 = BuscarClienteRegistrado(nuevo.getReceptor().getDocumento());
        if(Aux2.getDocumento().equals("")){
            funcionesPanelMantUsuario utilitarioPanelMantUsuario = new funcionesPanelMantUsuario();
            Aux2 = nuevo.getReceptor();
            utilitarioPanelMantUsuario.RegistrarUsuario(Aux2, TIPO_CLIENTE);
        }  
        
        try {
            PreparedStatement sqlCrearEnvio = conexion.prepareStatement("INSERT INTO paquete VALUES (NULL,?,?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            
            sqlCrearEnvio.setString(1, nuevo.getNumeroRastreo());
            sqlCrearEnvio.setInt(2, nuevo.getAlmacenOrigen().getId());
            sqlCrearEnvio.setInt(3, nuevo.getAlamcenDestino().getId());
            Timestamp fechaE = new Timestamp(nuevo.getFechaEnvio().getTime());
            sqlCrearEnvio.setTimestamp(4, fechaE);
            Timestamp fechaR = new Timestamp(nuevo.getFechaRecepcion().getTime());
            sqlCrearEnvio.setTimestamp(5, fechaR);
            sqlCrearEnvio.setString(6, nuevo.getDescripcion());
            sqlCrearEnvio.setInt(7, nuevo.getEstado());
            sqlCrearEnvio.setString(8, nuevo.getRemitente().getPersona().getDocumento());
            sqlCrearEnvio.setString(9, nuevo.getReceptor().getDocumento());
            int rows = sqlCrearEnvio.executeUpdate();
            
            ResultSet rs = sqlCrearEnvio.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llaveGeneradaPersona = rs.getInt(1);
            }
            
            if(rows > 0){
                cadena= "Usuario insertado con éxito";
            }
            else{
                cadena= "Usuario no se ha insertado";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, "Error al registrar un usuario", ex);
        }
        return cadena;
    }
}
