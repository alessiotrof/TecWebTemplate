package it.alessio.servlets;

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

public class ServletWelcome extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

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

		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Ottengo la sessione, la sessione è condivisa soltanto dalle chiamate di uno stesso client
		HttpSession session = request.getSession();
		
		boolean isLogged = (boolean) session.getAttribute("isLogged");
		
		System.out.println("isLogged ServletWelcome: " + isLogged);
		
		// Se il login è avvenuto con successo
		if(isLogged) 
			pw.println("<h1>Benvenuto! Sei registrato/a nel sito!<br>Username: " + username + "<br>Password: " + password + "</h1><br>");
		else
			pw.println("<h1>Non sei registrato/a nel sito!<br>Nessun utente trovato con username: " + username + "<br>E password: " + password + "</h1><br>");

		
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.include(request, response);

		
		// Fine pagina
		pw.println("</body></html>");
	
	}


}
