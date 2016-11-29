package net;

import java.net.*;

public class Server extends NetworkInterface {
	public ServerSocket serverSocket;
	public Socket clientSocket;
	
	public Server(int port){
		this.port = port;
		this.startServer();
	}

	public void startServer(){
		ServerAcceptTask serverTask = new ServerAcceptTask(this);
		Thread waitingConnection = new Thread(serverTask);
		waitingConnection.start();
	}

}
