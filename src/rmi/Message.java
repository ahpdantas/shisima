package rmi;

import java.util.UUID;

public class Message {
	UUID from;
	String msg;
	
	public Message(UUID from, String msg){
		this.from = from;
		this.msg = msg;
	}
}
