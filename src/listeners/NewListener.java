package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.ShisimaApp;
import gui.ChatGui;
import gui.ShisimaGui;

public class NewListener implements ActionListener{
	private ShisimaApp app;
	
	public NewListener(ShisimaApp app){
		this. app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if( app.chat != null || app.shisima != null ){
			JOptionPane.showMessageDialog(null, "There is a empty game running!!!");
			return;
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
