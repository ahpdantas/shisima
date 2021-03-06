package app;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import gui.ChatGui;
import gui.ShisimaGui;
import listeners.IpAddressListener;
import listeners.NewListener;
import listeners.PortListener;
import listeners.UserNameListener;
import net.NetworkService;

public class ShisimaApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String userName = "user";
	

	private String ipAddress = "127.0.0.1";
	private int port = 5000;
	private LayoutManager layout;
	
	public  NetworkService network;
	public ChatGui chat;
	public ShisimaGui shisima;
	
	public JMenuItem restartSubMenu;
	public JMenuItem exitSubMenu;
	
	public ShisimaApp(){
		// create application frame and set visible
		layout = new FlowLayout();
		this.setLayout(layout);
		this.setTitle("Shisima Game");
		
		createJMenuBar();
		
	    this.setVisible(true);
	    this.setSize(600, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void createJMenuBar(){
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("New",KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Start New Game");
		menuItem.addActionListener(new NewListener(this)); 
		menu.add(menuItem);
		
		menu.addSeparator();

		this.restartSubMenu = new JMenuItem("Restart",KeyEvent.VK_R);
		this.restartSubMenu.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.ALT_MASK));
		this.restartSubMenu.getAccessibleContext().setAccessibleDescription("Restart the Game");
		 
		menu.add(this.restartSubMenu);
		
		menu.addSeparator();
		
		this.exitSubMenu = new JMenuItem("Exit",KeyEvent.VK_X);
		this.exitSubMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));	
		
		menu.add(this.exitSubMenu);
		
		//Build second menu in the menu bar.
		menu = new JMenu("Configure");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription("Configure game options");
		
		menuItem = new JMenuItem("User name", KeyEvent.VK_U);
		menuItem.getAccessibleContext().setAccessibleDescription("Choose a new Username");
		menuItem.addActionListener(new UserNameListener(this)); 
		menu.add(menuItem);
		
		//a submenu
		menu.addSeparator();
		
		submenu = new JMenu("Network Settings");
		submenu.setMnemonic(KeyEvent.VK_S);
		
		menuItem = new JMenuItem("I.P Address",KeyEvent.VK_I);
		menuItem.addActionListener(new IpAddressListener(this) ); 
		submenu.add(menuItem);

		menuItem = new JMenuItem("Port",KeyEvent.VK_P);
		menuItem.addActionListener(new PortListener(this));
		submenu.add(menuItem);
		menu.add(submenu);
		
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		new ShisimaApp();
	}

}
