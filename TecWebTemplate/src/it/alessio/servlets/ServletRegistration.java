package it.alessio.servlets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SimpleServlet
 */


public class ServletRegistration extends HttpServlet {
	
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
	    
		// Ottengo il parametro userName
		String username = request.getParameter("username");
		
		// Ottengo la sessione, la sessione è condivisa soltanto dalle chiamate di uno stesso client
		HttpSession session = request.getSession();
		
		// Ottengo il contesto: il contesto è condiviso anche da diverse sessioni (anche browser diversi)
		ServletContext context = request.getServletContext();
		
		// Se il valore dell'userName inserito dall'utente contiene qualcosa
		if(username != null) {
	
			// Aggiungo valori nella sessione
			// "savedUsername" sarà il nome del parametro salvato
			// sia per la sessione che per il contesto
			session.setAttribute("savedUsername", username);
			context.setAttribute("savedUsername", username);
		}
		 
		// Scrivo in HTML
		pw.println("<h1>Ciao dal metodo doGet()!</h1>");
		pw.println("<br><br>Il tuo username è: " + username);
		pw.println("<br><br>Il tuo username salvato nella sessione è: " + session.getAttribute("savedUsername"));
		pw.println("<br><br>Il tuo username salvato nel contesto è: " + context.getAttribute("savedUsername"));
		
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
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String education = request.getParameter("education");
		String residence = request.getParameter("residence");
		
		// Ottengo tutte le lingue selezionate dall'utente nella tendina
		String[] languages = request.getParameterValues("languages");
		
		// Scrivo in HTML i risultati che ho catturato
		pw.println("<h1>Ciao dal metodo doPost()!</h1>");
		pw.println("<br><br>Il tuo username è: " + username);
		pw.println("<br>Il tuo nome è: " + name);
		pw.println("<br>Il tuo cognome è: " + surname);
		pw.println("<br>La tua password è: " + password);
		pw.println("<br>Il tuo grado di istruzione è: " + education);
		pw.println("<br>La tua residenza è: " + residence);
		pw.println("<br>E parli " + languages.length + " lingue diverse, ovvero: ");
		
		// Stampo, uno ad uno, le varie lingue scelte dall'utente  
		for(int i=0;i<languages.length; i++) {
			pw.println("<br>" + languages[i]);
		}
		
		// savedUser mi indica la stringa relativa all'utente che verrà salvata sul file 
		String savedUser = "username: " + username + " - password: " + password + "\n";
		
		System.out.println("Stringa da scrivere: " + savedUser);
		
		// Ottengo il path del file
		String filePath = this.getServletContext().getRealPath("/save/savedusers.dat");  
		
		// Salvo l'utente nel file
		//
		// NON SCRIVE SUL FILE! RISOLVERE!
		//
		writeStringOnFile(filePath, savedUser);
		
		System.out.println("File scritto!");
		
		
		// Fine pagina
		pw.println("</body></html>");
	
	}
	
	private void writeStringOnFile(String fileName, String message) {
	
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        try {
            pw.println(message);
            
            pw.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

}
