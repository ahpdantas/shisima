package net;

import java.rmi.Remote;

public interface UpdateChatInterface extends Remote {
	void updateChat(String userName, String msg);
}
