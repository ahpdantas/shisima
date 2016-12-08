package net;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import rmi.PlayerInstance;
import rmi.TransmitRmiInterface;

public class NetworkService implements ReceiverRmiInterface {
	private ArrayList<ReceiverListenerInterface> receiverListeners = new ArrayList<ReceiverListenerInterface>();
	private TransmitRmiInterface rmi;
	private PlayerInstance player;
	

	public NetworkService(TransmitRmiInterface rmi){
		this.rmi = rmi;
		
		try {
			this.player = this.rmi.login();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			ReceiverRmiInterface stub = (ReceiverRmiInterface) UnicastRemoteObject.exportObject(this, 0);
			// Bind the remote object's stub in the registry
            registry.rebind(this.player.getUserId().toString(), stub);
            
            
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public PlayerInstance login(){
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
			this.rmi.transmit(this.player, pack.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void receive(String msg) throws RemoteException {
		ReceiverTask r = new ReceiverTask(this.receiverListeners, msg);
		Thread receiving = new Thread(r);
		receiving.start();
	}

	public PlayerInstance getPlayer(){
		return this.player;
	}
	
	public void addReceiverListener(ReceiverListenerInterface r){
		this.receiverListeners.add(r);
	}
	
	public ArrayList<ReceiverListenerInterface> getReceiverListeners() {
		return receiverListeners;
	}
}
