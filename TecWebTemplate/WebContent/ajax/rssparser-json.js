// Funzione che genera una lista XHTML 
// con gli item presi dai feed RSS scaricati in formato JSON
// In poche parole, trasforma il testo JSON in un formato
// HTML che è poi piazzato nell'innerHtml di element
function parsificaJSON(jsonText) {

	// Otteniamo la lista degli item dall'RSS 2.0 di edit
	var items = JSON.parse(jsonText);

	// Predisponiamo una struttura dati in cui memorrizzare le informazioni di interesse
	var itemNodes = new Array();

	// la variabile di ritorno, in questo esempio, e' testuale
	var risultato = "";

	// ciclo di lettura degli elementi
	for (var a = 0, b = items.length; a < b; a++) {
		// [length al posto di push serve per evitare errori con vecchi browser]
		itemNodes[a] = new Object();
		itemNodes[a].title = items[a].title;
		itemNodes[a].description = items[a].description;
		itemNodes[a].link = items[a].link;
	}

	// non resta che popolare la variabile di ritorno
	// con una lista non ordinata di informazioni
	//
	// apertura e chiusura della lista sono esterne al ciclo for 
	// in modo che eseguano anche in assenza di items
	risultato = "<ul>";

	for (var c = 0; c < itemNodes.length; c++) {
		risultato += '<li class="item"><strong>' + itemNodes[c].title + '</strong><br/>';
		risultato += itemNodes[c].description + "<br/>";
		risultato += '<a href="' + itemNodes[c].link + '">approfondisci</a><br/></li>';
	};

	// chiudiamo la lista creata
	risultato += "</ul>";

	// restituzione dell'html da aggiungere alla pagina
	return risultato;

}


// Funzione di callback
function callback(xhr, element) {

	// verifica dello stato
	if (xhr.readyState === 2) {
		element.innerHTML = "Richiesta inviata...";
	}else if (xhr.readyState === 3) {
		element.innerHTML = "Ricezione della risposta...";
	}else if (xhr.readyState === 4) {

		// verifica della risposta da parte del server
		if (xhr.status === 200) {

			// operazione avvenuta con successo
			// Vado a sostituire nel HTML dell'element
			// il valore di ritorno della funzione
			// "parsificaJSON", che va a trasformare 
			// il JSON in HTML
			element.innerHTML = parsificaJSON(xhr.responseText);

		}else {
			// errore di caricamento
			element.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
			element.innerHTML += "Errore riscontrato: " + xhr.statusText;
		}

	}

}




// Imposta il contenuto disponibile presso uri
// come src di un iframe all'interno dell'elemento holder del DOM
// Non usa AJAX; per browser legacy
function caricaFeedIframe(uri, holder) {

	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	holder.innerHTML = '<iframe src="' + uri + '" width="50%" height="50px">Il tuo browser non supporta gli iframe</iframe>';
	
	// non riesco tuttavia a intervenire per parsificarlo! e' il browser che renderizza l'src dell'iframe!
}




// Imposta il contenuto xml disponibile presso uri
// all'interno dell'elemento holder del DOM
// Usa tecniche AJAX attrverso la XmlHttpRequest fornita in xhr
function caricaFeedAJAX(uri, element, xhr) {

	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() {
		callback(xhr, element);
	};

	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		xhr.open("get", uri, true);
	}catch (e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	// Questo metodo va messo dopo "open", ma prima di "send"
	xhr.setRequestHeader("connection", "close");

	// invio richiesta
	xhr.send(null);
}


// Scarica un contenuto xml dall'uri fornito
// e lo aggiunge al contenuto dell'elemento e del DOM
// Gestisce sia AJAX che il mancato supporto ad AJAX 
function caricaFeed(uri, e) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();
		
	if (xhr){
		caricaFeedAJAX(uri, e, xhr);
	}else{
		caricaFeedIframe(uri, e);
	}

}
