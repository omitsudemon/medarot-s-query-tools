package nekohime.software.cl;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Canvas;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import util.Variables;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import db.DbHandler;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import nekohime.software.cl.Test;
import java.awt.Font;
import javax.swing.JTextArea;

public class Window {

	private JFrame frame;
	private DbHandler db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String checkLocalDbPath() {

		File db = new File("medarot-s.db");

		if (db.canRead())
			return "medarot-s.db";
		else
			return null;
	}

	public String getDbPath() {
		String result = null;

		try {
			File myObj = new File("dbpath.txt");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				result = myReader.nextLine();
			}
			myReader.close();
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("File not found...");
			return result;
		}
	}

	public void saveDbPath(String path) {
		try {
			File myObj = new File("dbpath.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}

			try {
				FileWriter myWriter = new FileWriter("dbpath.txt");
				myWriter.write(path);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public String requestDbPath() {
		String dbpath;
		final JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Medarot Database", "db");
		jfc.setFileFilter(filter);
		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			dbpath = selectedFile.getAbsolutePath();
			this.saveDbPath(dbpath);
			return dbpath;
		} else {
			System.exit(0);
		}
		return null;
	}

	/**
	 * Create the application.
	 */
	public Window() {
		String dbpath = this.checkLocalDbPath();
		if (dbpath == null) {
			dbpath = this.getDbPath();
			if (dbpath == null) {
				JOptionPane.showMessageDialog(null,
						"Please select the Medarot Database, \nyou can find it inside the project. \nThis will generate a file with the path \nand will be used as reference for\nthe next time you open this. \n                                                  Omitsu.");
				dbpath = this.requestDbPath();
			}
		}

		try {
			this.db = new DbHandler(dbpath);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Medarot S Parts Browser");
		frame.setBounds(100, 100, 948, 714);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel lbl_medarot_tinpet = new JLabel("");
		lbl_medarot_tinpet.setBounds(57, -26, 825, 824);
		lbl_medarot_tinpet.setIcon(new ImageIcon(Window.class.getResource("/res/tinpet.png")));
		panel.add(lbl_medarot_tinpet);
		tabbedPane.addTab("Home", null, panel, null);
		
		JTextArea txtrSupBroYou = new JTextArea();
		txtrSupBroYou.setLineWrap(true);
		txtrSupBroYou.setWrapStyleWord(true);
		txtrSupBroYou.setFont(new Font("Consolas", Font.BOLD, 14));
		txtrSupBroYou.setBounds(634, 47, 253, 143);
		txtrSupBroYou.setText("Sup bro, you can do some browsing here, if you find some bugs or errors in the data, feel free to notify Omitsu or make a pull request in the repository. I hope you enjoy the program!");
		panel.add(txtrSupBroYou);
		
		JLabel lblNewLabel = new JLabel("Mr. Tinpet");
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 16));
		lblNewLabel.setBounds(634, 21, 120, 14);
		panel.add(lblNewLabel);

		HeadsPanel hp = new HeadsPanel(this.frame, this.db);
		tabbedPane.addTab("Heads", null, hp, null);

		LArmsPanel lap = new LArmsPanel(this.frame, this.db);
		tabbedPane.addTab("L.Arms", null, lap, null);

		RArmsPanel rap = new RArmsPanel(this.frame, this.db);
		tabbedPane.addTab("R.Arms", null, rap, null);

		LegsPanel lp = new LegsPanel(this.frame, this.db);
		tabbedPane.addTab("Legs", null, lp, null);

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				Rectangle r = frame.getBounds();
				int h = r.height;
				int w = r.width;
				Rectangle new_r = new Rectangle(10, 228, w - 50 - 10, h - 50 - 228 - 5 - 50);
				hp.getScrollPane().setBounds(new_r);
				lap.getScrollPane().setBounds(new_r);
				rap.getScrollPane().setBounds(new_r);
				lp.getScrollPane().setBounds(new_r);
			}
		});
	}
}
