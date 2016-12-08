package net;

import java.util.ArrayList;

public class ReceiverTask implements Runnable {
	private ArrayList<ReceiverListenerInterface> receiverListeners;
	private String msg;

	public ReceiverTask(ArrayList<ReceiverListenerInterface> receiverListeners, String msg){
		this.receiverListeners = receiverListeners;
		this.msg = msg;
	}
	
	@Override
	public void run() {
			for( ReceiverListenerInterface r: this.receiverListeners){
				if( msg != null ){
					r.receive(msg);
				}
			}
			System.out.println("thread is running...");
	}
}