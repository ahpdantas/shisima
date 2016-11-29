package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.ShisimaApp;

public class PortListener implements ActionListener {
	private ShisimaApp shisima;
	
	public PortListener(ShisimaApp shisima){
		this. shisima = shisima;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JLabel message = new JLabel("Choose a new port:");
		JTextField port = new JTextField(Integer.toString(shisima.getPort()));
		Object[] texts = {message, port};
		JOptionPane.showMessageDialog(null,texts);
		shisima.setPort(Integer.valueOf(port.getText()));
	}

}
