package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransmitRmiInterface extends Remote {
	PlayerInstance login() throws RemoteException;
	void transmit(PlayerInstance p, String msg) throws RemoteException;
}

