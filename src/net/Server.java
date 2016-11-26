package net;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server extends Thread implements NetworkInterface {
	private ArrayList<ChatReceiveListener> listeners = new ArrayList<ChatReceiveListener>();
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private DataInputStream is;
	private DataOutputStream os;
	
	public Server(int port){
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Waiting for connection...");
			clientSocket = serverSocket.accept();
			System.out.println("connection established.");
			
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
			
			this.start();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void send(String msg){
		try{
			os.writeUTF(msg);
			os.flush();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void addChatListener(ChatReceiveListener l){
		listeners.add(l);
	}
	
	public void run(){
		try{
			while(true){
				String msg = is.readUTF();
				for( ChatReceiveListener r: listeners){
					r.receiveMsg(msg);
				}
			}
		} catch( Exception e){
			System.out.println(e);
		}
	}

}
