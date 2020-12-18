<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- import di classi Java Bean -->
<%@ page import="it.tecwebtemplate.beans.UserDB"%>
<%@ page import="it.tecwebtemplate.beans.User"%>

<!DOCTYPE html>

<html>
   <head>
      <meta charset="UTF-8">
      <title>Pagina di amministrazione</title>    
      <!-- Inclusione CSS  -->
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css" type="text/css"/>
   </head>
   
   <body>
   
      <h1>Pagina di amministrazione</h1>
      
      Qui sotto puoi trovare tutti i vari utenti del sito: <br>
      
      <br><br>
		
	  <jsp:useBean id="db" class="it.tecwebtemplate.beans.UserDB" scope="application" />
	
		
      <% 

		// Ottengo l'utente loggato attualmente
		User userLogged = (User) session.getAttribute("userLogged");
		
		// Se l'utente loggato non è null e se è l'admin del sito
	 	if(userLogged != null && userLogged.getUsername().equals((String)application.getAttribute("adminDefaultUsername")) && userLogged.getPassword().equals((String)application.getAttribute("adminDefaultPassword"))) {
		 	for(User u : db.getRegistredUsers()) {
		 		%>
				<b>Username: </b> <%= u.getUsername() %> <b>Password: </b> <%= u.getPassword() %> <br>
				<% 
		 	}
	 	}else{
	 		%>
	 		<b>Errore: non sei loggato come admin, non puoi accedere a questa pagina!</b>
	 		<% 
	 	}

      %>
      
	  <br><br><a href="<%=request.getContextPath()%>/index.jsp">Torna alla pagina principale</a>
	  
   </body>
</html>

