package nekohime.software.cl;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Test extends JPanel{

	public Test (){
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(72, 71, 89, 23);
		add(btnNewButton);
	}
}
