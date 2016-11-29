package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.ShisimaApp;
import core.ChatGui;
import gui.ShisimaGui;
import net.NetworkService;

public class NewListener implements ActionListener{
	private ShisimaApp app;
	
	public NewListener(ShisimaApp app){
		this. app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if( app.network != null || app.chat != null || app.shisima != null ){
			JOptionPane.showMessageDialog(null, "There is a empty game running!!!");
			return;
		}
		
		try{
			app.network = new NetworkService(app.getIpAddress(),app.getPort());
		}catch(java.net.ConnectException ex){
			app.network = new NetworkService(app.getPort());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		app.restartSubMenu.addActionListener(new RestartListener(app));
		app.exitSubMenu.addActionListener(new ExitListener(app));
		
		
		app.chat = new ChatGui(app.getUserName(),app.network);
		app.shisima = new ShisimaGui(app.network);
		
		app.add(app.chat);
		app.add(app.shisima);
		
		app.pack();
		
	}

}
