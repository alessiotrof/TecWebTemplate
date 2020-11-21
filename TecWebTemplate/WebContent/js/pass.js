function passHider() {

	// Ottengo ciò che è stato scritto dall'utente
	// nel passwordField
	var x = document.getElementById("passwordField");
	
	// Cambio il tipo dell'elemento da password a text normale
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
} 