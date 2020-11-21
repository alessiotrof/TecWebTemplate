<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>

<!-- import di classi Java Bean -->
<%@ page import="it.alessio.beans.SimpleBean"%>


<!DOCTYPE html>

<html>

	<head>
		<meta charset="ISO-8859-1">
		<title>Test JSP</title>
		<!-- Inclusione CSS  -->
    	<link rel="stylesheet" href="../css/default.css" type="text/css">
	</head>
	
	<body>
	
	  	<h1>Test di una JSP </h1>
	  	
	  	<!-- Uso la Java Bean -->
		<jsp:useBean id="timeBean" class="it.alessio.beans.SimpleBean" scope="request" />
		
	  	<!-- Questo è il codice Scriplet  -->
	  	<b>
	  	    Java Bean attuale: <%= timeBean %>
	  	    
	  	    <br>
	  		
	  		<!-- Ottengo l'ora attuale attraverso la Java Bean -->
	  		Ora attuale:
	  		<jsp:getProperty name="timeBean" property="hours"/> :
	  		<jsp:getProperty name="timeBean" property="minutes"/>
	  	</b>

	  	<br>
	  	<br>
	  	
	  	<!-- Questo è il codice di Dichiarazione  -->
	  	<%!
	  		public int add(int a, int b){
	  			return a + b;
	  		}
	  	%>
	  	
	  	<!-- Questo è il codice Scriplet  -->
	  	<%
	  		// Normale codice java
	  		
	  		int a = 2;
	  		int b = 5;
	  		
	  		int c = a + b;

	  	%>
	  	
	 	<!-- Questo è il codice di Espressione -->
	  	Il valore di C è: <%=c%>
	  	
	  	
	  	<!-- Questo è il codice Scriplet  -->
	  	<%
	  		c = add(1000, 1000);
	  	
	  	%>
	  	
	  	<br>

	  	Il valore di C restituito dalla funzione è: <%=c%>
	  	
	  	<br>
	  	
	  	<%
	  		for(int i=0; i<10; i++){
	  			
	  	%>
	  	
	  	Il valore di i è: <%=i%><br>
	  	
	  	<%  } %>

	  	
	</body>
	
</html>