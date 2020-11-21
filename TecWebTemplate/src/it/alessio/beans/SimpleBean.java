package it.alessio.beans;

import java.util.Calendar;

// Semplice Java Bean che mi permette di stampare l'ora
//
// Si ricorda che un Java Bean ha solo:
// 1) Costruttore public
// 2) Metodi get public 
// 3) Metodi set public
// 
public class SimpleBean {
	
    private int hours;
    private int minutes;
    
    
    public SimpleBean() {
        Calendar now = Calendar.getInstance();
        this.hours = now.get(Calendar.HOUR_OF_DAY);
        this.minutes = now.get(Calendar.MINUTE);
    }
    

    public int getHours() {
        return hours;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
}
