<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- import di classi Java Bean -->
<%@ page import="it.tecwebtemplate.beans.UserDB"%>
<%@ page import="it.tecwebtemplate.beans.User"%>
<%@ page import="it.tecwebtemplate.beans.LoggedUsers"%>

<!DOCTYPE html>


<html>

   <head>
      <title>Index</title>    
      <!-- Inclusione CSS  -->
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css" type="text/css"/>
      <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/img/favicon.ico"/>
   </head>
   
   <body>
   
      <h1>Full Template - Tecnologie Web T</h1>
      
      Questo è un semplice template per l'esame di Tecnologie Web T fatto da Alessio Troffei. <br>
      Per qualsiasi problema/errore relativo a questo template, scrivetemi una email al seguente indirizzo:
      <a href="mailto:alessio.troffei@studio.unibo.it">alessio.troffei@studio.unibo.it</a> <br>
      Indice del sito:
      
      <br><br>
      
      <ul style="list-style-type:disc;">
         <li><a href="<%=request.getContextPath()%>/reg.html">Registrati</a></li>
         <li><a href="<%=request.getContextPath()%>/login.html">Effettua Login</a></li>
         <li><a href="<%=request.getContextPath()%>/jsp/simple.jsp">Vai alla JSP</a></li>
         <li><a href="<%=request.getContextPath()%>/javascript.html">JavaScript</a></li>
         <li><a href="<%=request.getContextPath()%>/ajax.html">Ajax</a></li>
         <li><a href="<%=request.getContextPath()%>/reactindex.html">React.js</a></li>
         <li><a href="<%=request.getContextPath()%>/websocket.html">WebSocket</a></li>
      </ul>
      
      <br><br>
      <% 

    	final String adminDefaultUsername = "admin";
   		final String adminDefaultPassword = "admin";
   	
	   	application.setAttribute("adminDefaultUsername", adminDefaultUsername);
	   	application.setAttribute("adminDefaultPassword", adminDefaultPassword);
	    
	 	// Ottengo il Database 
	  	UserDB db = (UserDB)application.getAttribute("database");
	 	
		// Controllo se esiste già il database nel Context
		if (db == null) {
			
			// Istanzio un nuovo Database
			db = new UserDB();
			
			// Creo l'utente "admin"
			User admin = new User();
			admin.setUsername(adminDefaultUsername);
			admin.setPassword(adminDefaultPassword);
			
			// Aggiungo l'admin al Database
			db.addUser(admin);
			
			// Setto nel Context il Database
 			application.setAttribute("database", db);
 			%>
 			Database correttamente inizializzato e inserito nel Context!<br><br>
 			<% 

 		} else {
 			%>
 			Database già presente nel Context! Non è stato inizializzato adesso!<br><br>
			<%
 		}
		
		// Ottengo gli utenti loggati
	  	LoggedUsers loggedUsers = (LoggedUsers)application.getAttribute("loggedUsers");
	 	
		// Controllo se esiste già il database nel Context
		if (loggedUsers == null) {
			
			loggedUsers = new LoggedUsers();
			
			application.setAttribute("loggedUsers", loggedUsers);
			
 			%>
 			Lista degli utenti loggati correttamente inizializzata e inserita nel Context!<br><br>
			<%
			
		}else{
			
 			%>
 			Lista degli utenti loggati non presente nel Context! Non è stata inizializzata adesso!<br>
			<%
			
			if(loggedUsers.getLoggedUsers().size() > 0) {
				
	 			%>
	 			Ci sono <%=loggedUsers.getLoggedUsers().size() %> utenti loggati nel sito, ovvero:<br>
				<%
				
				// Stampo tutti gli utenti loggati
				for(User u : loggedUsers.getLoggedUsers()){
		 			%>
		 			<b><%=u.getUsername()%> </b>
					<%
				}
			}else{
				
	 			%>
	 			Non ci sono utenti loggati nel sito!<br>
				<%
				
			}

		}
		
		// Controllo se l'utente in questione è loggato o no tramite il reperimento dell'attributo
		User userLogged = (User) session.getAttribute("userLogged");
		
		// Se l'utente è loggato
		if(userLogged != null) {
			%>
			
			<b><br><br>Sei loggato nel sito come: <%= userLogged.getUsername() %> </b><br>
			<a href="<%=request.getContextPath()%>/jsp/logout.jsp">Effettua il logout</a><br><br>
			
			<%
			
			// Controllo se l'utente è loggato come admin
			if(userLogged.getUsername().equals(adminDefaultUsername) && userLogged.getPassword().equals(adminDefaultPassword)){
				%>
				<b><br><br>Sei loggato come admin! Puoi ora accedere alla pagina di amministrazione qui sotto:</b>b><br>
				<a href="<%=request.getContextPath()%>/jsp/admin.jsp">Amministrazione sito</a>

				<% 
			}
			
		}else {
			%>
			<b>Non sei loggato nel sito!</b><br><br>
			<%
		}
      %>

   </body>
</html>

