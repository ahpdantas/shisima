package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.ChatReceiveListener;
import net.NetworkService;

public class ChatGui extends JPanel implements ActionListener, KeyListener, ChatReceiveListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LayoutManager layout;
	private JTextArea messageLog;
	private JTextField message;
	private JButton btnSend;
	private JButton btnExit;
	private NetworkService network;
	
	public ChatGui(NetworkService network){
     
		layout = new BorderLayout();
		this.setLayout(layout);
		
		this.network = network;
		network.addChatListener(this);
		
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
	    
	    btnExit = new JButton("Exit");
	    btnExit.setToolTipText("Exit Chat");
	    btnExit.addActionListener(this);
	    
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
		buttonsPanel.add(btnExit);
		buttonsPanel.add(btnSend);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.setBackground(Color.LIGHT_GRAY);
		
	}
	public void SendMessage(String msg) throws IOException{
		network.sendChatMessage(msg);
	}
	
	
	public void Exit(){
		
	}
	
	@Override
	public void receiveMsg(String message) {
		messageLog.append(message);
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 400);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
             
      try {
         if(e.getActionCommand().equals(btnSend.getActionCommand())){
        	 SendMessage(message.getText()); 
         } else if(e.getActionCommand().equals(btnExit.getActionCommand())){
        	 Exit(); 
         }
      } catch (IOException e1) {
    	  // TODO Auto-generated catch block
          e1.printStackTrace();
      }                       
    }

	@Override
	public void keyPressed(KeyEvent key) {
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			try {
				SendMessage(message.getText());
			} catch (IOException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}                                                          
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
