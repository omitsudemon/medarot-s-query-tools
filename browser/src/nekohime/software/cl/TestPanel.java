package nekohime.software.cl;

import javax.swing.JPanel;
import javax.swing.JButton;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPanel() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(158, 105, 89, 23);
		add(btnNewButton);

	}

}
