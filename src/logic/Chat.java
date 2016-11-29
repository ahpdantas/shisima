package logic;

import javax.swing.JOptionPane;

import net.NetworkService;
import net.ShisimaPacket;

public class Chat {
	private NetworkService network;
	private String userName;
		
	public Chat(String userName, NetworkService network){
		this.userName = userName;
		this.network = network;
	}
	
	public void SendMessage(String msg) {
		try{
			ShisimaPacket pack = new ShisimaPacket(this.userName,msg,"","");
			network.send(pack);
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
