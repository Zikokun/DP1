/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import javax.swing.JMenu;

/**
 *
 * @author gerson
 */
public class funcionesVentanaPrincipal {
    public static void esconderMenu(JMenu menu){
        menu.setEnabled(false);
        menu.setVisible(false);
    }
}
