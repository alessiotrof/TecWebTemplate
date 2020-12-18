package it.tecwebtemplate.websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class OperationDecoder implements Decoder.Text<Operation> {
    
    private static Gson gson = new Gson();

    public void destroy() {

    }

    public void init(EndpointConfig arg0) {
    	
    }
    
    // Permette di decodificare una certa stringa S in un oggetto java (in questo caso, oggetto "Operation")
    public Operation decode(String s) throws DecodeException {
        return gson.fromJson(s, Operation.class);
    }

    public boolean willDecode(String s) {
    	
        if (s == null) {
        	return false;
        }
        
        return true;
    }
}

