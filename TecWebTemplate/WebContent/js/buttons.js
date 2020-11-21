function simpleAlert(){
	alert("Test di JavaScript 1!");
}

function checkPrimeNumber(){

	// Ottengo l'input digitato dall'utente
	var number = parseInt(prompt("Digita un numero positivo: "));
	var isPrime = true;
	
	// Controllo se il numero inserito è uguale a 1
	if (number == 1) {
	    alert("1 non è un numero primo");
	}
	
	// Controllo se il numero inserito è maggiore di 1
	else if (number > 1) {
	
	    // looping through 2 to number-1
	    for (var i = 2; i < number; i++) {
	        if (number % i == 0) {
	            isPrime = false;
	            break;
	        }
	    }
	
	    if (isPrime) {
	        alert(number + " e' un numero primo!");
	    } else {
	        alert(number + " non e' un numero primo!");
	    }
	}
}

function editPageHtml(){
	
	alert("Come per magia, l'HTML della tua pagina verra' ora modificato!");

	document.getElementById("buttonEditPageHtml").innerHTML = "Ecco l'HTML della pagina modificato da JavaScript!";
}

function getBrowserInfo(){

	alert("Hey, adesso leggero' i tuoi dettagli! Spero non ti dispiaccia!");
	
	document.getElementById("buttonGetBrowserInfo").innerHTML = "Ciao " + navigator.appName + "!<br>Versione: " + navigator.appVersion + "<br>Piattaforma: " + navigator.platform;
}


function getExamStats(){


	// Voti scritti dall'utente
	var voti = prompt("Inserisci una sequenza di voti separati da uno spazio, che vanno da 18 a 33: ").split(" ");
	
	// Trasformo l'array in un array numerico
	voti = voti.map(Number);
	
	for(var i=0; i<voti.length; i++){
		if(voti[i] < 18 || voti[i] > 33){
			alert("Attenzione, il voto " + voti[i] + " non rientra nel range richiesto!");
			
			// Indica che c'è stato un errore!
			var err = true;
		}
	}
	
	if(err){
		alert("Errore!");
		document.getElementById("error").innerHTML = "Errore: non hai inserito correttamente alcuni voti!";
	}else{
	
		// Definizione oggetto statistica
		var statistica = new Object();
		
		// Trovo il minimo 
		// (e intanto anche la somma totale)
		statistica.min = voti[0];
		var somma = 0;
		for(var i = 0; i< voti.length; i++){
		
			somma += voti[i]; // Intanto calcolo anche la somma totale
			
			if(statistica.min > voti[i]){
				statistica.min = voti[i];	
			}
			
		}
		
		alert("Voto minimo: " + statistica.min);
		
		// Trovo il massimo
		statistica.max = voti[0];
		for(var i = 0; i<voti.length; i++){
			if(statistica.max < voti[i]){
				statistica.max = voti[i];
			}
		}
		
		alert("Voto massimo: " + statistica.max);
		
		// Trovo la media 
		statistica.media = somma/voti.length;
		statistica.media = statistica.media.toFixed(2); // Arrotondamento a 2 cifre decimali
		
		alert("Media: " + statistica.media);
		
		// Unisco tutti i voti dell'array in una stringa unica che ha come separatore "-"
		var arrayString = voti.join("-");
		
		var urlString = "results.html?arr="+arrayString+"&min="+statistica.min+"&max="+statistica.max+"&mid="+statistica.media;
		
		// Simulo ridirezione HTTP
		location.replace(urlString);

		// Simulate a mouse click:
		//window.location.href = "http://www.w3schools.com";
		//
		// Simulate an HTTP redirect:
		//window.location.replace("http://www.w3schools.com");
	}
	
}

// Funzione che scrive dei numeri random all'interno dei due campi del quiz
function fill(){	
	
	// Ottengo il form "calc"
	var field = document.getElementById("calc");
	
	// Intero random tra 0 e 100 
	field.first.value = Math.floor(Math.random() * 101);     
	field.second.value = Math.floor(Math.random() * 101);

}

// Funzione che verifica il corretto inserimento del risultato del quiz
function compute(field){

	// Calcolo il "vero" risultato della form
	var result = eval(field.first.value) + eval(field.second.value);
	
	alert("result 2: " + result + " - tipo: " + typeof result);
	
	// Se il risultato è uguale a quello scritto dall'utente 
	if (result == field.sum.value) {
		field.out.value = "Corretto! Complimenti!";
	}else{
		field.out.value = "Sbagliato! Fa: " + result + " :)";
	}



}

// Appena viene caricata la pagina, la funzione fill() verrà lanciata!
onload = fill;





