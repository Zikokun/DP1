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
    
    private int maxPoblacion = 10; //maximo numero de soluciones posibles
    private int maxGeneraciones=30;//maximo numero de iteraciones
    private double probMutacion=0.01;//probabilidad de mutacion
    private int horaSist;
    private int diaSist;
    private String []diasSemana={"Sab","Dom","Lun","Mar","Mie","Jue","Vie"};
    private ArrayList<Ruta> universoRutas= new ArrayList<>();
    private ArrayList<Cromosoma> cromosomas=new ArrayList<>();
    private Cromosoma mejorCrom=new Cromosoma();
    int maxIntentos=50;
    private String mensaje;
    public Genetico(){
        
    }
    
    public void ejecutar(TreeMap<String,Ciudad> aeropuertos, ArrayList<Vuelo> vuelos, ArrayList<Pedido> pedidos,int hora, int dia, String mensaje){
        int fitnessTotal=generarPoblacion(pedidos,aeropuertos,vuelos);//poblacion incicial
        System.out.println("fitness Total: "+fitnessTotal);
        this.mensaje = "";
        mensaje = reproduccion(fitnessTotal);
        horaSist=hora;
        diaSist=dia;        
    }
       
    public String reproduccion(int fitnessTotal){
        //Cromosoma mejorCrom=new Cromosoma();
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
                if(hijo.fitness>getMejorCrom().fitness)mejorCrom=hijo;
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
        for(int i=0;i<getMejorCrom().genes.size();i++){
            
            Ruta rutasPacki=getMejorCrom().genes.get(i).ruta;
            
            System.out.print("Paquete "+i+":");
            mensaje = "Paquete "+i+":";
            for(int j=0;j<rutasPacki.getVuelos().size();j++){
                Vuelo vuelo= rutasPacki.getVuelos().get(j);
                System.out.print(vuelo.getOrigen()+"-"+vuelo.getDestino()+"//");
                mensaje+=vuelo.getOrigen()+"-"+vuelo.getDestino()+"//";
            }
            System.out.println("------Tiempo: "+mejorCrom.genes.get(i).tiempo/60+" horas");
            mensaje+="------Tiempo: "+getMejorCrom().genes.get(i).tiempo/60+" horas\n";
            tiempoTotal+=getMejorCrom().genes.get(i).tiempo;
        }
        System.out.println("Tiempo total de entrega de paquetes: "+tiempoTotal+"\n");
        mensaje+="Tiempo total de entrega de paquetes: "+tiempoTotal+"\n";
        return mensaje;
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
    
    public int generarPoblacion(ArrayList<Pedido> pedidos,TreeMap<String,Ciudad> ciudades,ArrayList<Vuelo> vuelos){
        //System.out.println("En generar Poblacion");
        int fitnessTotal=0; //servira en el momento de escoger el padre y madre
        for(int i=0; i<maxPoblacion;i++){ //generamos cromosomas
            // System.out.println("En generar Poblacion");
            Cromosoma crom= new Cromosoma();
            /*Un cromosoma tiene tanto genes como paquetes (un pedido consta de 0 o muchos paquetes)
                existen ya que cada gen representa la ruta que tomara el paquete
            */
            for(int j=0;j<pedidos.size();j++){ 
                Pedido pedActual=pedidos.get(j);
                String origen=pedActual.getOrigen();
                String destino=pedActual.getDestino();
               // System.out.println("Origen: "+origen+" Destino: "+destino);
                ArrayList<Ruta> rutasOF=ciudades.get(origen).rutas.get(destino);//Lista de todas las posibles rutas con su origen y destino
                Random ran=new Random();//Un random para escoger un ruta(solucion) aleatoria
                int tiempoMax=48; //tiempo maximo(horas) para envios entre continentes
                if (ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())) tiempoMax=24; //si estan en el mismo continente, el maximo es de 24 horas
                int hPedido=pedActual.getHora();
                int mPedido=pedActual.getMin();
                int dPedido=pedActual.getDiaSemana();
                int tTotal=50,k=0;
                /*
                A partir de aqui se considera que un pedido puede tener varios paquetes,
                pero luego de ver los archivos de pedidos del profesor, me parece que cada
                cada pedido es de un paquete no? si es asi, prodriamos obviar el FOR
                */
                for(int h=0;h<pedActual.getCant();h++){ //por paquete se genera un alelo
                    Ruta ruta=rutasOF.get(ran.nextInt(rutasOF.size())); //escogemos una ruta aleatoriamente
                    int hSalida=ruta.getVuelos().get(0).gethSalida();
                    //verificamos si hay capacidad en el almacen y el avion con esa ruta
                    if(hSalida<hPedido ||(hSalida==hPedido && mPedido!=0)) hSalida+=24;
                    int tiempTotal=(hSalida-hPedido+ruta.getTiempo())*60-mPedido;
                    
                    while((tiempTotal>tiempoMax*60 || 
                            actualizarCaps(ruta,dPedido,hPedido,mPedido)!=1 ||
                            verificarCapsAvion(ruta,dPedido,hPedido,mPedido)!=1) && 
                            k<maxIntentos)
                            {
                        ruta=rutasOF.get(ran.nextInt(rutasOF.size())); //escogemos otra ruta aleatoriamente
                        hSalida=ruta.getVuelos().get(0).gethSalida();
                        if(hSalida<hPedido ||(hSalida==hPedido && mPedido!=0)) hSalida+=24;
                        tiempTotal=(hSalida-hPedido+ruta.getTiempo())*60-mPedido;
                        k++;
                    }
                    
                    hSalida=ruta.getVuelos().get(0).gethSalida();
                    //System.out.println("Hora Salida Vuelo: "+hSalida);
                    if(hSalida<hPedido ||(hSalida==hPedido && mPedido!=0)) hSalida+=24;
                    copiarCapsVuelosYAlmacenes(vuelos,ciudades);//actualizar capacidades con los nuevos paquetes enviados
                   
                    Gen gen=new Gen();
                    gen.setRuta(ruta);
                    //System.out.println("hSalida: "+hSalida+" hPedido: "+hPedido+"mPedido: "+mPedido+" tiempoRuta: "+ruta.getTiempo());
                    gen.setTiempo((hSalida-hPedido+ruta.getTiempo())*60-mPedido); //el tiempo se toma en minutos
                    gen.setPedido(pedActual);
                    //System.out.println("Tiempo Gen "+i+"."+j+" :"+gen.tiempo);
                    //de acuerdo a la ruta escogida, se debe actualizar las capacidades de los almacenes
                    
                    crom.genes.add(gen); //se ha generado aleatoriamente su ruta
                }
                
            }
            for(Vuelo vuelo:vuelos){ //Es un nuevo cromosoma asi que inicializamos los vuelos a su maxima capacidad
                vuelo.setearCaps();
            }
            reiniciarCapsCiudades(ciudades);//regresar las ciudades a su capacidad COMPLETA porque se comenzara de nuevo para el siguiente cromosoma
            int fitness=calcFitness(crom);
            //System.out.println("Fitness Crom "+ i+" :"+fitness);
            fitnessTotal+=fitness;
            crom.fitness=fitness;
            cromosomas.add(crom);
        }
        return fitnessTotal;
    }
    
    public void copiarCapsVuelosYAlmacenes(ArrayList<Vuelo> vuelos,TreeMap<String,Ciudad> ciudades){
        for(Vuelo item:vuelos){
            item.copiarDesdeCapAux();
        }
        for(Ciudad ciudad : ciudades.values()){
            ciudad.copiarDesdeCapAux();
        }
    }
    
    public int verificarCapsAvion(Ruta ruta,int diaP,int horaP, int minP){
        ArrayList<Vuelo> vuelos=ruta.getVuelos();
        int diaLlegadaEscala=0;
        for(int i=0;i<vuelos.size();i++){
            Vuelo vueloActual=vuelos.get(i);
            int hSalida=vueloActual.gethSalida();
            int espacioLibre=0,dia;
            vueloActual.copiarACapAux();
            if(i==0){
                if(hSalida<horaP || (hSalida==horaP&&minP!=0)){ //el paquete se va a en el vuelo del dia siguiente
                    dia=(diaP+1)%7;
                    espacioLibre=vueloActual.getCapTiempoAux().get(dia)-1;
                    vueloActual.getCapTiempoAux().set(dia, espacioLibre);
                    diaLlegadaEscala=dia;
                }else if(hSalida>horaP||(hSalida==horaP&&minP==0)){// el paquete se va el mismo dia que llega
                    dia=diaP;   
                    espacioLibre=vueloActual.getCapTiempoAux().get(dia)-1;
                    vueloActual.getCapTiempoAux().set(dia, espacioLibre);
                    diaLlegadaEscala=dia;
                }
                if (espacioLibre<0)return 0;
            }
            if(i==1){
                int horaL=vuelos.get(0).gethLlegada(),minL=vuelos.get(0).getmLlegada(),diaL;
                int diasTranscurridos=diasTrans(vuelos.get(0));
                diaLlegadaEscala+=diasTranscurridos;
                if(hSalida<horaL || (hSalida==horaL&&minL!=0)){ //el paquete se va a en el vuelo del dia siguiente
                    dia=(diaLlegadaEscala+1)%7;
                    espacioLibre=vueloActual.getCapTiempoAux().get(dia)-1;
                    vueloActual.getCapTiempoAux().set(dia, espacioLibre);
                }else if(hSalida>horaL||(hSalida==horaL&&minL==0)){// el paquete se va el mismo dia que llega
                    dia=diaLlegadaEscala;   
                    espacioLibre=vueloActual.getCapTiempoAux().get(dia)-1;
                    vueloActual.getCapTiempoAux().set(dia, espacioLibre);
                }
                if (espacioLibre<0)return 0;
            }            
        }
        return 1;
    }
    
    public int actualizarCaps(Ruta ruta,int diaP,int horaP, int minP){  //esta es hora y min del pedido////// dia-hora:00 / dia-hora:01
        //System.out.println("En actualizarCaps");
        int diaK=diaP,espacioLibre=100;
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
                ciudadOrig.copiarACapAux();
                //si la hora de salida ya paso, hay que esperar hasta el dia siguiente
                if(hSalida<horaP ||(hSalida==horaP && minP!=0)) hSalida+=24;
                int primeraVez=1;
                if(minP==0)primeraVez=0;
                for (int hora=horaP;hora<=hSalida;hora++){
                    horaKey=hora%24; // porque las horas del dia van de 0 a 24 (circular)
                    if(hora!=horaP && horaKey==0) diaK++; // se paso al día siguiente
                    diaK=diaK%7; //mantener el rango de los 7 dias (circular)
                    if(primeraVez==0){//si el paquete no acaba de llegar o si ha llegado en una hora exacta
                        String key=diasSemana[diaK]+"-"+horaKey+":00";
                        ciudadOrig.capTiempoAux.put(key,ciudadOrig.capTiempoAux.get(key)-1);//un espacio menos disponible
                        espacioLibre=ciudadOrig.capTiempoAux.get(key);
                    }
                    else{primeraVez=0;}
                    if(hora!=hSalida){ //el paquete todavia se va a quedar ahi por una hora mass
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTiempoAux.put(key2, ciudadOrig.capTiempoAux.get(key2)-1);
                        espacioLibre=ciudadOrig.capTiempoAux.get(key2);
                    }
                }
            }
            //registramos su escala si la hubiera
            if(i==1){
                ciudadOrig.copiarACapAux();
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
                    espacioLibre=ciudadOrig.capTiempoAux.get(key);
                    if(hora!=hSalida){
                        String key2=diasSemana[diaK]+"-"+horaKey+":01";
                        ciudadOrig.capTiempoAux.put(key2, ciudadOrig.capTiempoAux.get(key2)-1);
                        espacioLibre=ciudadOrig.capTiempoAux.get(key2);
                    }
                }
                
            }
            
            //registramos fin del destino de paquete
            
            if(i==ruta.getVuelos().size()-1){
                ciudadFin.copiarACapAux();
                int diasTrans=(hSalida+ruta.getVuelos().get(i).getTiempo()+
                         ruta.getVuelos().get(i).getAeroFin().huso
                         -ruta.getVuelos().get(i).getAeroOrig().huso)/24;
                diaK=(diaK+diasTrans)%7;//se determina que dia llego a la ciudad escala
                String key=diasSemana[diaK]+"-"+hLlegada+":00";
                ciudadFin.capTiempoAux.put(key,ciudadFin.capTiempoAux.get(key)-1);
                espacioLibre=ciudadFin.capTiempoAux.get(key);
            }
            if(espacioLibre<0)return 0;
        }
        return 1;
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
        fitness=48*60-tiempoTotal/crom.genes.size();
        return fitness;
    }

    /**
     * @return the mejorCrom
     */
    public Cromosoma getMejorCrom() {
        return mejorCrom;
    }
}
