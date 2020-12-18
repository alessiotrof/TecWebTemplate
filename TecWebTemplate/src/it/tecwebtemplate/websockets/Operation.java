package it.tecwebtemplate.websockets;

// Indica il formato del messaggio che verrà scambiato tra WebSockets
public class Operation {

	private String numberOne;
	private String numberTwo;
	private String operation;
	private String result;
	
	
	// Non essendo un Java Bean, non utilizza un costruttore senza argomenti
	public Operation(String numberOne, String numberTwo, String operation, String result) {
		this.numberOne = numberOne;
		this.numberTwo = numberTwo;
		this.operation = operation;
		this.result = result;
	}

	
	public String getNumberOne() {
		return numberOne;
	}

	
	public String getNumberTwo() {
		return numberTwo;
	}
	
	
	public String getOperation() {
		return operation;
	}

	public String getResult() {
		return result;
	}
	
	public void setNumberOne(String numberOne) {
		this.numberOne = numberOne;
	}


	public void setNumberTwo(String numberTwo) {
		this.numberTwo = numberTwo;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
