// Funzione vecchia, presente ma non piu' utilizzata...
// Meglio utilizzare lo stile fatto dal prof, questa funzione
// e' probabilmente troppo semplice e poco riutilizzabile...
function loadFile() {

	var xhttp = new XMLHttpRequest();

	// Quando cambia il readyState
	xhttp.onreadystatechange = function() {

		// Se il readyState = 4 e la risorsa è stata trovata
		if (this.readyState == 4 && this.status == 200) {

			// Al posto di "pass" metto il responseText, ovvero il contenuto del file
			document.getElementById("pass").innerHTML = this.responseText;
		}
	};

	// Ottengo il file "savedusers" in modo asincrono!
	xhttp.open("GET", "save/savedusers.dat", true);
	xhttp.send();

}

// Funzione per caricare un documento XML
function loadXml() {

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			buildXml(this);
		}
	};

	xhttp.open("GET", "xml/sample.xml", true);
	xhttp.send();
}


// Funzione vera e propria che mi prende i dati XML e me li stampa come tabella
function buildXml(xml) {

	var i;
	var xmlDoc = xml.responseXML;
	var table = "<tr><th>Artist</th><th>Title</th></tr>";
	var x = xmlDoc.getElementsByTagName("CD");

	// Costruisco la tabella
	for (i = 0; i < x.length; i++) {
		table += "<tr><td>" +
			x[i].getElementsByTagName("ARTIST")[0].childNodes[0].nodeValue +
			"</td><td>" +
			x[i].getElementsByTagName("TITLE")[0].childNodes[0].nodeValue +
			"</td></tr>";
	}

	document.getElementById("xml").innerHTML = table;
}


// Funzione che mi stampa i contenuti di un file json
function loadJsonObject() {

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {

			// Trasformo il responseText della richiesta GET
			// (che è un oggetto JSON) in un normale oggetto
			// JavaScript
			var jsonObj = JSON.parse(this.responseText);


			// Scrivo i vari campi dell'oggetto nel paragrafo "json" della pagina HTML
			// element contiene il nome di ogni campo dell'oggetto JSON
			// jsonObj[element] contiene ogni valore del campo che stiamo prendendo in considerazione ora
			for (element in jsonObj) {
				document.getElementById("json").innerHTML += element + ": " + jsonObj[element] + "<br>";
			}


		}
	};

	xhttp.open("GET", "json/example.json", true);
	xhttp.send();

}


// Non usa AJAX per browser legacy
function caricaTestoIframe(uri, holder) {
	holder.innerHTML = '<iframe src="' + uri + '" width="50%" height="50px">Il tuo browser non supporta gli iframe</iframe>';
}


function callback(xhr, element) {

	// Designiamo la formattazione della zona dell'output
	element.class = "content";


	// Verifica dello stato:
	// 2 = richiesta inviata
	// 3 = ricezione risposta
	// 4 = risposta ricevuta
	if (xhr.readyState === 2) {

		element.innerHTML = "Richiesta inviata...";

	} else if (xhr.readyState === 3) {

		element.innerHTML = "Ricezione della risposta...";

	} else if (xhr.readyState === 4) {

		// verifica della risposta da parte del server
		// 200 = operazione avvenuta con successo
		if (xhr.status === 200) {

			// Scrivo nel codice HTML dell'elemento la risposta ottenuta
			element.innerHTML = xhr.responseText;

		} else { // Se lo status non è 200, significa che l'operazione non  avvenuta con successo

			element.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
			element.innerHTML += "Errore: " + xhr.statusText;

		}

	}


}

// Imposta il contenuto testuale disponibile presso uri
// come HTML interno dell'elemento theHolder del DOM
function caricaTestoAJAX(uri, element, xhr) {

	// impostazione controllo e stato della richiesta
	// SAREBBE MEGLIO UTILIZZARE LA FUNZIONE DI CALLBACK QUI!
	// Edit: l'ho messo :)
	xhr.onreadystatechange = function() {
		// Ogni volta che cambia lo stato invoco la funzione "callback()"!
		callback(xhr, element);

	}

	// impostazione richiesta ASINCRONA in GET del file specificato
	try {
		xhr.open("get", uri, true);
	} catch (e) { // Potrebbero esserci eccezioni vengono sollevate, ad esempio, quando si cerca di accedere a URI "cross-domain" 
		alert(e);
	}

	// sostituzione dell'header "connection" (default = "keep alive")
	xhr.setRequestHeader("connection", "close");

	// Invio la richiesta
	xhr.send(null);

}



// Scarica un contenuto testuale dall'uri fornito
// e lo aggiunge al contenuto dell'elemento "e" del dom
// Gestisce sia AJAX che il mancato supporto ad AJAX 
function caricaTesto(uri, element) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	// Se Ajax è supportato
	if (xhr) {
		caricaTestoAJAX(uri, element, xhr);
	} else { // Altrimenti lancio l'altra funzione fatta per i Browser Legacy
		caricaTestoIframe(uri, element);
	}

}