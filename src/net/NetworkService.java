package net;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import rmi.Player;
import rmi.ShisimaGameInterface;

public class NetworkService {
	private ArrayList<ReceiverListener> receiverListeners = new ArrayList<ReceiverListener>();
	private ShisimaGameInterface rmi;
	private Player player;
	

	public NetworkService(ShisimaGameInterface rmi){
		this.rmi = rmi;
		
		try {
			this.player = this.rmi.login();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ReceiverTask r = new ReceiverTask(this);
		Thread receiving = new Thread(r);
		receiving.start();
		
	}

	public Player login(){
		try {
			return this.rmi.login();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void send(ShisimaPacket pack) {
		System.out.println("Sending packet");
		try {
			this.rmi.publish(this.player, pack.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String receive(){
		try {
			return this.rmi.receive(player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addReceiverListener(ReceiverListener r){
		this.receiverListeners.add(r);
	}
	
	public ArrayList<ReceiverListener> getReceiverListeners() {
		return receiverListeners;
	}

	public Player getPlayer() {
		return player;
	}

}
