package net;

public class NetworkService {
	private NetworkInterface net; 
	
	public NetworkService(int port){
		net = new Server(port);
	}
	
	public NetworkService(String host, int port){
		net = new Client(host,port);
	}
	
	public void addChatListener(ChatReceiveListener l){
		net.addChatListener(l);
	}
	
	public void sendChatMessage(String msg){
		net.send(msg);
	}

}