package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerAcceptTask implements Runnable{
	private Server serverInstance;
	
	public ServerAcceptTask(Server server){
		this.serverInstance = server;  
	}

	@Override
	public void run() {
		try {
            serverInstance.serverSocket = new ServerSocket(this.serverInstance.port);
            System.out.println("Waiting for clients to connect...");
            serverInstance.clientSocket = serverInstance.serverSocket.accept();
            System.out.println("Clients connected");
            
			serverInstance.is = new DataInputStream(serverInstance.clientSocket.getInputStream());
			serverInstance.os = new DataOutputStream(serverInstance.clientSocket.getOutputStream());
			
			serverInstance.connected = true;
			System.out.println("Connection made");
			for( ConnectionStatusChangeListener c: serverInstance.statusListeners){
				c.ConnectionStatusChange(serverInstance.connected);;
			}
			serverInstance.start();
          
        } catch (IOException e) {
            System.err.println("Unable to process client request");
            e.printStackTrace();
        }
		
	}

}
