package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;
import app.ShisimaApp;
import net.CloseGameInterface;
import net.RestartGameInterface;


public class ExitListener implements ActionListener, CloseGameInterface {
	private ShisimaApp app;
	
	public ExitListener(ShisimaApp app){
		this.app = app;
		try {
			Registry registry = LocateRegistry.getRegistry();
			RestartGameInterface stub = (RestartGameInterface) UnicastRemoteObject.exportObject(this, 0);
			// Bind the remote object's stub in the registry
            registry.rebind(this.app.remoteGame.getPlayer().getUserId().toString()+":close", stub);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int reply = JOptionPane.showConfirmDialog(null, "Do you really want to close the Shisima Game?", "Exit Shisima Game", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	try {
				this.app.remoteGame.closeGame();
				} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	System.exit(0);
        }
        		
	}


	@Override
	public void closeGame() {
		JOptionPane.showMessageDialog(null, "The other player closed Shisima Game.");
		System.exit(0);
	}

}
