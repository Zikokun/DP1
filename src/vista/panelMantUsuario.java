/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Persona;
import utilitario.Correo;
import utilitario.controladorCorreo;
import utilitario.funcionesPanelMantUsuario;

/**
 *
 * @author FranciscoMartin
 */
public class panelMantUsuario extends javax.swing.JPanel {
    
    private String usuario;
    private String contrasenha;
    private String tipoUsuario;
    Correo corr = new Correo();
    
    public panelMantUsuario() {
        initComponents();
        this.setVisible(true);
    }
    
    public panelMantUsuario(String usuario, String contrasenha, String tipoUsuario, int distinguidor) {
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipoUsuario;
        initComponents();
        this.setVisible(true);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        label8.setVisible(false);
        label9.setVisible(false);
    }
    
    private int CamposObligatorios() throws InstantiationException, IllegalAccessException {
        int cont = 0;
        String cadena = "Ingresar:";
        if (nombreCampo.getText().isEmpty()) {
            cadena += "\n Contraseña";
            cont++;
            label1.setVisible(true);
        } else {
            label1.setVisible(false);
        }
        if (apellidoPaternoCampo.getText().isEmpty()) {
            cadena += "\n Apellido Paterno";
            cont++;
            label2.setVisible(true);
        } else {
            label2.setVisible(false);
        }
        if (apellidoMaternoCampo.getText().isEmpty()) {
            cadena += "\n Apellido Materno";
            cont++;
            label3.setVisible(true);
        } else {
            label3.setVisible(false);
        }
        if (fechaNacimientoCampo.getText().isEmpty()) {
            cadena += "\n Fecha de Nacimiento";
            cont++;
            label4.setVisible(true);
        } else {
            label4.setVisible(false);
        }
        if (direccionCampo.getText().isEmpty()) {
            cadena += "\n Dirección";
            cont++;
            label5.setVisible(true);
        } else {
            label5.setVisible(false);
        }
        if (correoCampo.getText().isEmpty()) {
            cadena += "\n Correo";
            cont++;
            label6.setVisible(true);
        } else {
            label6.setVisible(false);
        }
        if (docIdentidadCampo.getText().isEmpty()) {
            cadena += "\n Documento de Identidad";
            cont++;
            label7.setVisible(true);
        } else {
            label7.setVisible(false);
        }
        if (usuarioCampo.getText().isEmpty()) {
            cadena += "\n Nombre de Usuario";
            cont++;
            label8.setVisible(true);
        } else {
            label8.setVisible(false);
        }
        if (contrasenhaCampo.getText().isEmpty()) {
            cadena += "\n Contraseña";
            cont++;
            label9.setVisible(true);
        } else {
            label9.setVisible(false);
        }
        if (cont > 0) {
            //JOptionPane.showMessageDialog(this, cadena, "Campo Obligatorio", JOptionPane.INFORMATION_MESSAGE);
        }
        return cont;
    }
    
    public panelMantUsuario(String usuario, String contrasenha, String tipoUsuario) throws InstantiationException, IllegalAccessException {
        
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipoUsuario;
        initComponents();
        this.setVisible(true);
        this.contrasenhaCampo.setVisible(false);
        this.jLabel11.setVisible(false);
        String[] datosUsuario = new String[10];
        
        funcionesPanelMantUsuario funcionesMantUsuario = new funcionesPanelMantUsuario();
        
        this.botonRegistrar.setVisible(false);
        datosUsuario = funcionesMantUsuario.devolverDatosUsuario(usuario, tipoUsuario);
        this.nombreCampo.setText(datosUsuario[0]);
        this.apellidoPaternoCampo.setText(datosUsuario[1]);
        this.apellidoMaternoCampo.setText(datosUsuario[2]);
        this.fechaNacimientoCampo.setText(datosUsuario[3]);
        this.direccionCampo.setText(datosUsuario[4]);
        this.correoCampo.setText(datosUsuario[5]);
        this.docIdentidadCampo.setText(datosUsuario[6]);
        
        if (datosUsuario[7].equals(TIPO_CLIENTE)) {
            this.tipoUsuarioComboBox.setSelectedIndex(2);
        } else if (datosUsuario[7].equals(TIPO_ADMIN)) {
            this.tipoUsuarioComboBox.setSelectedIndex(0);
        } else {
            this.tipoUsuarioComboBox.setSelectedIndex(1); //Operario
        }
        this.docIdentidadComboBox.setSelectedIndex(0);
        
        this.usuarioCampo.setText(datosUsuario[9]);
        
        funcionesMantUsuario.colocarCampoComoNoEditable(this.nombreCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.apellidoPaternoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.apellidoMaternoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.fechaNacimientoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.direccionCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.correoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.docIdentidadCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.usuarioCampo);
        
        funcionesMantUsuario.colocarComboBoxesComoNoEditable(this.tipoUsuarioComboBox);
        funcionesMantUsuario.colocarComboBoxesComoNoEditable(this.docIdentidadComboBox);
        
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        label8.setVisible(false);
        label9.setVisible(false);
    }
    
    public void enviarCorreo(Persona nuevoUsuario) {
        corr.setContrasenha("dfcljwktcrnxqulr");
        corr.setUsuarioCorreo("traslapack.packsis@gmail.com");
        corr.setAsunto("Registros de usuario en PackSis");
        corr.setMensaje("Se registro con los siguientes datos: usuario=" + nuevoUsuario.getUsuario() + " contraseña=" + nuevoUsuario.getContrasenhia() + ".");
        corr.setDestino(nuevoUsuario.getCorreo());
        corr.setNombArch("logo.png");
        corr.setRutaArch("src/recursos/logo.png");
        controladorCorreo corrCor = new controladorCorreo();
        corrCor.enviarCorreo(corr);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nombreCampo = new javax.swing.JTextField();
        apellidoPaternoCampo = new javax.swing.JTextField();
        apellidoMaternoCampo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fechaNacimientoCampo = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        tipoUsuarioComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        direccionCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        correoCampo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        docIdentidadComboBox = new javax.swing.JComboBox<>();
        docIdentidadCampo = new javax.swing.JTextField();
        botonRegistrar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        usuarioCampo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        contrasenhaCampo = new javax.swing.JTextField();
        regresarBoton = new javax.swing.JButton();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        label6 = new javax.swing.JLabel();
        label7 = new javax.swing.JLabel();
        label8 = new javax.swing.JLabel();
        label9 = new javax.swing.JLabel();

        jTextField2.setText("jTextField2");

        jLabel6.setText("jLabel6");

        jTextField7.setText("jTextField7");

        jTextField9.setText("jTextField9");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apellido Paterno");

        jLabel3.setText("Apellido Materno");

        nombreCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreCampoKeyTyped(evt);
            }
        });

        apellidoPaternoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apellidoPaternoCampoKeyTyped(evt);
            }
        });

        apellidoMaternoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apellidoMaternoCampoKeyTyped(evt);
            }
        });

        jLabel4.setText("Fecha de nacimiento(aaa-mm-dd)");

        fechaNacimientoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fechaNacimientoCampoKeyTyped(evt);
            }
        });

        jLabel5.setText("Tipo usuario");

        tipoUsuarioComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Operario", "Cliente" }));
        tipoUsuarioComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoUsuarioComboBoxActionPerformed(evt);
            }
        });

        jLabel7.setText("Dirección");

        direccionCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionCampoKeyTyped(evt);
            }
        });

        jLabel8.setText("Correo");

        correoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                correoCampoKeyTyped(evt);
            }
        });

        jLabel9.setText("Documento de identidad");

        docIdentidadComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "LE", "CE" }));

        docIdentidadCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                docIdentidadCampoKeyTyped(evt);
            }
        });

        botonRegistrar.setText("Registrar");
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });

        jLabel10.setText("Usuario");

        usuarioCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usuarioCampoKeyTyped(evt);
            }
        });

        jLabel11.setText("Contraseña");

        contrasenhaCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contrasenhaCampoKeyTyped(evt);
            }
        });

        regresarBoton.setText("Regresar");
        regresarBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regresarBotonMouseClicked(evt);
            }
        });

        label1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 0, 0));
        label1.setText("* Nombre obligatorio");
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 0, 0));
        label2.setText("* Apellido Paterno obligatorio");

        label3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 0, 0));
        label3.setText("* Apellido Materno obligatorio");

        label4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 0, 0));
        label4.setText("* Fecha de Nacimiento obligatoria");
        label4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label5.setForeground(new java.awt.Color(255, 0, 0));
        label5.setText("* Drección obligatoria");
        label5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label6.setForeground(new java.awt.Color(255, 0, 0));
        label6.setText("* Correo obligatorio");
        label6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label7.setForeground(new java.awt.Color(255, 0, 0));
        label7.setText("* Documento obligatorio");
        label7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label8.setForeground(new java.awt.Color(255, 0, 0));
        label8.setText("* Usuario obligatorio");
        label8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        label9.setForeground(new java.awt.Color(255, 0, 0));
        label9.setText("* Contraseña obligatorio");
        label9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(nombreCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(apellidoPaternoCampo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(label2))
                                .addGap(0, 29, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3)
                            .addComponent(apellidoMaternoCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(tipoUsuarioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaNacimientoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(docIdentidadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(usuarioCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label7)
                                    .addComponent(contrasenhaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(docIdentidadCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel11))))
                            .addComponent(jLabel9))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(correoCampo)
                            .addComponent(direccionCampo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label6)
                                    .addComponent(label5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(label8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonRegistrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regresarBoton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidoPaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidoMaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(label2)
                    .addComponent(label3))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechaNacimientoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label4)
                    .addComponent(label5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoUsuarioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(correoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(docIdentidadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(docIdentidadCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label7)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usuarioCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contrasenhaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonRegistrar)
                            .addComponent(regresarBoton))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label8)
                            .addComponent(label9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tipoUsuarioComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoUsuarioComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoUsuarioComboBoxActionPerformed

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        try {
            if (CamposObligatorios() == 0) {
                String nombre = nombreCampo.getText();
                String apellidoP = apellidoPaternoCampo.getText();
                String apellidoM = apellidoMaternoCampo.getText();
                String fechaNac = fechaNacimientoCampo.getText();
                String direccion = direccionCampo.getText();
                String correo = correoCampo.getText();
                String documento = docIdentidadCampo.getText();
                String usuario = usuarioCampo.getText();
                String contrasenhia = contrasenhaCampo.getText();
                
                String tipoUsuario = (String) this.tipoUsuarioComboBox.getSelectedItem();
                String tipoDocumento = "DNI";
                
                Persona usuarioNuevo = new Persona(nombre, apellidoP, apellidoM, fechaNac, direccion, correo, documento, usuario, contrasenhia);
                String mensaje = "";
                funcionesPanelMantUsuario utilitarioMantenimientos = new funcionesPanelMantUsuario();
                try {
                    if ((mensaje = utilitarioMantenimientos.DuplicadoUsuario(usuario)) == "OK") {
                        mensaje = utilitarioMantenimientos.RegistrarUsuario(usuarioNuevo, tipoUsuario);
                        JOptionPane.showMessageDialog(null, mensaje);
                        if (mensaje.equals("Usuario insertado con éxito")) {
                            enviarCorreo(usuarioNuevo);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, mensaje);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private void regresarBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regresarBotonMouseClicked
        VentanaPrincipal.pnlFondo.removeAll();
        
        if (tipoUsuario.equals(TIPO_ADMIN)) {
            PanelSim ps = new PanelSim();
            VentanaPrincipal.pnlFondo.add(ps, BorderLayout.CENTER);
        }
        VentanaPrincipal.pnlFondo.revalidate();
        VentanaPrincipal.pnlFondo.repaint();
    }//GEN-LAST:event_regresarBotonMouseClicked

    private void nombreCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreCampoKeyTyped
        char key = evt.getKeyChar();
        if (Character.isDigit(key)) {
            evt.consume();
        }
        if (nombreCampo.getText().length() >= 30) {
            evt.consume();
        }
    }//GEN-LAST:event_nombreCampoKeyTyped

    private void apellidoPaternoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoPaternoCampoKeyTyped
        char key = evt.getKeyChar();
        if (Character.isDigit(key)) {
            evt.consume();
        }
        if (apellidoPaternoCampo.getText().length() >= 30) {
            evt.consume();
        }
    }//GEN-LAST:event_apellidoPaternoCampoKeyTyped

    private void apellidoMaternoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoMaternoCampoKeyTyped
        char key = evt.getKeyChar();
        if (Character.isDigit(key)) {
            evt.consume();
        }
        if (apellidoMaternoCampo.getText().length() >= 30) {
            evt.consume();
        }
    }//GEN-LAST:event_apellidoMaternoCampoKeyTyped

    private void usuarioCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioCampoKeyTyped
        if (usuarioCampo.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_usuarioCampoKeyTyped

    private void docIdentidadCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_docIdentidadCampoKeyTyped
        char key = evt.getKeyChar();
        if (!Character.isDigit(key)) {
            evt.consume();
        }
        if (docIdentidadCampo.getText().length() >= 8) {
            evt.consume();
        }
    }//GEN-LAST:event_docIdentidadCampoKeyTyped

    private void fechaNacimientoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaNacimientoCampoKeyTyped
        char key = evt.getKeyChar();
        if (!Character.isDigit(key) && key != '-') {
            evt.consume();
        }
        if (fechaNacimientoCampo.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_fechaNacimientoCampoKeyTyped

    private void direccionCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionCampoKeyTyped
        if (direccionCampo.getText().length() >= 30) {
            evt.consume();
        }
    }//GEN-LAST:event_direccionCampoKeyTyped

    private void contrasenhaCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contrasenhaCampoKeyTyped
        if (contrasenhaCampo.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_contrasenhaCampoKeyTyped

    private void correoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_correoCampoKeyTyped
        if (correoCampo.getText().length() >= 30) {
            evt.consume();
        }
    }//GEN-LAST:event_correoCampoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidoMaternoCampo;
    private javax.swing.JTextField apellidoPaternoCampo;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JTextField contrasenhaCampo;
    private javax.swing.JTextField correoCampo;
    private javax.swing.JTextField direccionCampo;
    private javax.swing.JTextField docIdentidadCampo;
    private javax.swing.JComboBox<String> docIdentidadComboBox;
    private javax.swing.JFormattedTextField fechaNacimientoCampo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel label6;
    private javax.swing.JLabel label7;
    private javax.swing.JLabel label8;
    private javax.swing.JLabel label9;
    private javax.swing.JTextField nombreCampo;
    private javax.swing.JButton regresarBoton;
    private javax.swing.JComboBox<String> tipoUsuarioComboBox;
    private javax.swing.JTextField usuarioCampo;
    // End of variables declaration//GEN-END:variables

    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getContrasenha() {
        return contrasenha;
    }
    
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
