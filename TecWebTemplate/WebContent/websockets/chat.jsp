<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- import di classi Java Bean -->
<%@ page import="it.tecwebtemplate.beans.UserDB"%>
<%@ page import="it.tecwebtemplate.beans.User"%>

<!DOCTYPE html>

<html>
   <head>
      <meta charset="UTF-8">
      <title>Chat</title>    
      <!-- Inclusione CSS  -->
      <link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css" type="text/css"/>
 	  <script src="<%=request.getContextPath()%>/js/utility.js"></script>
	  <script src="<%=request.getContextPath()%>/js/chat.js"></script>
   </head>
   
   <body>
   
      <h1>WebSockets - Chat</h1>

      Qui sotto puoi utilizzare una semplice chat per lo scambio dei messaggi tra gli utenti registrati al sito!
      
      <br><br>

      <% 

		// Controllo se l'utente in questione è loggato o no
		User userLogged = (User) session.getAttribute("userLogged");
		
		// Controllo se l'utente in questione è loggato
		if(userLogged != null) {
			
			%>
			
			<b>Sei loggato nel sito come: <%= userLogged.getUsername() %> </b><br><br>Puoi ora procedere alla chat:<br><br>  
			<a href="<%=request.getContextPath()%>/jsp/logout.jsp">Effettua il logout</a><br><br>
			
			<textarea id="chatarea" name="chatarea" rows="10" cols="128" readonly>
  			</textarea><br><br>

      		<input name="messagearea" type="text" maxlength="256" id="messagearea"/><br>
			<button type="submit" onClick="chat('<%=userLogged.getUsername()%>')">Invia</button>
			
			<% 

		}else {
			%>
			<b>Non sei loggato nel sito! Non puoi accedere alla chat senza aver fatto il login!</b><br><br>
			<a href="<%=request.getContextPath()%>/login.html">Effettua il login!</a>
			<%
		}
      %>
		
		<br><br><a href="../index.jsp">Torna alla pagina principale</a>
		
   </body>
</html>

