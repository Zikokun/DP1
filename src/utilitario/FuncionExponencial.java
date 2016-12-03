/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesVentanaPrincipal.TIPO_ADMIN;
import static constantes.constantesVentanaPrincipal.TIPO_CLIENTE;
import static constantes.constantesVentanaPrincipal.TIPO_OPERARIO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.Math.exp;
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
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Paquete;
import modelo.Persona;
import java.math.*;
import java.util.List;
import java.util.Random;
import modelo.Lectura;
import modelo.Pedido;

//2.71828

public class FuncionExponencial {
    public int CalcularFuncion(double X, double Y, double Z, int tipo,String origen) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        int value;
        String cadena="-";
        int  j=0, number=0, rastreo=0, cont=0, limite;
        if(tipo==0) limite=3;
        else limite=365;
        Random r = new Random();
        funcionesPanelCrearEnvio utilitarioPanelCrearEnvio = new funcionesPanelCrearEnvio();
        rastreo = utilitarioPanelCrearEnvio.GetLastNumeroRastreo() + 1;
        String linea;
        BufferedWriter bufer;
        
        try {
            bufer = new BufferedWriter(new FileWriter("src/recursos/salidaPaqAnho.txt", true));

                    
            for(cont=0;cont<limite;cont++){
                System.out.println(" dia: "+cont);
                double e=exp(Y*cont);
                value =(int) Math.round( X * exp(Y*cont));
                rastreo+=value;
                for(j=0;j<value;j++){
                    number = r.nextInt(39)+4;
                    if(number==(int)Math.round(Z)) {
                        if(number==43) number=4;
                        else number++;
                    }
                    cadena = utilitarioPanelCrearEnvio.CrearEnvioExponencialS((int) Math.round(Z), number, Integer.toString(rastreo+j), tipo,cont);
                    //System.out.println("cadena: "+ cadena);
                    bufer.write(cadena, 0, cadena.length());
                    bufer.newLine();
                }


            }
            bufer.close();
        } catch (IOException ex) {
            Logger.getLogger(FuncionExponencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }
}
