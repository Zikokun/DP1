/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1_gui_alg;
import controlador.Genetico;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import modelo.Ciudad;
import modelo.Lectura;
import modelo.Pedido;
import modelo.Ruta;
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
        //Nv nv= new Nv();
        //nv.setVisible(true);
        
        //principal();
        
        VentanaPrincipal principal=new VentanaPrincipal("admin");
        principal.setVisible(true);
        
    }
    
    public static void principal(){
        int hora,dia;
        Lectura lector= new Lectura();
        TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
        ArrayList<Vuelo> vuelos=new ArrayList<>();
        ArrayList<Pedido> pedidos=new ArrayList<>();
        lector.leerArchivos("src/recursos/_aeropuertos.OACI.txt", "src/recursos/_plan_vuelo.txt",
                "src/recursos/_pedidos_04-10-2016.txt", "src/recursos/_husos_horarios.txt", vuelos, ciudades, pedidos);
        asignarTipoVuelo(vuelos,ciudades);
        generarRutas(ciudades);
//        for(Pedido item:pedidos)
//            item.print();
//        imprimirAeros(ciudades);
        Calendar calendario=Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);
        
        Genetico algoritmo=new Genetico();
        algoritmo.ejecutar(ciudades, vuelos, pedidos, hora, dia);        
    }
    
    public static void generarRutas(TreeMap<String,Ciudad> ciudades){
        int tEspera;
        int tiempoRuta;
        for(Ciudad ciudad: ciudades.values()){
            ArrayList<Vuelo>vuelos=ciudad.vuelos;
            int cantVuelos=vuelos.size();
            for(int i=0;i<cantVuelos;i++){
                String destino=vuelos.get(i).getDestino();
                if(!ciudad.rutas.containsKey(destino)){ // si todavia no tiene ninguna ruta a ese destino
                   ArrayList<Ruta> rutas=new ArrayList<>();
                   rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                   ciudad.rutas.put(destino, rutas);
                }
                else{
                    ArrayList<Ruta> rutas=ciudad.rutas.get(destino);
                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                    ciudad.rutas.put(destino, rutas);
                }
                
                //caso con escala
                Ciudad ciudadIntermedia=vuelos.get(i).getAeroFin();
                for(int j=0;j<ciudadIntermedia.vuelos.size();j++){
                    Vuelo vuelo2=ciudadIntermedia.vuelos.get(j);
                    String destino2=vuelo2.getDestino();
                    if (ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) continue;
                    tEspera=vuelo2.gethSalida()-vuelos.get(i).gethLlegada();
                    if(tEspera<0) tEspera+=24;
                    tiempoRuta=vuelos.get(i).getTiempo()+vuelo2.getTiempo()+tEspera;
                    if(tiempoRuta>48) continue; // si se demora más de 48 horas, no tomar en cuenta
                    if(!ciudad.rutas.containsKey(destino2)){ // si todavia no tiene ninguna ruta ese destino
                        ArrayList<Ruta> rutas= new ArrayList<>();
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2, rutas);
                    }
                    else{
                        ArrayList<Ruta> rutas=ciudad.rutas.get(destino2);
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2,rutas);
                    }
                    //System.out.println(tiempoRuta);
                }
            }
        }        
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
                vuelos.get(i).setTipoVuelo("Continental");
                vuelos.get(i).setTiempo(12);
            }
            else{
                vuelos.get(i).setTipoVuelo("Intercontinental");
                vuelos.get(i).setTiempo(24);
            }
            if(!ciudades.get(origen).vecinos.contains(destino)) 
                ciudades.get(origen).vecinos.add(destino);//agrego vecinos
            ciudades.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto
            vuelos.get(i).setAeroOrig(ciudades.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(ciudades.get(destino)); // agrego aeropuerto fin 
        }
    }
    
    public static void imprimirAeros(TreeMap<String,Ciudad> aeropuertos){
        for(Ciudad item:aeropuertos.values())
            System.out.println(item.getCiudad());
    }
}
