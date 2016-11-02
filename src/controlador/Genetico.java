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
    
    public Genetico(){
        
    }
    
    public void ejecutar(TreeMap<String,Ciudad> aeropuertos, ArrayList<Vuelo> vuelos, ArrayList<Pedido> pedidos,int hora, int dia){
        int fitnessTotal=generarPoblacion(pedidos,aeropuertos);//poblacion incicial
        System.out.println("fitness Total: "+fitnessTotal);
        reproduccion(fitnessTotal);
        horaSist=hora;
        diaSist=dia;        
    }
    
    public void reproduccion(int fitnessTotal){
        Cromosoma mejorCrom=new Cromosoma();
        mejorCrom.fitness=0;
        for(int i=0;i<maxGeneraciones;i++){
            int auxFitnessTotal=0;
            ArrayList<Cromosoma> descendencia= new ArrayList<>();
            for(int j=0;j<maxPoblacion;j++){
                Random semilla=new Random();//seleccionar padre
                Random semilla2=new Random();//seleccionar madre
                int posPadre=semilla.nextInt(fitnessTotal);
                int posMadre=semilla2.nextInt(fitnessTotal);
                int encPadre=0,encMadre=0,sumaFit=0;//simulamos ruleta de seleccion de padres
                while(sumaFit<=posPadre){
                    sumaFit+=cromosomas.get(encPadre++).fitness;
                }
                sumaFit=0;
                while(sumaFit<=posMadre){
                    sumaFit+=cromosomas.get(encMadre++).fitness;
                }
                Cromosoma hijo= crossover(cromosomas.get(--encPadre),cromosomas.get(--encMadre),cromosomas.get(0).genes.size());
                mutacion(hijo);
                hijo.fitness=calcFitness(hijo);
                if(hijo.fitness>mejorCrom.fitness)mejorCrom=hijo;
                auxFitnessTotal+=hijo.fitness;
                descendencia.add(hijo);
            }
            fitnessTotal=auxFitnessTotal;
            //System.out.println("generacion-"+i+" Fitnessprom= "+fitnessTotal/maxPoblacion);
            //System.out.println("fitness Total: "+fitnessTotal);
            for(int h=0;h<maxPoblacion;h++)//reemplazo de nuevo generacion
                cromosomas.set(h, descendencia.get(h));
            
        }
        
        int tiempoTotal=0;
        for(int i=0;i<mejorCrom.genes.size();i++){
            Ruta rutasPacki=mejorCrom.genes.get(i).ruta;
            
            System.out.print("Paquete "+i+":");
            //Aeropuerto aeroPackO=rutasPacki.vuelos.get(0).getAeroOrig(); 
            //Aeropuerto aeroPackF=rutasPacki.vuelos.get(0).getAeroFin();
            //aeroPackO.setCantEspacioUsado(aeroPackO.getCantEspacioUsado()+1);// asigno espacio usado
            //aeroPackF.setCantEspacioUsado(aeroPackF.getCantEspacioUsado()+1);
            for(int j=0;j<rutasPacki.getVuelos().size();j++){
                Vuelo vuelo= rutasPacki.getVuelos().get(j);
                System.out.print(vuelo.getOrigen()+"-"+vuelo.getDestino()+"//");
            }
            System.out.println("------Tiempo: "+mejorCrom.genes.get(i).tiempo);
            tiempoTotal+=mejorCrom.genes.get(i).tiempo;
            
        }
        System.out.println("Tiempo total de entrega de paquetes: "+tiempoTotal);
        
    }
    
    public void mutacion(Cromosoma crom){
        if(Math.random()<=probMutacion){ //prob Mutacion
            Random paqueteRand=new Random();
            Random rutaRand=new Random();
            int cromNumero=rutaRand.nextInt(maxPoblacion);//Elegimos otro cromosoma de la poblacion
            int aleloNumero=paqueteRand.nextInt(crom.genes.size());//elegimos un gen de ese cromosoma
            crom.genes.set(aleloNumero,cromosomas.get(cromNumero).genes.get(aleloNumero));//hacemos la mutacion
        }
    }
    
    public Cromosoma crossover(Cromosoma padre, Cromosoma madre, int nPaquetes){
        Cromosoma hijo = new Cromosoma();
        int posIni=(int) (Math.random()*nPaquetes);
        int aux=posIni;
        int posFin=(int) (Math.random()*nPaquetes);
        if(posIni>posFin){
            posIni=posFin;
            posFin=aux;
        }
        for(int i=0;i<nPaquetes;i++){ //inicializamos el hijo con genes de la madre
            hijo.genes.add(madre.genes.get(i));
        }
        for(int i=0;i<nPaquetes;i++){
            if(i<posIni||i>posFin){
                hijo.genes.set(i, padre.genes.get(i));
            }
        }
        return hijo;
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
                    gen.setTiempo(ruta.getTiempo()); //horaSalidaVuelo - horaPEdido + tiemporuta
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
                int diasTrans=(hSalida+ruta.getVuelos().get(i).getTiempo()+
                         ruta.getVuelos().get(i).getAeroFin().huso
                         -ruta.getVuelos().get(i).getAeroOrig().huso)/24;
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
