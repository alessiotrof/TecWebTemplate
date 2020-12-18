// Variabile globale "operazione"
var operazione = new Object();
operazione.numberOne = "";
operazione.numberTwo = "";
operazione.operation = "";
operazione.result = "";


// Creazione della WebSocket globale
var socket = new WebSocket("ws://" + location.host + "/TecWebTemplate/calculator");



// Quando ricevo un messaggio da parte del Server
socket.onmessage = function(event){

	alert("Dati in formato JSON ricevuto dal Server: " + event.data);

	// Trasformo il JSON in una normale variabile JavaScript
	var data = JSON.parse(event.data);
	
	alert("Dati ricevuti dal Server: " + data.result);
	
	if(data.numberOne != ""){
		myGetElementById('numberOne').value = data.numberOne;
	}
	if(data.operation != ""){
		myGetElementById('operation').value = data.operation;
	}
	if(data.numberTwo != ""){
		myGetElementById('numberTwo').value = data.numberTwo;
	}
	if(data.result != ""){
		myGetElementById('result').value = data.result;
	}
	
};


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

function isOperation(str) {

	// Controllo se è null o se contiene qualcosa
	if (!str || str === "") {
		return false;
	}


	// Se non è una stringa
	if (typeof str != "string") {
		return false;
	}


	// Ritorna vero se almeno una di queste condizioni è verificata
	return str === "+" || str === "-" || str === "*" || str === "/";

}

// Funzione che viene invocata ogni volta che l'utente digita qualcosa nella prima casella di testo
function sendNumberOne(){

	var numberOne = myGetElementById('numberOne').value;
	
	operazione.numberOne = numberOne;
	operazione.numberTwo = "";
	operazione.operation = "";
	operazione.result = "";
	
	
	// Invio l'oggetto JSON
	alert("Da inviare al Server: " + JSON.stringify(operazione));
	socket.send(JSON.stringify(operazione));
	
}

// Funzione che viene invocata ogni volta che l'utente digita qualcosa nella prima casella di testo
function sendNumberTwo(){

	var numberTwo = myGetElementById('numberTwo').value;
	
	operazione.numberOne = "";
	operazione.numberTwo = numberTwo;
	operazione.operation = "";
	operazione.result = "";
	
	
	// Invio l'oggetto JSON
	alert("Da inviare al Server: " + JSON.stringify(operazione));
	socket.send(JSON.stringify(operazione));

}

// Funzione che viene invocata ogni volta che l'utente digita qualcosa nella prima casella di testo
function sendOperation(){
	
	var operation = myGetElementById('operation').value;
	
	operazione.numberOne = "";
	operazione.numberTwo = "";
	operazione.operation = operation;
	operazione.result = "";
	
	
	// Invio l'oggetto JSON
	alert("Da inviare al Server: " + JSON.stringify(operazione));
	socket.send(JSON.stringify(operazione));
	
}

function calculate() {

	// Ottengo i due numeri dai campi di input nel file HTML
	var numberOne = myGetElementById('numberOne').value;
	var numberTwo = myGetElementById('numberTwo').value;
	var operation = myGetElementById('operation').value;

	// Indica se c'e' stato un errore o meno
	var err = false;

	// Se le stringhe inserte dall'utente non sono valide
	if (!isNumeric(numberOne) && !isNumeric(numberTwo)) {
		alert("Inserisci dei numeri validi!");

		// Si e' verificato un errore!
		err = true;
	}

	// Controllo che sia un'operation valida
	if (!isOperation(operation)) {
		alert("Inserisci un'operazione valida!");

		// Si e' verificato un errore!
		err = true;
	}

	// Se non ci sono stati errori, invio al Server
	if (!err) {
	
		// Fase di invio al Server 
		operazione.numberOne = numberOne;
		operazione.numberTwo = numberTwo;
		operazione.operation = operation;
		operazione.result = "";

		socket.send(JSON.stringify(operazione));
		
	}
	
}
