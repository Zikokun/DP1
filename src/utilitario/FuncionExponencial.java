/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesVentanaPrincipal.TIPO_ADMIN;
import static constantes.constantesVentanaPrincipal.TIPO_CLIENTE;
import static constantes.constantesVentanaPrincipal.TIPO_OPERARIO;
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
import java.util.Random;

//2.71828

public class FuncionExponencial {
    public int CalcularFuncion(double X, double Y, double Z, int tipo, int vuelta) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        int value;
        int i=vuelta, j=0, number=0, rastreo=0, cont=0, limite;
        if(tipo==0) limite=3;
        else limite=10;
        Random r = new Random();
        funcionesPanelCrearEnvio utilitarioPanelCrearEnvio = new funcionesPanelCrearEnvio();
        for(cont=0;cont<limite;cont++){
            double e=exp(Y*cont);
            value =(int) Math.round( X * exp(Y*cont));
            rastreo = utilitarioPanelCrearEnvio.GetLastNumeroRastreo() + 1;
            for(j=0;j<value;j++){
                number = r.nextInt(39)+4;
                String cadena = utilitarioPanelCrearEnvio.CrearEnvioExponencial((int) Math.round(Z), number, Integer.toString(rastreo+j), tipo, i);
            }
            System.out.println("cantidad: " + value + " vuelta: " + cont);
            i++;
        }
        return i;
    }
}
