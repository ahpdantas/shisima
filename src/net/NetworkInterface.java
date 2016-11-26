package net;

public interface NetworkInterface {
	public void send(String msg);
	public void addChatListener(ChatReceiveListener l);
}
