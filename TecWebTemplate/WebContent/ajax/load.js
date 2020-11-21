
// In attesa del caricamento del contneuto AJAX
// collochiamo una immagine di attesa al suo posto,
// magari animata, per intrattenere l'utente :)
function aggiungiImmagineAttesa( element ) {

	// variabili di funzione
	var imgWait = myGetChildByName( element, "wait");

	// lo facciamo solo una volta, 
	// successive richieste di aggiungere l'immagine sono ignorate
	if (!imgWait) {
		// Effetto animato per ingannare l'attesa
		imgWait = document.createElement('img');
		imgWait.src = "img/wait.gif";
		imgWait.alt = "Attesa";
		imgWait.name = "wait";
		element.appendChild(imgWait);
	}

}

// Ad attesa terminata rimuoviamo l'immagine
function rimuoviImmagineAttesa( element ) {

	// variabili funzione
	var imgWait = myGetChildByName(element, "wait");

	// se esiste lo rimuoviamo	
	if(imgWait){
		element.removeChild(imgWait);
	}

}


// In caso di timeout 
// inseriamo un messaggio di fallimento al posto del contenuto atteso
// e rimuoviamo eventuali intrattenitori
function aggiungiMessaggioFallimento(element) {

	// creiamo un elemento per avvertire l'utente
	// del fallimento della richiesta da aggiungere
	// a quello predisposto per mostrare il risultato.
	// Usiamo il metodo createElement() del document e
	// non innerHTML, che potrebbe riscrivere il link selezionato
	// annullando l'assegnazione del parametro fittizio.
	
	var sorry = myGetChildByName( element, "sorry");

	// ovviamente evitiamo messaggi duplicati
	if(!sorry) {
		sorry = document.createElement("p");
		sorry.name = "sorry";
		sorry.innerHTML = 
			"Spiacente, richiesta fallita.<br />" +
			"Si prega di ritentare tra qualche istante.";
		element.appendChild(sorry);
		
		// e rimuoviamo l'immagine di intrattenimento, visto che l'attesa è finita (male)
		rimuoviImmagineAttesa(element)
	}

}




// Gestiamo l'attesa associando a ogni richiesta AJAX una funzione che
// a intervalli di tempo prefissati ne verifica il completamento 
// e che la abortisce in caso di superamento del tempo massimo di attesa
function gestisciAttesa( ajax, element, inizioChiamata, massimaAttesa ) {

	// un solo gestore per richiesta
	if ( ajax.gestita ){
		// non siamo noi, è gia qualcun altro: restituiamo il controllo
		return;
	}else{ 
		// lo faremo noi
		ajax.gestita = true;  
	}
	
	// immagine per l'attesa
	aggiungiImmagineAttesa( element );

	// il controllo sul tempo trascorso deve essere
	// asincrono a questa funzione poiche' non e' detto
	// che il cambio di stato della richiesta
	// venga effettuato in tempi utili.
	// Una funzione apposita per la verifica
	// e' la soluzione piu' indicata
	verificaTempoTrascorso = function() {

		// se la richiesta si e' conclusa non eseguo
		if ( ajax.readyState == 4 ) {
			rimuoviImmagineAttesa( element );
			return;
		}

		// ogni chiamata asincrona a questa funzione
		// dovra' verificare la durata dell'interazione
		// e' necessario quindi ridichiarare la variabile
		// al fine di ottenere il nuovo oggetto Date
		dataChiamata = new Date();

		// Se il tempo trascorso e' maggiore della massima attesa ...
		if((dataChiamata.getTime() - inizioChiamata) > massimaAttesa) {

			// ... interrompiamo la richiesta ed
			// informarmiamo l'utente di quanto avvenuto.

			// Quindi riassegnamo onreadystatechange ad una
			// funzione vuota, poiche' quest'evento sara'
			// sollevato chiamando il metodo abort()
			ajax.onreadystatechange = function(){
			
				// se stiamo usando Firebug il browser fornisce l'oggetto console!
				if ( console ){
					// possiamo scrivere qui messaggi di trace e di informazioni per lo sviluppo
					// con diversi livelli di gravita' (filtrabili, per non morire tra gli output!)
					//console.debug("Richiesta AJAX abortita"); // granularità piu' fine
					console.info("Richiesta AJAX abortita"); // messaggi informativi generici
					//console.warn("Richiesta AJAX abortita"); // avvisi di potenziali criticita'
					//console.error("Richiesta AJAX abortita"); // messaggi di errori
				}
				return;
			};

			// e' possibile a questo punto richiamare il metodo abort
			// ed annullare le operazioni dell'oggetto XMLHttpRequest
			ajax.abort();

			// comunicazione del fallimento
			aggiungiMessaggioFallimento( element );
       
		}

		// se invece il tempo è inferiore al timeout
		else {
			// si richiama questa stessa funzione, con un tempo
			// che non dovrà essere ne alto ne troppo basso.
			setTimeout(verificaTempoTrascorso, 100);
		}
	}

	// definita la funzione non resta che avviarla
	verificaTempoTrascorso();

} // gestisciAttesa()




// La solita funzione di callback
function callback(xhr, element) {

	// Questa volta gestiamo anche l'attesa, non solo il risultato
	gestisciAttesa( xhr, element, new Date().getTime(), 5000 );

	// designiamo la formattazione della zona dell'output
	element.class = "content";
	
	// verifica dello stato
	if ( xhr.readyState === 2 ) {
	    	element.innerHTML = "Richiesta inviata...";
	}else if ( xhr.readyState === 3 ) {
    		element.innerHTML = "Ricezione della risposta...";
	}else if ( xhr.readyState === 4 ) {

		// verifica della risposta da parte del server
	        if ( xhr.status === 200 ) {
	        	// operazione avvenuta con successo
       			element.innerHTML = '<p>'+xhr.responseText+'</p>';
	        }else {
	        	// errore di caricamento
	        	element.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
	        	element.innerHTML += "Errore riscontrato: " + xhr.statusText;
	        }

	}

}



// Caricamento versione non AJAX
function caricaParagrafoIframe(uri, holder) {

	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	holder.innerHTML = '<iframe src="'+uri+'">Il tuo browser non supporta i frame</iframe>';
	
}




// Caricamento versione AJAX
function caricaParagrafoAJAX(uri, element, xhr) {
    
	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() { 
		callback(xhr, element); 
	};

	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		xhr.open("get", uri, true);
	}catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	xhr.setRequestHeader("connection", "close");

	// invio richiesta
	xhr.send(null);

} 




// Funzione di caricamento testo
// che astrae i casi AJAX e non AJAX
function caricaParagrafo(uri, e) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();
	
	// Se il Browser supporta Ajax
	if (xhr) { 
		caricaParagrafoAJAX(uri, e ,xhr);
	}else {  // Altrimenti utilizzo la funzione per Browser Legacy
		caricaParagrafo(uri, e); 
	}

}
