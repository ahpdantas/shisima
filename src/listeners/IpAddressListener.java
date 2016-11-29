package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.ShisimaApp;

public class IpAddressListener implements ActionListener {
	private ShisimaApp shisima;
	
	public IpAddressListener(ShisimaApp shisima){
		this. shisima = shisima;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JLabel message = new JLabel("New I.P Address:");
		JTextField address = new JTextField(shisima.getIpAddress());
		Object[] texts = {message, address};
		JOptionPane.showMessageDialog(null,texts);
		shisima.setIpAddress(address.getText());
	}

}
