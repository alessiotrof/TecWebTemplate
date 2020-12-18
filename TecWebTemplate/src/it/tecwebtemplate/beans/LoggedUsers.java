package it.tecwebtemplate.beans;

import java.io.Serializable;
import java.util.ArrayList;

// Si ricorda che un Java Bean ha solo:
// 1) Costruttore public SENZA argomenti
// 2) Metodi get public 
// 3) Metodi set public
//
public class LoggedUsers implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    private ArrayList<User> loggedUsers = new ArrayList<>();
   
    
    public LoggedUsers() {
    	
    }


	public ArrayList<User> getLoggedUsers() {
		return this.loggedUsers;
	}
	
	public void setLoggedUsers(ArrayList<User> loggedUsers) {
		this.loggedUsers = loggedUsers;
	}
	
	public boolean addUser(User user) {

        for (User u : this.loggedUsers) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
            	return false;
            }
        }
        
        this.loggedUsers.add(user);
        return true;
	}
	
	public boolean removeUser(User user) {
		
		boolean result = this.loggedUsers.remove(user);
		
		return result;
		
	}
	
	public boolean isPresent(User user) {
		return this.loggedUsers.contains(user);
	}
	   
   

    
}