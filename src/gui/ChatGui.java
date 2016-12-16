package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.Chat;
import net.RemoteGameService;
import net.UpdateChatInterface;
import net.UpdateGameStatusInterface;

public class ChatGui extends JPanel implements ActionListener, KeyListener, UpdateChatInterface {
	private static final long serialVersionUID = 1L;
	private LayoutManager layout;
	private JTextArea messageLog;
	private JTextField message;
	private JButton btnSend;
	private Chat chat;

	public ChatGui(String userName, RemoteGameService gameService){
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			UpdateChatInterface stub = (UpdateChatInterface) UnicastRemoteObject.exportObject(this, 0);
			// Bind the remote object's stub in the registry
            registry.rebind(gameService.getPlayer().getUserId().toString()+":chat", stub);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
     
		layout = new BorderLayout();
		this.setLayout(layout);
		
		this.chat = new Chat(userName,gameService);
		
	    messageLog = new JTextArea(15,10);
	    messageLog.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
	    messageLog.setEditable(false);
	    
	    message = new JTextField(20);
	    message.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
	    message.addKeyListener(this);
	    
	    JLabel lblMsgLog     = new JLabel("Message Log");
	    JLabel lblMsg        = new JLabel("Mensage");
	    
	    btnSend = new JButton("Send");
	    btnSend.setToolTipText("Send Message");
	    btnSend.addActionListener(this);
	    btnSend.addKeyListener(this);
	    
	    JScrollPane scroll = new JScrollPane(messageLog);
	    messageLog.setLineWrap(true);  
		
	    JPanel ChatPanel = new JPanel();
	    ChatPanel.setLayout(new BoxLayout(ChatPanel,BoxLayout.Y_AXIS ));
	    ChatPanel.add(lblMsgLog);
	    ChatPanel.add(scroll);
		ChatPanel.add(lblMsg);
		ChatPanel.add(message);
		
		this.add(ChatPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel(new GridLayout());
		buttonsPanel.add(btnSend);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.setBackground(Color.LIGHT_GRAY);
		
	}
	
	public void setUsername(String userName){
		this.chat.setUserName(userName);
	}
	
	public void updateMessageLog(String message){
		messageLog.append( "You said: "+ message + "\r\n");
	}
	
	@Override
	public void updateChat(String userName, String msg) {
		// TODO Auto-generated method stub
		messageLog.append(userName + " said: "+ msg + "\r\n");
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	chat.SendMessage(message.getText()); 
    	updateMessageLog(message.getText());
    }

	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			chat.SendMessage(message.getText());
			updateMessageLog(message.getText());
			JTextField field = (JTextField)key.getSource();
			field.setText("");
	   }                     
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
