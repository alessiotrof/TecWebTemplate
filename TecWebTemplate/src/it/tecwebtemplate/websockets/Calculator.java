package it.tecwebtemplate.websockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// @ServerEndpoint da un nome all'end point
// Questo può essere acceduto via ws://localhost:8080/TecWebTemplate/calculator
// - "localhost" è l'indirizzo dell'host dove è deployato il server ws,
// - "TecWebTemplate" è il nome principale del Server
// - "calculator" è l'indirizzo specifico di questo endpoint
@ServerEndpoint(value = "/calculator", decoders = { OperationDecoder.class }, encoders = { OperationEncoder.class })
public class Calculator {

	// Variabili interne
	private Session session; // Sessione
	double result; // Risultato
	private static Set<Calculator> clientEndpoints = new CopyOnWriteArraySet<>(); // Set di Client connessi

	// @OnOpen questo metodo ci permette di intercettare la creazione di una nuova
	// sessione.
	// La classe session permette di inviare messaggi ai client connessi.
	// Nel metodo onOpen, faremo sapere all'utente che le operazioni di handshake
	// sono state completate con successo ed è quindi possibile iniziare le
	// comunicazioni.
	@OnOpen
	public void onOpen(Session session) {

		System.out.println(session.getId() + " ha aperto una connessione");

		this.session = session;
		result = 0;

		// Aggiungo il cliente connesso all'insieme dei client connessi
		clientEndpoints.add(this);
	}

	// Quando un client invia un messaggio al server questo metodo intercetterà tale
	// messaggio
	// e compierà le azioni di conseguenza.
	@OnMessage
	public void onMessage(Session session, Operation received) {

		// Sarà poi inviata a tutti i Client
		Operation toSend = new Operation("", "", "", "");

		int numberOne = 0;
		int numberTwo = 0;
		String operation;
		double result = 0;

		System.out.println("Ricevuto messaggio da: " + session.getId());

		// Se il messaggio che ho ricevuto ha il primo numero non vuoto
		if (!received.getNumberOne().isBlank() && !received.getNumberOne().isEmpty()) {

			numberOne = Integer.parseInt(received.getNumberOne());

			// Se ho ricevuto effettivamente un solo numero
			if (received.getOperation().isBlank() || received.getOperation().isEmpty()) {

				toSend.setNumberOne(Integer.toString(numberOne));

				try {
					// Invio a tutti gli endpoint connessi il messaggio
					broadcast(toSend);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}

		// Se il messaggio che ho ricevuto ha il secondo numero non vuoto
		if (!received.getNumberTwo().isBlank() && !received.getNumberTwo().isEmpty()) {

			numberTwo = Integer.parseInt(received.getNumberTwo());

			// Se ho ricevuto effettivamente un solo numero
			if (received.getOperation().isBlank() || received.getOperation().isEmpty()) {

				toSend.setNumberTwo(String.valueOf(numberTwo));

				try {
					// Invio a tutti gli endpoint connessi il messaggio
					broadcast(toSend);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}

		// Se il messaggio che ho ricevuto non ha un'operazione vuota
		if (!received.getOperation().isBlank() && !received.getOperation().isEmpty()) {

			operation = received.getOperation();

			toSend.setOperation(operation);

			try {
				// Invio a tutti gli endpoint connessi il messaggio
				broadcast(toSend);
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		}

		// Se il messaggio che ho ricevuto ha un'operazione inserita
		if (!received.getOperation().isBlank() && !received.getOperation().isEmpty()) {

			// Faccio i calcoli
			switch (received.getOperation()) {

				case "+": {
					result = Double.parseDouble(received.getNumberOne()) + Double.parseDouble(received.getNumberTwo());
					break;
				}
				case "-": {
					result = Double.parseDouble(received.getNumberOne()) - Double.parseDouble(received.getNumberTwo());
					break;
				}
				case "*": {
					result = Double.parseDouble(received.getNumberOne()) * Double.parseDouble(received.getNumberTwo());
					break;
				}
				case "/": {
					result = Double.parseDouble(received.getNumberOne()) / Double.parseDouble(received.getNumberTwo());
					break;
				}
				default: {
					throw new IllegalArgumentException("Operazione non valida ricevuta: " + received.getOperation());
				}

			}

			// Setto il risultato all'oggetto che devo inviare
			toSend.setResult(Double.toString(result));

			try {
				// Invio a tutti gli endpoint connessi
				broadcast(toSend);
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}

		}

	}

	// Metodo che intercetta la chiusura di una connessine da parte di un client
	// Nota: non si possono inviare messaggi al client da questo metodo
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " terminata");
		clientEndpoints.remove(this);
	}

	// Metodo che viene invocato in caso di errore
	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	// Metodo per inviare ad ogni client connesso l'operazione
	private static void broadcast(Operation operation) throws IOException, EncodeException {

		clientEndpoints.forEach(endpoint -> {
			synchronized (endpoint) {
				try {
					endpoint.session.getBasicRemote().sendObject((Object) operation);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}

}