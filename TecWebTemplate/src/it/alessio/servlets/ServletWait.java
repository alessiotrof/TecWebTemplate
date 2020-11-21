package it.alessio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class ServletWait extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	// Service è il primo metodo che viene invocato dalla Servlet
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		// Ottengo il parametro "wait" dalla richiesta (passato attravero ?wait=5)
		int wait = Integer.parseInt(request.getParameter("wait"));
		
		PrintWriter out = response.getWriter();
		
		out.write("Ecco il risultato");
		
		for (int i = 0 ; i < wait ; i++ ) { 
			out.append("!"); 
			try {
				// Metto la Servlet in Sleep per "wait" secondi
				Thread.sleep(1000); 
			}	
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.write(" (Attesa = " + wait + " secondi)");
	}

}
