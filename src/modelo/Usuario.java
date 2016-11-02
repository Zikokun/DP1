/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Camila
 */
public class Usuario {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String fechaNac;
    private String direccion;
    private String correo;
    private String documento;
    private String usuario;
    private String contrasenhia;
    
    public Usuario(){
        nombre = "";
        apellidoP = "";
        apellidoM = "";
        fechaNac = "";
        direccion = "";
        correo = "";
        documento = "";
        usuario = "";
        contrasenhia = "";
    }
    
     public Usuario(String nom, String apeP, String apeM, String feNac, String dir, String cor, String doc, String us, String con){
        nombre = nom;
        apellidoP = apeP;
        apellidoM = apeM;
        fechaNac = feNac;
        direccion = dir;
        correo = cor;
        documento = doc;
        usuario = us;
        contrasenhia = con;                
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidoP
     */
    public String getApellidoP() {
        return apellidoP;
    }

    /**
     * @param apellidoP the apellidoP to set
     */
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    /**
     * @return the apellidoM
     */
    public String getApellidoM() {
        return apellidoM;
    }

    /**
     * @param apellidoM the apellidoM to set
     */
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    /**
     * @return the fechaNac
     */
    public String getFechaNac() {
        return fechaNac;
    }

    /**
     * @param fechaNac the fechaNac to set
     */
    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasenhia
     */
    public String getContrasenhia() {
        return contrasenhia;
    }

    /**
     * @param contrasenhia the contrasenhia to set
     */
    public void setContrasenhia(String contrasenhia) {
        this.contrasenhia = contrasenhia;
    }
}
