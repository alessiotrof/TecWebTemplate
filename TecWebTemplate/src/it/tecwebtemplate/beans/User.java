package it.tecwebtemplate.beans;

import java.io.Serializable;

//Si ricorda che un Java Bean ha solo:
//1) Costruttore public SENZA argomenti
//2) Metodi get public 
//3) Metodi set public
//
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    
    private String username;
    private String password;
    private int gruppo;
    
    // Costruttore public senza argomenti
    public User() {
    	
    }
    

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


	public int getGruppo() {
		return gruppo;
	}


	public void setGruppo(int gruppo) {
		this.gruppo = gruppo;
	}


    
}