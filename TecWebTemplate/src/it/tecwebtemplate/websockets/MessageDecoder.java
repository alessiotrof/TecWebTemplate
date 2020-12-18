package it.tecwebtemplate.websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Message> {
    
    private static Gson gson = new Gson();

    public void destroy() {

    }

    public void init(EndpointConfig arg0) {
    	
    }
    
    // Permette di decodificare una certa stringa "s" in un oggetto java
    public Message decode(String s) throws DecodeException {
        return gson.fromJson(s, Message.class);
    }

    public boolean willDecode(String s) {
    	
        if (s == null) {
        	return false;
        }
        
        return true;
    }
}

