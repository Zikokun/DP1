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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Lectura;
import regression.NotEnoughValues;
import regression.RegressionMethods;
import test1_gui_alg.Test1_gui_alg;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesPanelSimulacion {
        
        public void calculaRegExp(String nombArch){
            double[] intArr,results = null;
            double[] yValues={1,2,3,4,5,6,7,8,9,10,11,12};
            double[] yValuesD=new double[312];
            for(int i=0;i<312;i++){
                yValuesD[i]=i+1;
            }
            int flagDia=constantes.constantesGenerales.SIMULACION_POR_DIA;
            int flagArch=constantes.constantesGenerales.LECTURA_TOTAL_ARCH;
            if(flagDia==1){
                if(flagArch==1)
                    intArr=Lectura.leerPedidoxAeropuertoD_full(nombArch);
                else
                    intArr=Lectura.leerPedidoxAeropuertoD(nombArch);
            }else{
                if(flagArch==1)
                    intArr=Lectura.leerPedidoxAeropuerto_full(nombArch);
                else
                     intArr=Lectura.leerPedidoxAeropuerto(nombArch);
                
            }
               results=new double[2];
   //            funcionesRegresionExp regExp=new funcionesRegresionExp(intArr,yValues);
   //            results=regExp.calcExpValores();
           try {
               if(flagDia==1)
                   results=RegressionMethods.exponential(intArr,yValuesD);
               else
                   results=RegressionMethods.exponential(intArr,yValues);
           } catch (NotEnoughValues ex) {
               Logger.getLogger(Test1_gui_alg.class.getName()).log(Level.SEVERE, null, ex);
           }
           for(int i=0;i<intArr.length;i++){
               System.out.print(intArr[i]+"-");
           }
           System.out.println();
           System.out.println("funcion: "+"y="+results[0]+"*e^("+results[1]+"*x)");
        }
        
       public double[] calculaRegExpD(String nombArch) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
            double[] resp=new double[3];
            int id=0; 
            double[] intArr,results = null;
            double[] yValues={1,2,3,4,5,6,7,8,9,10,11,12};
            double[] yValuesD=new double[312];
            for(int i=0;i<312;i++){
                yValuesD[i]=i+1;
            }
            funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
            
                Connection conexion = cc.conexion();
            
            
            String codC= nombArch.substring(18,22);
            String sqlBuscarUsuario = "";
            String tabla = "";
            String[] datosUsuario = new String[10];
            tabla = " almacen ";
            sqlBuscarUsuario = "SELECT idAlmacen " +
                           " FROM " + tabla  +
                           " WHERE codCiudad = '" + codC+ "'";
            
            try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarAlmacen = st.executeQuery(sqlBuscarUsuario);
            
            while(resultadoBuscarAlmacen!=null && resultadoBuscarAlmacen.next()){
                id=Integer.parseInt(resultadoBuscarAlmacen.getString(1));
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
            int flagDia=constantes.constantesGenerales.SIMULACION_POR_DIA;
            int flagArch=constantes.constantesGenerales.LECTURA_TOTAL_ARCH;
            if(flagDia==1){
                if(flagArch==1)
                    intArr=Lectura.leerPedidoxAeropuertoD_full(nombArch);
                else
                    intArr=Lectura.leerPedidoxAeropuertoD(nombArch);
            }else{
                if(flagArch==1)
                    intArr=Lectura.leerPedidoxAeropuerto_full(nombArch);
                else
                     intArr=Lectura.leerPedidoxAeropuerto(nombArch);
                
            }
               results=new double[2];
   //            funcionesRegresionExp regExp=new funcionesRegresionExp(intArr,yValues);
   //            results=regExp.calcExpValores();
           try {
               if(flagDia==1)
                   results=RegressionMethods.exponential(intArr,yValuesD);
               else
                   results=RegressionMethods.exponential(intArr,yValues);
           } catch (NotEnoughValues ex) {
               Logger.getLogger(Test1_gui_alg.class.getName()).log(Level.SEVERE, null, ex);
           }
          resp[0]=results[0];
          resp[1]=results[1];
          resp[2]=(double)id;
          return resp;
        }
       public void lectorPaquetesSimulacion(int tipo) throws InstantiationException, IllegalAccessException, ParseException{
           
            funcionesPanelSimulacion func = new funcionesPanelSimulacion();
            String nombArch = "/recursos/arch_";
            int contBD = 4;
            int contador=0;
            FuncionExponencial funcion = new FuncionExponencial();
            for (contBD = 4; contBD < 44; contBD++) {
                funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
                Connection conexion = cc.conexion();//null

                String origen = "";
                String sqlBuscarCiudad = "SELECT codCiudad FROM `almacen` WHERE idAlmacen = '" + contBD + "'";
                try {
                    Statement st = conexion.createStatement();
                    ResultSet resultadoBuscar = st.executeQuery(sqlBuscarCiudad);

                    while (resultadoBuscar != null && resultadoBuscar.next()) {
                        origen = resultadoBuscar.getString("codCiudad");
                    }
                    String NombreCompleto = nombArch + origen + ".txt";
                    System.out.println("archivo: " + NombreCompleto);
                    double[] resp=new double[3];
                    resp = func.calculaRegExpD(NombreCompleto);
                    //System.out.println("x="+resp[0]+" Y= "+resp[1]+" Id="+resp[2]);
                    resp[2] = contBD;
                    contador = funcion.CalcularFuncion(resp[0], resp[1], resp[2],tipo,0);
                } catch (SQLException ex) {
                }
            }
            System.out.println("termino crear paquetes");
       }
        public int revisaFlagSimu() throws InstantiationException, IllegalAccessException{
           int respt=2;
           funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
           Connection conexion = cc.conexion();
           int aux=0;
                String sqlBuscaFlag = "SELECT flagSimu FROM `datossimu` WHERE `iddatosSimu`='1';";
                try {
                    Statement st = conexion.createStatement();
                    ResultSet resultadoBuscar = st.executeQuery(sqlBuscaFlag);

                    while (resultadoBuscar != null && resultadoBuscar.next()) {
                        aux = resultadoBuscar.getInt(1);
                    }
                    if(aux==1)
                        respt=1;
                    else
                        respt=0;
                } catch (SQLException ex) {
                }
            System.out.println("flag:"+respt);
           return respt;
       }
       public void actualizaFlag(int tipoSimu)throws InstantiationException, IllegalAccessException{
           funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
           Connection conexion = cc.conexion();
           int aux=0;
          

                try {
                     PreparedStatement sqlactualizaFlag = conexion.prepareStatement( "UPDATE datossimu SET `flagSimu`='1', `tipoSimu`=' "+ tipoSimu +" ' WHERE `iddatossimu`='1'; ");
                    int actualizaFlag = sqlactualizaFlag.executeUpdate();
                } catch (SQLException ex) {
                }
       }
       public int comparaSimu(int tipoSim) throws InstantiationException, IllegalAccessException{
           int respt=0;
           funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
           Connection conexion = cc.conexion();
           int aux=0;
                String sqlBuscaFlag = "SELECT tipoSimu FROM `datossimu` WHERE `iddatosSimu`='1'; ";
                try {
                    Statement st = conexion.createStatement();
                    ResultSet resultadoBuscar = st.executeQuery(sqlBuscaFlag);

                    while (resultadoBuscar != null && resultadoBuscar.next()) {
                        aux = resultadoBuscar.getInt(1);
                    }
                    if(aux==tipoSim)
                        respt=1;
                    else
                        respt=0;
                } catch (SQLException ex) {
                }
           return respt;
       }
       public Date obtieneFecha()throws InstantiationException, IllegalAccessException{
           funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
           Connection conexion = cc.conexion();
           Date respt=new Date();
           int aux=0;
          

                String sqlBuscaFlag = "SELECT fechaActualSimu FROM `datossimu` WHERE `iddatosSimu`='1';";
                try {
                    Statement st = conexion.createStatement();
                    ResultSet resultadoBuscar = st.executeQuery(sqlBuscaFlag);

                    while (resultadoBuscar != null && resultadoBuscar.next()) {
                        respt = resultadoBuscar.getDate(1);
                    }
                    
                } catch (SQLException ex) {
                }
                
           return respt;
       }
       
}
