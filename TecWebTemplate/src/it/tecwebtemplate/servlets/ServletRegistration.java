package it.tecwebtemplate.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.tecwebtemplate.beans.User;
import it.tecwebtemplate.beans.UserDB;


public class ServletRegistration extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//
	// Metodo doGet
	//
	// Nel metodo GET i parametri vengono scritti all'interno dell'URL
	// Serve per ottenere dei dati
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cssTag = "<link rel='stylesheet' type='text/css' href='css/default.css'>";
		System.out.println("E' stato lanciato il doGet()!");

		// Setto il fatto che il testo della response sarà in HTML
		response.setContentType("text/html;charset=UTF-8");

		// Ottengo il writer
		PrintWriter pw = response.getWriter();

		// Includo il CSS
		pw.println("<html>");
		pw.println("<head><title>Metodo doGet()</title>" + cssTag + "</head>");
		pw.println("<body>");

		pw.println("Metodo doGet!");

		// Fine pagina
		pw.println("</body></html>");
	}

	//
	// Metodo doPost
	//
	// Nel metodo POST i parametri non sono presenti nell'URL
	// Serve per mandare dei dati
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cssTag = "<link rel='stylesheet' type='text/css' href='css/default.css'>";

		System.out.println("E' stato lanciato il doPost()!");

		// Setto il fatto che il testo della response sarà in HTML
		response.setContentType("text/html;charset=UTF-8");

		// Ottengo il writer
		PrintWriter pw = response.getWriter();

		// Includo il CSS
		pw.println("<html>");
		pw.println("<head><title>Metodo doPost()</title>" + cssTag + "</head>");
		pw.println("<body>");

		// Ottengo i vari parametri passati
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String education = request.getParameter("education");
		String residence = request.getParameter("residence");

		// Ottengo tutte le lingue selezionate dall'utente nella tendina
		String[] languages = request.getParameterValues("languages");

		// Scrivo in HTML i risultati che ho catturato
		pw.println("<h1>Riepilogo dati registrazione</h1>");
		pw.println("<br>Il tuo username è: " + username);
		pw.println("<br>Il tuo nome è: " + name);
		pw.println("<br>Il tuo cognome è: " + surname);
		pw.println("<br>La tua password è: " + password);
		pw.println("<br>Il tuo grado di istruzione è: " + education);
		pw.println("<br>La tua residenza è: " + residence);
		pw.println("<br>E parli " + languages.length + " lingue diverse, ovvero: ");

		// Stampo, uno ad uno, le varie lingue scelte dall'utente
		for (int i = 0; i < languages.length; i++) {
			pw.println("<br>" + languages[i]);
		}

		// Ottengo il database dal context
		ServletContext context = getServletContext();

		UserDB db = (UserDB) context.getAttribute("database");

		// Controllo se esiste già il database nel Context
		if (db != null) {

			pw.println("<br><br><b>Il database è già stato correttamente inizializzato nella pagina principale! Successo!</b>");

			User u = new User();
			u.setUsername(username);
			u.setPassword(password);

			// Controllo se l'utente è registrato o meno
			if (db.checkUserAndPassword(u)) {
				pw.println("<b>L'utente " + u.getUsername() + " è già registrato nel database!</b>");
			} else {

				// Scrivo in HTML i risultati che ho catturato
				pw.println("<br><br>Il tuo username è: " + username);
				pw.println("<br>La tua password è: " + password);

				// Aggiungo l'utente al database
				db.addUser(u);

				pw.println("<br><br><b>Ti sei correttamente registrato! Esegui ora il login qui sotto:</b>");

				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);

			}
		} else {
			pw.println("<b>Il database non è stato correttamente inizializzato! Impossibile procedere con la registrazione!</b>");
		}

		
		pw.println("<br><br><a href='index.jsp'>Torna alla pagina principale</a>");
		
		// Fine pagina
		pw.println("</body></html>");

	}

}
