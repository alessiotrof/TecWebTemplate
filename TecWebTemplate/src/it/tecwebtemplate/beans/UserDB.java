package it.tecwebtemplate.beans;


import java.io.Serializable;
import java.util.ArrayList;

// Si ricorda che un Java Bean ha solo:
// 1) Costruttore public SENZA argomenti
// 2) Metodi get public 
// 3) Metodi set public
//
public class UserDB implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private ArrayList<User> registredUsers = new ArrayList<>();

    
    // Vuoto
    public UserDB() {
    	
    }
    
    
    public ArrayList<User> getRegistredUsers() {
        return this.registredUsers;
    }

    public void setRegistredUsers(ArrayList<User> registredUsers) {
        this.registredUsers = registredUsers;
    }

    public boolean addUser(User user) {
    	
        for (User u : this.registredUsers) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
            	return false;
            }
        }
        
        this.registredUsers.add(user);
        return true;
    }


    public boolean isPresent(User user) {
    	return this.registredUsers.contains(user);
    }

    
    public int getUserGroup(String username, String password) {
    	
    	for (User u : this.registredUsers) {
    		if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
    			return u.getGruppo();
    		}
    	}
    	
    	return -1;
    	
    }
    
    public boolean checkUserAndPassword(User user) {
    	
        for (User u : this.registredUsers) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername()) && u.getPassword().equals(user.getPassword()))
            	return true;
        }
        
        return false;
    }
    
    
    public boolean checkWrongPassword(User user) {
    	
        for (User u : this.registredUsers) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername()) && !u.getPassword().equals(user.getPassword()))
            	return true;
        }
        
        return false;
    }
    

    public User getUser(String username) {
    	
        for (User u : this.registredUsers) {
            if (u.getUsername().equalsIgnoreCase(username))
            	return u;
        }
        
        return null;
    }

}