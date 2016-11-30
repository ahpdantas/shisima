package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class NetworkInterface extends Thread{
	private ArrayList<ReceiverListener> receiverListeners = new ArrayList<ReceiverListener>();

	public int port;
	public DataInputStream is;
	public DataOutputStream os;
	
	
	public void send(String msg){
		try{
			os.writeUTF(msg);
			os.flush();
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	
	public void addReceiverListener(ReceiverListener r){
		this.receiverListeners.add(r);
	}
	
	public void run(){
		try{
			while(true){
				String msg = is.readUTF();
				for( ReceiverListener r: receiverListeners){
					r.receive(msg);
				}
			}
		} catch( Exception e){
			e.printStackTrace();
		}
	}
}
