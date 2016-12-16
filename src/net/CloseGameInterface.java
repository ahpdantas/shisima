package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CloseGameInterface extends Remote {
	void closeGame() throws RemoteException;
}
