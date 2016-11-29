package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.ShisimaApp;
import net.ReceiverListener;
import net.ShisimaPacket;

public class ExitListener implements ActionListener, ReceiverListener {
	private ShisimaApp app;
	
	public ExitListener(ShisimaApp app){
		this.app = app;
		app.network.addReceiverListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int reply = JOptionPane.showConfirmDialog(null, "Do you really want to close the Shisima Game?", "Exit Shisima Game", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	ShisimaPacket pack = new ShisimaPacket("","","","close");
        	try {
				this.app.network.send(pack);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	System.exit(0);
        }
        		
	}

	@Override
	public void receive(String message) {
		String[]msg = message.split(":");
		System.out.println(message);
		
		System.out.println(msg.length);
		
		if( msg.length == 4 && !msg[3].isEmpty() ){
			System.out.println(msg[3]);
			if( msg[3].equals("close")){
				JOptionPane.showMessageDialog(null, "The other player closed Shisima Game.");
				System.exit(0);
			}
		}
		
	}

}
