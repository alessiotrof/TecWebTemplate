
// Funzione di callback
function categoriaCallback(xhr, element) {

	// verifica dello stato
	if (xhr.readyState === 2) {
		// non faccio niente
		// element.innerHTML = "Richiesta inviata...";
	}
	else if (xhr.readyState === 3) {
		// non faccio niente
		// element.innerHTML = "Ricezione della risposta...";
	}
	else if (xhr.readyState === 4) {

		// verifica della risposta da parte del server
		if (xhr.status === 200) {

			// Operazione avvenuta con successo!
			if (xhr.responseText && xhr.responseText !== "") {
				element.value = xhr.responseText;
			} else {
				// non faccio niente
			}

		} else { // Errore caricamento
			// non faccio niente nemmeno qui
		}

	}

}



// Imposta il contenuto testuale disponibile presso uri
// come src di un iframe all'interno dell'elemento holder del DOM
// Non usa AJAX; per browser legacy
function caricaCategoriaIframe(uri, holder) {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	holder.innerHTML = '<iframe src="' + uri + '" width="50%" height="50px">Il tuo browser non supporta gli iframe</iframe>';

	// non riesco tuttavia a intervenire per parsarlo! è il browser che renderizza l'src dell'iframe!
}



// Imposta il contenuto testuale disponibile presso uri
// all'interno dell'elemento holder del DOM
// Usa tecniche AJAX attrverso la XmlHttpRequest fornita in xhr
function caricaCategoriaAJAX(uri, element, xhr) {

	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() {
		categoriaCallback(xhr, element);
	};

	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		xhr.open("get", uri, true);
	} catch (e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	xhr.setRequestHeader("connection", "close");

	// invio richiesta
	xhr.send(null);

}



// Scarica un contenuto testuale dall'uri fornito
// e lo aggiunge al contenuto dell'elemento e del dom
// Gestisce sia AJAX che il mancato supporto ad AJAX 
function caricaCategoria(uri, e) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	if (xhr) {
		caricaCategoriaAJAX(uri, e, xhr);
	} else {
		caricaCategoriaIframe(uri, e);
	}

}
