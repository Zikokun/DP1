/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author a20111055 - Vivian
 */
public class Cromosoma {
    public ArrayList<Gen> genes= new ArrayList<>();
    public int fitness=0;
    
    public void print(){
        for(int i=0; i<genes.size();i++){
            Gen gen=genes.get(i);
            gen.getRuta().print();
        }
    }
}
