package it.tecwebtemplate.websockets;

public class Message {

	
	private String user;
	private String content;
	
	
	public Message(String user, String content) {
		this.user = user;
		this.content = content;
	}
	
	
	public String getUser() {
		return user;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
