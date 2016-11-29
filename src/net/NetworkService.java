package net;

import java.io.IOException;
import java.net.UnknownHostException;

public class NetworkService {
	public static final int CLIENT_TYPE = 1;
	public static final int SERVER_TYPE = 2;
	
	
	private NetworkInterface net; 
	public int type = SERVER_TYPE;
	
	public NetworkService(int port){
		net = new Server(port);
		this.type = SERVER_TYPE;
	}
	
	public NetworkService(String host, int port) throws UnknownHostException, IOException{
		net = new Client(host,port);
		this.type = CLIENT_TYPE;
	}
	
	public void send(ShisimaPacket pack) throws Exception{
		net.send(pack.toString());
	}
	
	public void addReceiverListener(ReceiverListener r){
		net.addReceiverListener(r);
	} 

}
