/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1_gui_alg;
import java.util.ArrayList;
import java.util.TreeMap;
import modelo.Ciudad;
import modelo.Lectura;
import modelo.Pedido;
import modelo.Vuelo;
import vista.Nv;
import vista.VentanaPrincipal;
/**
 *
 * @author alulab
 */
public class Test1_gui_alg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Nv nv= new Nv();
        nv.setVisible(true);
        
        //principal();
        
        //VentanaPrincipal principal=new VentanaPrincipal();
        //principal.setVisible(true);
        
    }
    
    public static void principal(){
        int hora,dia;
        Lectura lector= new Lectura();
        TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
        ArrayList<Vuelo> vuelos=new ArrayList<>();
        ArrayList<Pedido> pedidos=new ArrayList<>();
        lector.leerArchivos("Data/_aeropuertos.OACI.txt", "Data/_plan_vuelo.txt",
                "Data/_pedidos_04-10-2-2016.txt", "Data/_husos_horarios.txt", vuelos, ciudades, pedidos);
        
        
    }
    
    public static void asignarTipoVuelo(ArrayList<Vuelo> vuelos, TreeMap<String, Ciudad> ciudades){
        int nVuelos=vuelos.size();
        int nAeros=ciudades.size();
        int nOrig=0, nFin=0;
        for(int i=0; i<nVuelos;i++){
            Vuelo vueloActual=vuelos.get(i);
            String origen=vueloActual.getOrigen();
            String destino=vueloActual.getDestino();
            if(ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())){
                vueloActual.setTipoVuelo("Continental");
                vueloActual.setTiempo(12);
            }
            else{
                vueloActual.setTipoVuelo("Intercontinental");
                vueloActual.setTiempo(24);
            }
            if(!ciudades.get(origen).vecinos.contains(destino)) 
                ciudades.get(origen).vecinos.add(destino);//agrego vecinos
            ciudades.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto
            vuelos.get(i).setAeroOrig(ciudades.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(ciudades.get(destino)); // agrego aeropuerto fin 
        }
    }
}
