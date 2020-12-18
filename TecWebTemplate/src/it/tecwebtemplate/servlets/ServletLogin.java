package it.tecwebtemplate.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.tecwebtemplate.beans.LoggedUsers;
import it.tecwebtemplate.beans.User;
import it.tecwebtemplate.beans.UserDB;

public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//
	// Metodo doPost
	//
	// Nel metodo POST i parametri non sono presenti nell'URL
	// Serve per mandare dei dati
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Ottengo il database dal context
		ServletContext context = getServletContext();

		// Per ottenere i tentativi rimasti
		HttpSession session = request.getSession();
		
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


		UserDB db = (UserDB) context.getAttribute("database");
		Integer tentativiRimasti = (Integer) session.getAttribute("tentativi");

		// Se non esiste nella sessione l'attributo dei tentativi rimasti
		if (tentativiRimasti == null) {
			session.setAttribute("tentativi", Integer.valueOf(3));
			tentativiRimasti = (Integer) session.getAttribute("tentativi");
		}

		// Controllo se esiste già il database nel Context
		if (db != null) {
			pw.println("<b>Dastabase ottenuto correttamente dal Context!</b>");
			
			// Creo il nuovo utente e setto Username e Password
			User u = new User();
			u.setUsername(username);
			u.setPassword(password);


			// Se l'utente è presente nel Database, ex metodo "isPresent()"
			if (db.checkUserAndPassword(u)) {

				pw.println("<br><b>Bentornato " + u.getUsername() + "!</b>");
				
				// Setto l'utente loggato nel Context
				session.setAttribute("userLogged", u);
				
				// Aggiungo l'utente alla lista degli utenti loggati
				LoggedUsers loggedUsers = (LoggedUsers)context.getAttribute("loggedUsers");
				loggedUsers.addUser(u);
				context.setAttribute("loggedUsers", loggedUsers);

			} else {

				// Se ho ancora dei tentativi a disposizione per effettuare il login
				if (tentativiRimasti.intValue() > 1) {
					
					// Bisogna fare questa porcata per modificare un Integer
					tentativiRimasti = Integer.valueOf(tentativiRimasti.intValue() - 1);

					pw.println("<b>Nome utente o password errata!</b>");
					pw.println("<b>Tentativi rimasti: </b>" + tentativiRimasti.intValue());
					
					// Setto i nuovi tentativi rimasti decrementati
					session.setAttribute("tentativi", tentativiRimasti);
					
				} else {
					pw.println("<b>Hai terminato i tentativi a disposizione per un corretto login! Non puoi procedere!</b>");
				}

			}
		} else {
			pw.println("<b>Il database non è stato ancora inizializzato! Non puoi procedere con il login!</b>");
		}

		
		pw.println("<br><br><a href='index.jsp'>Torna alla pagina principale</a>");
		
		// Fine pagina
		pw.println("</body></html>");
	}

}
