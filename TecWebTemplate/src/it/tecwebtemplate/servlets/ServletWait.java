package it.tecwebtemplate.servlets;

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

		// Ottengo il parametro "wait" dalla richiesta (passato "?wait=5")
		int wait = Integer.parseInt(request.getParameter("wait"));

		PrintWriter out = response.getWriter();

		out.write("Ecco il risultato");
		
		// Ciclo di attesa, per ogni secondo passato, fa una "sleep(1000)"
		for (int i = 0; i < wait; i++) {
			
			// Intanto "appendo" all'output un "!" per ogni secondo passato
			out.append("!");
			
			try {
				// Metto la Servlet in Sleep per "wait" secondi
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.write(" (Attesa = " + wait + " secondi)");
		
		out.println("<br><br><a href='index.jsp'>Torna alla pagina principale</a>");
	}

}
