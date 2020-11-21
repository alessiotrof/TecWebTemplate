// Funzione per mostrare i risultati della media degli esami
function mostraRisultati(){
		
	// Ottengo l'URL
	var urlString = location.href;
	
	// Variabile URL
	var url = new URL(urlString);
	
	alert("URL: " + urlString);
	
	document.getElementById("showres").innerHTML = "Voti: "+ url.searchParams.get("arr") +"<br>Voto minimo: " + url.searchParams.get("min") + "<br>Voto massimo: " + url.searchParams.get("max") + "<br>Voto medio: " + url.searchParams.get("mid"); 

}

// Appena la pagina HTML viene caricata, la funzione mostraRisultati() verrà avviata!
onload = mostraRisultati;

