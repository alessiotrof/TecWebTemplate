// File utility.js
//
// Utile per avere delle utilita' in JavaScript!



// Questa funzione mi restituisce l'elemento selezionato
// utilizzando una compatibilità migliore, anche per browser
// vecchi
function myGetElementById(idElemento) {

	// Elemento da restituire
	var elemento;

	// Se esiste il metodo "getElementById" nel Browser
	if ( document.getElementById ){
		elemento = document.getElementById(idElemento);
	}else{ // altrimenti bisogna usare un vecchio sistema
		elemento = document.all[idElemento];
	}
	
	// restituzione elemento
	return elemento;
}


// Mi restituisce la XmlHttpRequest, aggiunge anche la
// compatibilita' per Browser legacy
function myGetXmlHttpRequest() {


	// Risultato 
	var xhr = false;
	// Opzioni activeX dal pi� nuovo al piu' vecchio (Browser Legacy)
	var activeXoptions = new Array( "Microsoft.XmlHttp", "MSXML4.XmlHttp", "MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp" );

	// Primo tentativo: come oggetto nativo
	try { 
		xhr = new XMLHttpRequest(); 
	} 
	catch (e) { 
		// Non facciamo niente... semplicemente proviamo un altro modo
	}

	// Successivi tentativi come oggetto activeX dal piu' al meno recente
	if ( ! xhr ) {
		var created = false; 
		for ( var i = 0 ; i < activeXoptions.length && !created ; i++ ) {
			try {
				// tentativo di creazione
				xhr = new ActiveXObject( activeXoptions[i] );
				// se la creazione non fallisce il codice del blocco try continua a essere eseguito
				created = true;
			} 
			catch (e) { 
				// non facciamo niente... semplicemente proviamo un altro modo
			} 
		} 
	} 

	// restituzione del risultato, eventualmente ancora false se il browser non supporta AJAX
	return xhr;

}


function myGetRequestParameter(parameterName) {

	// estraiamo i parametri di get dalla uri della pagina
	var queryString = window.top.location.search.substring(1);

	// Add "=" to the parameter name (i.e. parameterName=value)
	// torna utile nello split della query per cercare il parametro voluto
	var parameterName = parameterName + "=";
		
	if ( queryString.length > 0 ) {

		// Find the beginning of the string
		begin = queryString.indexOf ( parameterName );

		// If the parameter name is not found, skip it, otherwise return the value
		if ( begin != -1 ) {

			// Add the length (integer) to the beginning
			begin += parameterName.length;

			// Multiple parameters are separated by the "&" sign
			end = queryString.indexOf ( "&" , begin );
	
			if ( end == -1 ) {
				end = queryString.length
			}

			// Return the string (unescapes special characters such as & / = etc...)
			return unescape ( queryString.substring ( begin, end ) );
		}

		// Return "null" if no parameter has been found
		return "null";

	}

}




// Funzione per recuperare per nome l'elemento figlio di un elemento dato
//
// Non usa l'id (che deve essere unico nel DOM) ma il name 
// per lasciare la possibilita' di avere piu' nodi con lo stesso name
// ma figli di elementi diversi.
//
// Ad esempio per collocare piu' immagini di attesa nel documento, 
// in caso di piu' richieste AJAX contemporanee
function myGetChildByName( element, name ) {
	
	// analisi alla ricerca del nodo voluto
	if ( element.childNodes )
		for ( var i = 0 ; i < element.childNodes.length ; i++ )
			if ( element.childNodes[i].name === name )
				return element.childNodes[i];

}
