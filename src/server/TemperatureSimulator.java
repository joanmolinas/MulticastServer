/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author Josep Cañellas <jcanell4@ioc.cat>
 */
public class TemperatureSimulator {
    public static final int maximumBias = 10;
    public static final double maximumVariation = 0.1; 
    int [] averageTemp = {6, 0, 7, 15, 18, 23, 30, 30, 25, 15, 12, 8};
    double temp;
    int month;
    long millis;
    Random random;

    public TemperatureSimulator() {
        Calendar calendar = GregorianCalendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        millis=System.currentTimeMillis();
        random = new Random(millis);
    }
    
    public TemperatureSimulator(int month) {
        this.month = month-1;
        temp = averageTemp[month];
        millis=System.currentTimeMillis();
        random = new Random(millis);
    }
    
    public int getTemperature(){
        long time = System.currentTimeMillis()-millis/1000;
        millis = System.currentTimeMillis();
        temp += (random.nextBoolean()?1:-1)*maximumVariation*temp*random.nextFloat();
        double dif = temp-averageTemp[month];
        if(dif>maximumBias){
            temp=averageTemp[month]+maximumBias;
        }else if(dif<-maximumBias){
            temp=averageTemp[month]-maximumBias;
        }
        return (int) temp;
    }
    
    
    
}
