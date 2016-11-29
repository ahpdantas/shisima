package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

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
		try{
			app.network = new NetworkService(app.getIpAddress(),app.getPort());
		}catch(java.net.ConnectException ex){
			app.network = new NetworkService(app.getPort());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		app.chat = new ChatGui(app.network);
		app.shisima = new ShisimaGui();
		
		app.add(app.chat);
		app.add(app.shisima);
		app.pack();
		
	}

}
