package it.tecwebtemplate.websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;


public class OperationEncoder implements Encoder.Text<Operation> {
	
    private static Gson gson = new Gson();
    
    // Permette di convertire un oggetto "Operation" in una stringa JSON
    public String encode(Operation operation) throws EncodeException {
        return gson.toJson(operation);
    }

    public void destroy() {
    	
    }

    public void init(EndpointConfig arg0) {
    	
    }
}

