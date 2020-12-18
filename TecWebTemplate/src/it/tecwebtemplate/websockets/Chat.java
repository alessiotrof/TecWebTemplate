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
@ServerEndpoint(value = "/chat", decoders = { MessageDecoder.class }, encoders = { MessageEncoder.class })
public class Chat {

	
	// Variabili interne
	private Session session; // Sessione
	double result; // Risultato
	private static Set<Chat> clientEndpoints = new CopyOnWriteArraySet<>(); // Set di Client connessi

	
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
	public void onMessage(Session session, Message received) {

		// Sarà poi inviata a tutti i Client
		Message toSend = new Message("", "");

		System.out.println("Ricevuto messaggio da: " + session.getId());


		// Se il messaggio che ho ricevuto ha un'operazione inserita
		if (!received.getContent().isEmpty() && !received.getContent().isBlank()) {

			// Setto il risultato all'oggetto che devo inviare
			toSend.setUser(received.getUser());
			toSend.setContent(received.getContent());

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
	private static void broadcast(Message message) throws IOException, EncodeException {

		clientEndpoints.forEach(endpoint -> {
			synchronized (endpoint) {
				try {
					endpoint.session.getBasicRemote().sendObject((Object) message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}

}