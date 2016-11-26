package app;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import core.ChatService;
import gui.ShisimaGui;
import net.NetworkService;

public class ShisimaClient extends JFrame {
	private static final long serialVersionUID = 1L;
	private LayoutManager layout;
	
	
	public ShisimaClient(){
		// create application frame and set visible
		layout = new FlowLayout();
		this.setLayout(layout);
		
		
		NetworkService network = new NetworkService("127.0.0.1", 5000);
		ChatService chat = new ChatService(network);
				
		ShisimaGui shisima = new ShisimaGui();
		
		this.add(chat);
		this.add(shisima);
		
		this.pack();
		
	    this.setVisible(true);
	    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		new ShisimaClient();
	}
}
