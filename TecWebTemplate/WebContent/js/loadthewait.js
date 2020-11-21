// Funzione che permette di caricare la pagina HTML "wait.html" e
// di passare come parametro il numero di secondi scelto dall'utente
// nell'elemento di input HTML
function isNumeric(str) {
	
	// Controllo se è null o se contiene qualcosa
	if(!str || str === ""){
		return false;
	}
	
	// Se non è una stringa
	if (typeof str != "string") {
		return false;
	}
		
	return !isNaN(str) && // use type coercion to parse the _entirety_ of the string ("parseFloat" alone does not do this)...
 	!isNaN(parseFloat(str)); // ...and ensure strings of whitespace fail
}

// Funzione che permette di andare a richiamare la pagina HTML
// desiderata e passargli come argomento i secondi scelti dall'utente
function loadTheWait() {
	// Ottengo il valore inserito dall'utente nel campo di input
	//var seconds = document.getElementById("numberOfSeconds").value;
	var seconds = myGetElementById('numberOfSeconds').value;
	var err = false;
	
	// Se la stringa inserita dall'utente non è valida
	if(!isNumeric(seconds)) {
		alert("Inserisci un numero di secondi valido!");
		err = true;
	}
	
	// Se non ci sono stati errori, cambio pagina!
	if(!err){
		var urlString = "wait.html?wait="+seconds;
		
		// Simulo ridirezione HTTP
		// location.replace(urlString);
		location.href = urlString;
	}
}