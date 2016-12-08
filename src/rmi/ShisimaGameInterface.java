package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ShisimaGameInterface extends Remote {
	Player login() throws RemoteException;
	void publish(Player p, String msg) throws RemoteException;
	String receive(Player p) throws RemoteException;
}
