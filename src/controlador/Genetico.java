/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import modelo.Ciudad;
import modelo.Cromosoma;
import modelo.Gen;
import modelo.Pedido;
import modelo.Ruta;
import modelo.Vuelo;

/**
 *
 * @author Vivian
 */
public class Genetico {
    
    private int maxPoblacion = 100; //maximo numero de soluciones posibles
    private int maxGeneraciones=30;//maximo numero de iteraciones
    private double probMutacion=0.01;//probabilidad de mutacion
    private int horaSist;
    private int diaSist;
    private String []diasSemana={"Sab","Dom","Lun","Mar","Mie","Jue","Vie"};
    private ArrayList<Ruta> universoRutas= new ArrayList<>();
    private ArrayList<Cromosoma> cromosomas=new ArrayList<>();
    
    Genetico(){
        
    }
    
    public void ejecutar(TreeMap<String,Ciudad> aeropuertos, ArrayList<Vuelo> vuelos, ArrayList<Pedido> pedidos,int hora, int dia){
        
    }
    
    public int generarPoblacion(ArrayList<Pedido> pedidos,TreeMap<String,Ciudad> ciudades){
        int fitnessTotal=0; //servira en el momento de escoger el padre y madre
        for(int i=0; i<maxPoblacion;i++){ //generamos cromosomas
            Cromosoma crom= new Cromosoma();
            /*Un cromosoma tiene tanto genes como paquetes (un pedido consta de 0 o muchos paquetes)
                existen ya que cada gen representa la ruta que tomara el paquete
            */
            for(int j=0;j<pedidos.size();j++){ 
                Pedido pedActual=pedidos.get(j);
                String origen=pedActual.getOrigen();
                String destino=pedActual.getDestino();
                ArrayList<Ruta> rutasOF=ciudades.get(origen).rutas.get(destino);//Lista de todas las posibles rutas cone se origen y destino
                Random ran=new Random();//Un random para escoger un ruta(solucion) aleatoria
                int tiempoMax=48; //tiempo maximo(horas) para envios entre continentes
                if (ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())) tiempoMax=24; //si estan en el mismo continente, el maximo es de 24 horas
                int hPedido=pedActual.getHora();
                int tTotal=50;
                /*
                A partir de aqui se considera que un pedido puede tener varios paquetes,
                pero luego de ver los archivos de pedidos del profesor, me parece que cada
                cada pedido es de un paquete no? si es asi, prodriamos obviar el FOR
                */
                for(int h=0;h<pedActual.getCant();h++){ //por paquete se genera un alelo
                    Ruta ruta=rutasOF.get(ran.nextInt(rutasOF.size())); //escogemos una ruta aleatoriamente
                    /*
                    La ruta solo se debe elegir si cumple con las condiciones de tiempo
                    de lo contrario se prueba con otra ruta, por ahora debido a que el archivo
                    de pedidos que se esta utilizando tiene pedido "imposibles" de cumplir,toda
                    esta parte ha quedado comentada
                    */
//                    while(tTotal>tiempoMax){//condicion de negocio : tiempo maximo
//                        ruta=rutasOF.get(ran.nextInt(rutasOF.size()));
//                        int hSalida=ruta.getVuelos().get(0).gethSalida();
//                        if(hSalida<hPedido) hSalida+=24;
//                        tTotal=hSalida-hPedido+ruta.getTiempo();
//                    }
                    Gen gen=new Gen();
                    gen.setRuta(ruta);
                    gen.setTiempo(ruta.getTiempo());
                    gen.setPedido(pedActual);
                    //de acuerdo a la ruta escogida, se debe actualizar las capacidades de los almacenes
                    actualizarCaps(gen,pedActual.getDia(),pedActual.getHora(),pedActual.getMin());
                    crom.genes.add(gen); //se ha generado aleatoriamente su ruta
                }
                
            }
            reiniciarCapsCiudades(ciudades);//regresar las ciudades a su capacidad COMPLETA porque se comenzara de nuevo para el siguiente cromosoma
            int fitness=calcFitness(crom);
            fitnessTotal+=fitness;
            crom.fitness=fitness;
            cromosomas.add(crom);
        }
        return fitnessTotal;
    }
    
    public void actualizarCaps(Gen gen,int diaP,int horaP, int minP){  //esta es hora y min del pedido////// dia-hora:00 / dia-hora:01
        Ruta ruta= gen.getRuta();
        int diaK=diaP;
        int cant=ruta.getVuelos().size();
        for(int i=0;i<cant;i++){
            Vuelo vuelo=ruta.getVuelos().get(i);
            Ciudad ciudadOrig=vuelo.getAeroOrig();
            Ciudad ciudadFin=vuelo.getAeroFin();
            int hSalida=vuelo.gethSalida();
            int hLlegada=vuelo.gethLlegada();
            int horaKey;
            
            //registramos su ingreso por primera vez
            if(i==0){ 
                //si la hora de salida ya paso, hay que esperar hasta el dia siguiente
                if(hSalida<horaP) hSalida+=24;
                for (int hora=horaP;hora<=hSalida;hora++){
                    horaKey=horaP%24; // porque las horas del dia van de 0 a 24 (circular)
                    if(hora!=horaP && horaKey==0) diaK++; // se paso al día siguiente
                    diaK=diaK%7; //mantener el rango de los 7 dias (circular)
                    String key=diasSemana[diaK]+"-"+horaKey+":00";
                    ciudadOrig.capTiempo.put(key,ciudadOrig.capTiempo.get(key)-1);//un espacio menos disponible
                    if(hora!=hSalida){ //el paquete todavia se va a quedar ahi por una hora mass
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTiempo.put(key2, ciudadOrig.capTiempo.get(key2)-1);
                    }
                }
            }
            //registramos su escala si la hubiera
            if(i==1){
                int hLlegadaEscala=ruta.getVuelos().get(0).gethLlegada();
                int hSalidaDeOrigen=ruta.getVuelos().get(0).gethSalida();
                int diasTrans=diasTrans(ruta.getVuelos().get(0));
                diaK=(diaK+diasTrans)%7;//se determina que dia llego a la ciudad escala
                if(hSalida<hLlegadaEscala)hSalida+=24;//si la salida es al dia siguiente
                for(int hora=hLlegadaEscala;hora<=hSalida;hora++){
                    horaKey=hora%24;
                    if(hora!=hLlegadaEscala && horaKey==0) diaK++;//se paso al dia siguiente
                    diaK=diaK%7;
                    String key=diasSemana[diaK]+"-"+horaKey+":00";
                    ciudadOrig.capTiempo.put(key, ciudadOrig.capTiempo.get(key)-1);
                    if(hora!=hSalida){
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTiempo.put(key2, ciudadOrig.capTiempo.get(key2)-1);
                    }
                }
                
            }
            
            //registramos fin del destino de paquete
            
            if(i==ruta.getVuelos().size()-1){
                int diasTrans=diasTrans(vuelo);
                diaK=(diaK+diasTrans)%7;//se determina que dia llego a la ciudad escala
                String key=diasSemana[diaK]+"-"+hLlegada+":00";
                ciudadFin.capTiempo.put(key,ciudadFin.capTiempo.get(key)-1);
            }
            
        }
    }
    
    public int diasTrans(Vuelo vuel){
        int hSalida=vuel.gethSalida();
        int hLlegada=vuel.gethLlegada();
        Ciudad ciudOrig=vuel.getAeroOrig();
        Ciudad ciudFin=vuel.getAeroFin();
        int diasTrans=(hSalida+vuel.getTiempo()+ciudFin.huso-ciudOrig.huso)/24;//es division entera?
        return diasTrans;
    }
    
    public void reiniciarCapsCiudades(TreeMap<String,Ciudad> ciudades){ // reiniciamos las ciudades a su capacidad COMPLETA
        for(Ciudad ciudad : ciudades.values()){
            ciudad.reiniciarCaps();
        }
    }
    
    public int calcFitness(Cromosoma crom){
        int fitness;
        int tiempoTotal=0;
        int espacioLibre=0;
        /*
        Por ahora la funcion fitness solo considera el tiempo del viaje pero hay que mejorarla
        tal vez considerar la distribucion de los paquetes, las capacidades libres que dejan
        y ponerle pesos
        */
        for(int i=0;i<crom.genes.size();i++){
            Gen gen=crom.genes.get(i);
            tiempoTotal+=gen.tiempo;
            //espacioLibre+=gen.espacioLibre;
        }
        //48 porque es el mayor tiempo que se puede demorar,a mayor fitness es una mejor solucion
        fitness=4*48-4*tiempoTotal/crom.genes.size();
        return fitness;
    }
}
