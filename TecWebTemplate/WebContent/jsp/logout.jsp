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
   
      <h1>Pagina di logout</h1>
      
      <br><br>

      <% 
		
     	User userLogged = (User)session.getAttribute("userLogged");
		if(userLogged != null) {
			
			// Tolgo l'utente dalla lista degli utenti loggati e lo rimetto nel Context
			LoggedUsers loggedUsers = (LoggedUsers) application.getAttribute("loggedUsers");
			loggedUsers.removeUser(userLogged);
			application.setAttribute("loggedUsers", loggedUsers);
			
			// Vado a distruggere la sessione corrente, così da eliminare ogni utente loggato
			session.invalidate();
			
			 %>
			 <b>Hai effettuato il logout dal sito correttamente!</b>
			 <% 
			 
		}else {
			
			 %>
			<b>Non sei loggato nel sito! Non puoi quindi procedere al logout!</b>
			<% 
			
		}

      %>
      
      
	  <br><br><a href="<%=request.getContextPath()%>/index.jsp">Torna alla pagina principale</a>
	  
	  
   </body>
</html>

