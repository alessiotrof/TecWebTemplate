// Questo file contiene le funzioni utilizzate per andare a
// caricare l'attesa di N secondi scelta dall'utente nella
// form HTML



// Funzione che permette di individuare se la stringa passata come
// argomento è valida (non null e non vuota) e contiene soltanto numeri
function isNumeric(str) {

	// Controllo se è null o se contiene qualcosa
	if (!str || str === "") {
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
// desiderata ("wait.html") e passargli come argomento i secondi scelti
// dall'utente nell'elemento di input HTML:
// Attenzione: bisogna includere il file Javascript "utility.js" per farla
// funzionare correttamente!
function loadTheWait() {

	// Ottengo il valore inserito dall'utente nel campo di input
	var seconds = myGetElementById('numberOfSeconds').value;
	var err = false;

	// Se la stringa inserita dall'utente non è valida
	if (!isNumeric(seconds)) {
		alert("Inserisci un numero di secondi valido!");
		
		// Si e' verificato un errore!
		err = true;
	}

	// Se non ci sono stati errori, cambio pagina!
	if (!err) {
		var urlString = "wait.html?wait=" + seconds;

		// Simulo ridirezione HTTP
		// location.replace(urlString); -- NO!
		// Meglio fare cosi':
		location.href = urlString;
	}
}