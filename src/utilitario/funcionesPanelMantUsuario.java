/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesEstadoAdministradorRiesgos;
import static constantes.constantesEstadoAdministradorRiesgos.*;
import static constantes.constantesEstadoCliente.*;
import constantes.constantesEstadoOperario;
import static constantes.constantesEstadoOperario.*;
import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import static constantes.constantesVentanaPrincipal.USUARIO_NO_VALIDO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.print.attribute.Size2DSyntax.MM;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import modelo.Persona;

/**
 *
 * @author gerson
 */
public class funcionesPanelMantUsuario {
    public String[] devolverDatosUsuario(String usuario,String tipo) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlBuscarUsuario = "";
        String tabla = "";
        String[] datosUsuario = new String[10];
        
        if(tipo.equals(TIPO_CLIENTE)) tabla = " cliente ";
        else if(tipo.equals(TIPO_OPERARIO)) tabla = " operario ";
        else if(tipo.equals(TIPO_ADMIN)) tabla = " `administrador de riesgos` ";
        
        sqlBuscarUsuario = "SELECT P.Nombres, P.ApellidoPaterno, P.ApellidoMaterno, P.FechaNacimiento, P.Direccion, P.CorreoElectronico, P.DNI, U.nombreUsuario " +
                           " FROM persona P, " + tabla + " C, usuario U " +
                           " WHERE C.Usuario_idUsuario = U.idUsuario AND C.Persona_idPersona = P.idPersona AND U.nombreUsuario = '" + usuario+ "'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarUsuario = st.executeQuery(sqlBuscarUsuario);
            
            while(resultadoBuscarUsuario!=null && resultadoBuscarUsuario.next()){
                datosUsuario[0] = resultadoBuscarUsuario.getString(1); //Nombre
                datosUsuario[1] = resultadoBuscarUsuario.getString(2); //ApellidoPaterno
                datosUsuario[2] = resultadoBuscarUsuario.getString(3); //ApellidoMaterno
                
                Date fechaNacimiento = (Date)resultadoBuscarUsuario.getObject(4); //FechaNacimiento
                DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd"); //("yyyy-MM-dd HH:mm:ss");
                datosUsuario[3] = fecha.format(fechaNacimiento);
                
                datosUsuario[4] = resultadoBuscarUsuario.getString(5); //Direccion
                datosUsuario[5] = resultadoBuscarUsuario.getString(6); //CorreoElectronico
                datosUsuario[6] = Integer.toString(resultadoBuscarUsuario.getInt(7)); //DNI
                datosUsuario[7] = tipo; //TipoUsuario
                datosUsuario[8] = "DNI"; //DNI
                datosUsuario[9] = usuario; //Usuario
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return datosUsuario;
    }
    
    public void colocarCampoComoNoEditable(JTextField campo){
        campo.setEditable(false);
    }
    
    public void colocarComboBoxesComoNoEditable(JComboBox comboBox){
        comboBox.setEditable(false);
        comboBox.setEnabled(false);
    }
    
    public String DuplicadoUsuario(String username) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        String sqlBuscarPersona = "";
        String cadena="OK";
        int llaveGeneradaPersona = -1;
        int llaveGeneradaUsuario = -1;
        
        sqlBuscarPersona = "SELECT * FROM usuario where nombreUsuario = '" + "';";        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscar = st.executeQuery(sqlBuscarPersona);
            while(resultadoBuscar!=null && resultadoBuscar.next()){
                cadena = "Usuario '" + username + "' ya existe";
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, "Error al registrar un usuario", ex);
        }
        return cadena;
    }
    
    public String RegistrarUsuario(Persona nuevoUsuario, String tipoUsuario) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        String cadena="";
        int llaveGeneradaPersona = -1;
        int llaveGeneradaUsuario = -1;
        
        try {
            PreparedStatement sqlGuardarPersona = conexion.prepareStatement("INSERT INTO persona VALUES (NULL,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            
            sqlGuardarPersona.setString(1, nuevoUsuario.getNombre());
            sqlGuardarPersona.setString(2, nuevoUsuario.getApellidoP());
            sqlGuardarPersona.setString(3, nuevoUsuario.getApellidoM());
            
            SimpleDateFormat formatoDeFechaNacimiento = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento;
            fechaNacimiento = formatoDeFechaNacimiento.parse(nuevoUsuario.getFechaNac());
            Timestamp fechaNac = new Timestamp(fechaNacimiento.getTime());
            
            sqlGuardarPersona.setTimestamp(4, fechaNac);
            sqlGuardarPersona.setString(5, nuevoUsuario.getDireccion());
            sqlGuardarPersona.setString(6, nuevoUsuario.getCorreo());
            sqlGuardarPersona.setString(7, nuevoUsuario.getDocumento());
            int rows = sqlGuardarPersona.executeUpdate();
            
            ResultSet rs = sqlGuardarPersona.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llaveGeneradaPersona = rs.getInt(1);
            }
            
            if(rows > 0){
                cadena= "Usuario insertado con éxito";
            }
            else{
                cadena= "Usuario no se ha insertado";
            }
            
            PreparedStatement sqlGuardarUsuario = conexion.prepareStatement("INSERT INTO usuario VALUES (NULL,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            sqlGuardarUsuario.setString(1, nuevoUsuario.getUsuario());
            sqlGuardarUsuario.setString(2, nuevoUsuario.getContrasenhia());
            sqlGuardarUsuario.setString(3,tipoUsuario);
            
            rows = sqlGuardarUsuario.executeUpdate();
            
            rs = sqlGuardarUsuario.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llaveGeneradaUsuario = rs.getInt(1);
            }
            
            if(rows > 0){
                cadena= "Usuario insertado con éxito";
            }
            else{
                cadena= "Usuario no se ha insertado";
            }
            
            String tabla = "";
            int estadoUsuario = -1;
            
            if(tipoUsuario.equals(TIPO_CLIENTE)){
                tabla = "cliente";
                estadoUsuario = ACTIVO_CLIENTE.ordinal();
            }
            else if(tipoUsuario.equals(TIPO_OPERARIO)){
                tabla = "operario";
                estadoUsuario = ACTIVO_OPERARIO.ordinal();
            }
            else if(tipoUsuario.equals(TIPO_ADMIN)) {
                tabla = "`administrador de riesgos`";
                estadoUsuario = ACTIVO_ADMIN.ordinal();
            }
            
            PreparedStatement sqlGuardarTabla = conexion.prepareStatement("INSERT INTO " + tabla +" VALUES (NULL,?,?,?,?)");
            
            Timestamp fechaRegistro = new Timestamp(new Date().getTime());
            sqlGuardarTabla.setTimestamp(1, fechaRegistro);
            
            sqlGuardarTabla.setInt(2, estadoUsuario);        
            
            sqlGuardarTabla.setInt(3,llaveGeneradaPersona);
            sqlGuardarTabla.setInt(4,llaveGeneradaUsuario);
            
            rows = sqlGuardarTabla.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, "Error al registrar un usuario", ex);
        }
        return cadena;
    }
}
