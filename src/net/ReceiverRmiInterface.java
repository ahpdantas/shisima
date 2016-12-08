package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.PlayerInstance;

public interface ReceiverRmiInterface extends Remote {
	void receive(String msg) throws RemoteException;
}
