// Variabile globale "message"
var message = new Object();
message.user = "";
message.content = "";

// Creazione della WebSocket globale
var socket = new WebSocket("ws://" + location.host + "/TecWebTemplate/chat");


// Quando ricevo un messaggio da parte del Server
socket.onmessage = function(event){

	alert("Dati in formato JSON ricevuto dal Server: " + event.data);

	// Trasformo il JSON in una normale variabile JavaScript
	var data = JSON.parse(event.data);
	
	alert("Dati ricevuti dal Server: User: "+ data.user  + " Contenuto: " + data.content);
	
	if(data.content != ""){
		// Accodo il testo nella chatarea e pulisco il campo di input
		myGetElementById('chatarea').value += data.user + ": " + data.content + "\n";
		myGetElementById('messagearea').value = "";
	}

};


// Funzione che permette di individuare se la stringa passata come
// argomento è valida (non null e non vuota)
function isValid(str) {

	// Controllo se è null o se contiene qualcosa
	if (!str || str === "") {
		return false;
	}
	
	return true;
}


// Funzione principale per la chat
function chat(currentUser) {

	// Ottengo i due numeri dai campi di input nel file HTML
	var content = myGetElementById('messagearea').value;

	// Indica se c'e' stato un errore o meno
	var err = false;

	// Se le stringhe inserte dall'utente non sono valide
	if (!isValid(content)) {
		alert("Inserisci del testo valido da inviare!");

		// Si e' verificato un errore!
		err = true;
	}

	// Se non ci sono stati errori, invio al Server
	if (!err) {
	
		// Fase di invio al Server 
		message.user = currentUser;
		message.content = content;

		socket.send(JSON.stringify(message));
		
	}
	
}
