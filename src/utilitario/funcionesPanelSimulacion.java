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
            int flagDia=0;
            if(flagDia==1)
                intArr=Lectura.leerPedidoxAeropuertoD(nombArch);
                else
                intArr=Lectura.leerPedidoxAeropuerto(nombArch);
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
        
        public double[] calculaRegExpD(String nombArch) throws InstantiationException, IllegalAccessException{
            double[] resp=new double[3];
            int id=0; 
            double[] intArr,results = null;
            double[] yValues={1,2,3,4,5,6,7,8,9,10,11,12};
            double[] yValuesD=new double[312];
            for(int i=0;i<312;i++){
                yValuesD[i]=i+1;
            }
            int flagDia=0;
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
            if(flagDia==1)
                intArr=Lectura.leerPedidoxAeropuertoD(nombArch);
                else
                intArr=Lectura.leerPedidoxAeropuerto(nombArch);
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
}
