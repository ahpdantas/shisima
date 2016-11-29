package net;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class Client extends NetworkInterface{
	private Socket clientSocket;
	
	public Client(String host, int port) throws java.net.ConnectException, UnknownHostException, IOException {
		
		clientSocket = new Socket(host, port);
		try{
			super.os = new DataOutputStream(clientSocket.getOutputStream());
			super.is = new DataInputStream(clientSocket.getInputStream());
			this.start();
		}catch( Exception e){
			e.printStackTrace();
		}
	}
}
