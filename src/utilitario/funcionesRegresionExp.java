/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesRegresionExp {
    private double[] xValores;
    private double[] yValores;
    private int tamanho;
    private Double sumX = 0.00;
    private Double sumX2 = 0.00;
    private Double sumY = 0.00;
    private Double sumY2 = 0.00;
    private Double sumXY = 0.00;
    private double coefRegB=0.0;
    private double coefRegA=0.0;
    private double coefDeter=0.0;
    private double coefCorr=0.0;
    private double error_std=0.0;
    
    public funcionesRegresionExp(double[] xArr,double[]yArr){
            xValores=xArr;
            yValores=yArr;
            tamanho=xArr.length;
    }
    public double[] calcExpValores(){
        double[] resp={0.0,0.0,0.0,0.0,0.0};
        
        for(int i=0;i<tamanho;i++)
		{
			sumX = sumX + xValores[i];
			sumX2 = sumX2 + Math.pow(xValores[i], 2);
			
			// exponential
			sumY = sumY + Math.log(yValores[i]);
			sumY2 = sumY2 + Math.pow(Math.log(yValores[i]), 2);
			sumXY = sumXY + (xValores[i]*(Math.log(yValores[i])));
			
			
        }
        coefRegB=0.0;
        coefRegB=((tamanho*sumXY) - (sumX*sumY))/(tamanho*sumX2 - (sumX*sumX));
        
        coefRegA=0.0;
        coefRegA = Math.pow(Math.E, (sumY - (coefRegB*sumX))/tamanho);
        
        coefDeter=0.0;
        Double c = 0.00;	// numerator
        Double d = 0.00;	// denominator
        c = (coefRegB)*(sumXY - sumX*sumY/tamanho);
        d = sumY2 - (sumY*sumY)/tamanho;
        coefDeter=c/d;
        
        coefCorr = 0.00;
	if(coefDeter > 0){
            coefCorr = Math.sqrt(coefDeter);
	} else {
            coefCorr = 0.00;
        }
        
        error_std=0.0;
        error_std=Math.sqrt((d-c)/(tamanho-2));
        
        resp[0]=coefRegA;
        resp[1]=coefRegB;
        resp[2]=coefDeter;
        resp[3]=coefCorr;
        resp[4]=error_std;
        
        return resp;
    }

}
