package it.tecwebtemplate.websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;


public class MessageEncoder implements Encoder.Text<Message> {
	
    private static Gson gson = new Gson();
    
    // Permette di convertire un oggetto in una stringa JSON
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }

    public void destroy() {
    	
    }

    public void init(EndpointConfig arg0) {
    	
    }
}

