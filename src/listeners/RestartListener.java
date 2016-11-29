package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.ShisimaApp;
import core.ChatGui;
import gui.ShisimaGui;
import net.ReceiverListener;
import net.ShisimaPacket;

public class RestartListener implements ActionListener, ReceiverListener {
	private ShisimaApp app;
	
	public RestartListener(ShisimaApp app) {
		this.app = app;
		app.network.addReceiverListener(this);
	}
	
	private void restartGame(){
		app.remove(app.chat);
		app.remove(app.shisima);
		app.chat = new ChatGui(app.getUserName(),app.network);
		app.shisima = new ShisimaGui(app.network);
		app.add(app.chat);
		app.add(app.shisima);
		app.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int reply = JOptionPane.showConfirmDialog(null, "Do you really want to restart the Shisima Game?", "Exit Shisima Game", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	ShisimaPacket pack = new ShisimaPacket("","","","restart");
        	try {
				this.app.network.send(pack);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	restartGame();
        }
	}

	@Override
	public void receive(String message) {
		String[]msg = message.split(":");
		System.out.println(message);
		
		System.out.println(msg.length);
		
		if( msg.length == 4 && !msg[3].isEmpty() ){
			System.out.println(msg[3]);
			if( msg[3].equals("restart")){
				JOptionPane.showMessageDialog(null, "The other player restarted Shisima Game.");
				restartGame();
			}
		}
		
	}


}
