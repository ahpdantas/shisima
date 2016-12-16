package logic;

import javax.swing.JOptionPane;
import net.RemoteGameService;


public class Chat {
	private RemoteGameService remoteGame;
	private String userName;
		
	public Chat(String userName, RemoteGameService remoteGame){
		this.userName = userName;
		this.remoteGame = remoteGame;
	}
	
	public void SendMessage(String msg) {
		try{
			remoteGame.sendMessage(this.userName,msg);
		}catch( Exception e){
			JOptionPane.showMessageDialog(null, "Waiting for a Client connection.");
		}
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
