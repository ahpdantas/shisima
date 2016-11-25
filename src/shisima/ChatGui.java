package shisima;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGui extends JPanel {
	private LayoutManager layout;
	
	public ChatGui(){
     
		layout = new BorderLayout();
		this.setLayout(layout);
				
	    JTextArea messageLog = new JTextArea(5,20);
	    messageLog.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
	    messageLog.setEditable(false);
	    
	    JTextField message = new JTextField(20);
	    message.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
	    
	    JLabel lblMsgLog     = new JLabel("Message Log");
	    JLabel lblMsg        = new JLabel("Mensage");
	    
	    JButton btnSend = new JButton("Send");
	    btnSend.setToolTipText("Send Message");
	    JButton btnExit           = new JButton("Exit");
	    btnExit.setToolTipText("Exit Chat");
	    
	    //btnSend.addActionListener(this);
	    //btnSair.addActionListener(this);
	    //btnSend.addKeyListener(this);
	    //txtMsg.addKeyListener(this);
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
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 400);
    }
	
	
}
