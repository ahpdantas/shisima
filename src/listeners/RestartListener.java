package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import app.ShisimaApp;
import gui.ChatGui;
import gui.ShisimaGui;
import net.RestartGameInterface;

public class RestartListener implements ActionListener, RestartGameInterface {
	private ShisimaApp app;
	
	public RestartListener(ShisimaApp app) {
		this.app = app;
		try {
			Registry registry = LocateRegistry.getRegistry();
			RestartGameInterface stub = (RestartGameInterface) UnicastRemoteObject.exportObject(this, 0);
			// Bind the remote object's stub in the registry
            registry.rebind(this.app.remoteGame.getPlayer().getUserId().toString()+":restart", stub);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int reply = JOptionPane.showConfirmDialog(null, "Do you really want to restart the Shisima Game?", "Exit Shisima Game", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	try {
				this.app.remoteGame.restartGame();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		this.app.shisima.reset();
        }
	}

	@Override
	public void RestartGame() {
		Thread t = new Thread(new Runnable(){
	        public void run(){
	        	JOptionPane.showMessageDialog(null, "The other player restarted Shisima Game.");
	    		app.shisima.reset();
	        }
	    });
	  t.start();
	}


}
