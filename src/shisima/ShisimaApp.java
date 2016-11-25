package shisima;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import shisima.ChatGui;
import shisima.ShisimaGui;

public class ShisimaApp extends JFrame {
	private static final long serialVersionUID = 1L;
	private LayoutManager layout;
	
	public ShisimaApp(){
		
		// create application frame and set visible
		layout = new FlowLayout();
		this.setLayout(layout);
		
		this.add(new ChatGui());
		this.add(new ShisimaGui());
		
		this.pack();
		
	    this.setVisible(true);
	    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new ShisimaApp();
	}
}
