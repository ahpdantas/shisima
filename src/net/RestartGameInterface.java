package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RestartGameInterface extends Remote {
	void RestartGame() throws RemoteException;

}
