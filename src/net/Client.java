package net;

import java.net.Socket;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Client extends Thread implements NetworkInterface{
	private ArrayList<ChatReceiveListener> listeners = new ArrayList<ChatReceiveListener>();
	private Socket clientSocket;
	private DataOutputStream os;
	private DataInputStream is;
	
	public Client(String host, int port){
		try{
			clientSocket = new Socket(host, port);
			System.out.println("Connected");
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			
			this.start();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void send(String msg){
		try{
			os.writeUTF(msg);
			os.flush();
		}catch( Exception e){
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
		}catch( Exception e){
			System.out.println(e);
		}
	}
}
