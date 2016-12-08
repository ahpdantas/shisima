package app;


import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gui.ChatGui;
import gui.ShisimaGui;
import listeners.NewListener;
import listeners.UserNameListener;
import net.NetworkService;
import net.ShisimaPacket;
import rmi.TransmitRmiInterface;

public class ShisimaApp extends JFrame{
	private static final long serialVersionUID = 1L;
	public NetworkService network;
	private String userName = "user";
	private LayoutManager layout;
	
	public ChatGui chat;
	public ShisimaGui shisima;
	
	public JMenuItem restartSubMenu;
	public JMenuItem exitSubMenu;
	

	public ShisimaApp(NetworkService net) {
		this.network = net;
		
		this.layout = new FlowLayout();
		this.setLayout(layout);
		this.setTitle("Shisima Game");
		
		createJMenuBar();
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(ShisimaApp.this, 
		            "Are you sure to close this window?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	ShisimaApp.this.network.send(new ShisimaPacket("","","","close"));
		            System.exit(0);
		        }
		    }});
	    this.setVisible(true);
	    this.setSize(600, 600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private void createJMenuBar(){
		JMenuBar menuBar;
		JMenu menu;
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
		
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
	}

	public static void main(String[] args) {
				
        try {
            Registry registry = LocateRegistry.getRegistry(null);
            ShisimaApp player = new ShisimaApp( 
            		new NetworkService((TransmitRmiInterface) registry.lookup("Shisima"))); 
         } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
