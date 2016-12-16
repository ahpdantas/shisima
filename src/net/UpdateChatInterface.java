package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateChatInterface extends Remote {
	void updateChat(String userName, String msg) throws RemoteException;
}
