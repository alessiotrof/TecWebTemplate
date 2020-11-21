package it.alessio.servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SimpleServlet
 */

public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	// 
	// Metodo doGet
	//
	// Nel metodo GET i parametri vengono scritti all'interno dell'URL
	// Serve per ottenere dei dati
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cssTag = "<link rel='stylesheet' type='text/css' href='css/default.css'>";
		System.out.println("E' stato lanciato il doGet()!");
		
		// Setto il fatto che il testo della response sarà in HTML
		response.setContentType("text/html;charset=UTF-8");
		
		// Ottengo il writer
		PrintWriter pw = response.getWriter();
		
		// Includo il CSS
	    pw.println("<html>");
	    pw.println("<head><title>Metodo doGet()</title>"+cssTag+"</head>");
	    pw.println("<body>");
		
		// Fine pagina
		pw.println("</body></html>");
	}
	
	// 
	// Metodo doPost
	//
	// Nel metodo POST i parametri non sono presenti nell'URL
	// Serve per mandare dei dati
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String cssTag = "<link rel='stylesheet' type='text/css' href='css/default.css'>";
		
		System.out.println("E' stato lanciato il doPost()!");
		
		// Setto il fatto che il testo della response sarà in HTML
		response.setContentType("text/html;charset=UTF-8");
		
		// Ottengo il writer
		PrintWriter pw = response.getWriter();
		
		// Includo il CSS
	    pw.println("<html>");
	    pw.println("<head><title>Metodo doPost()</title>"+cssTag+"</head>");
	    pw.println("<body>");
		
	    // Ottengo i vari parametri passati
		String username = request.getParameter("username");
		System.out.println("Username della request: " + username);
		
		String password = request.getParameter("password");
		System.out.println("Password della request: " + password);
		
		// Ottengo il path del file
		String filePath = this.getServletContext().getRealPath("/save/savedusers.dat");    
		
		// Un boolean che mi indica se sono registrato o meno
		boolean userIsRegistred = isUserRegistred(username, password, filePath);
		System.out.println("isLogged ServletLogin: " + userIsRegistred);
		
		
		// Ottengo la sessione, la sessione è condivisa soltanto dalle chiamate di uno stesso client
		HttpSession session = request.getSession();

		
		// Controllo che l'utente sia registrato nel sito
		if(userIsRegistred) {
			
			// Metto il valore "isLogged" nella sessione
			session.setAttribute("isLogged", true);
			
		}else { // Se non sono loggato
			
			// Setto l'attributo nella sessione a FALSO
			session.setAttribute("isLogged", false);

		}

		// Faccio una Forward alla Servlet "Welcome"
		RequestDispatcher rd = request.getRequestDispatcher("servletWelcome");
        rd.forward(request, response);
		
		
		// Fine pagina
		pw.println("</body></html>");
	
	}
	
	
	private boolean isUserRegistred(String username, String password, String fileName) {
		
		FileReader fr = null;
        try {
            fr = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(fr);
        
        String line;
        try {
            while ((line = br.readLine()) != null) {
            	
            	// Controlli sulla stringa 
            	String[] parts = line.split("[:-]");
            	
            	// Ottengo username e password
            	String usernameFromFile = parts[1].trim();
            	System.out.println("Username letto dal file: " + usernameFromFile);
            	
            	String passwordFromFile = parts[3].trim();
            	System.out.println("Password letta dal file: " + passwordFromFile);
            	
            	// Controllo che siano corrette
            	if(username.equals(usernameFromFile) && password.equals(passwordFromFile)) {
            		return true;
            	}
            	
            }
            
            // Chiusura BufferedReader
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
		
		
	} 

}
