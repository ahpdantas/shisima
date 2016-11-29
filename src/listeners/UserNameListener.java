package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.ShisimaApp;

public class UserNameListener implements ActionListener {
	private ShisimaApp shisima;
	
	public UserNameListener(ShisimaApp shisima){
		this. shisima = shisima;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JLabel message = new JLabel("Choose a new user name:");
		JTextField username = new JTextField(shisima.getUserName());
		Object[] texts = {message, username};
		JOptionPane.showMessageDialog(null,texts);
		shisima.setUserName(username.getText());
	}

}
