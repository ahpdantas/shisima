package app;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import core.ChatGui;
import gui.ShisimaGui;
import net.NetworkService;

public class ShisimaServer extends JFrame {
	private static final long serialVersionUID = 1L;
	private LayoutManager layout;
	
	
	public ShisimaServer(){
		// create application frame and set visible
		layout = new FlowLayout();
		this.setLayout(layout);
		this.setTitle("Shisima Game Server");
		
		
		NetworkService network = new NetworkService(5000);
		ChatGui chat = new ChatGui(network);
				
		ShisimaGui shisima = new ShisimaGui();
		
		this.add(chat);
		this.add(shisima);
		
		this.pack();
		
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		new ShisimaServer();
	}
}
