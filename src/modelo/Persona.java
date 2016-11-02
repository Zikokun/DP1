/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author gerson
 */
public class Persona {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String fechaNac;
    private String direccion;
    private String correo;
    private String documento;
    private String usuario;
    private String contrasenhia;
    
    public Persona(){
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
    
     public Persona(String nom, String apeP, String apeM, String feNac, String dir, String cor, String doc, String us, String con){
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenhia() {
        return contrasenhia;
    }

    public void setContrasenhia(String contrasenhia) {
        this.contrasenhia = contrasenhia;
    }
}
