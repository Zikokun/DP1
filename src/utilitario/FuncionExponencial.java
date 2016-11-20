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
    public void CalcularFuncion(double X, double Y, double Z) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        double value;
        int i=1, j=0, number=0, rastreo=0;
        Random r = new Random();
        funcionesPanelCrearEnvio utilitarioPanelCrearEnvio = new funcionesPanelCrearEnvio();
        while(true){
            value = X * exp(Y*i);
            for(j=0;j<(int) Math.round(value);j++){
                number = r.nextInt(43);
                rastreo = utilitarioPanelCrearEnvio.GetLastNumeroRastreo() + 1;
                utilitarioPanelCrearEnvio.CrearEnvioExponencial((int) Math.round(Z), number, Integer.toString(rastreo));
            }
            i++;
        }
    }
}
