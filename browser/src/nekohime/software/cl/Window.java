package nekohime.software.cl;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import java.awt.Canvas;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
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

public class Window {

	private JFrame frame;
	private JTable table;
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
		String dbpath = this.getDbPath();

		if (dbpath == null) {
			dbpath = this.requestDbPath();
		}

		try {
			this.db = new DbHandler(dbpath);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initialize();
	}

	private int[] getCheckedBoxes(JCheckBox[] jcb) {
		int[] a = new int[jcb.length];

		for (int i = 0; i < jcb.length; i++) {
			a[i] = jcb[i].isSelected() ? 1 : 0;
		}

		return a;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 841, 627);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setToolTipText("Home");
		tabbedPane.addTab("Home", null, panel, null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Window.class.getResource("/res/tinpet.png")));
		panel.add(lblNewLabel_1);

		JPanel panel_head = new JPanel();
		tabbedPane.addTab("Head", null, panel_head, null);
		panel_head.setLayout(null);

		JLabel label_info = new JLabel("Select your Query");
		label_info.setBounds(10, 11, 115, 14);
		panel_head.add(label_info);

		JCheckBox chbx_armor = new JCheckBox("ARMOR");
		chbx_armor.setBounds(10, 93, 97, 23);
		panel_head.add(chbx_armor);

		JCheckBox chbx_success = new JCheckBox("SUCCESS");
		chbx_success.setBounds(10, 119, 97, 23);
		panel_head.add(chbx_success);

		JCheckBox chbx_power = new JCheckBox("POWER");
		chbx_power.setBounds(120, 119, 97, 23);
		panel_head.add(chbx_power);

		JCheckBox chbx_heating = new JCheckBox("HEATING");
		chbx_heating.setBounds(10, 145, 97, 23);
		panel_head.add(chbx_heating);

		JCheckBox chbx_cooldown = new JCheckBox("COOLDOWN");
		chbx_cooldown.setBounds(120, 145, 97, 23);
		panel_head.add(chbx_cooldown);

		JCheckBox chbx_ammo = new JCheckBox("AMMO");
		chbx_ammo.setBounds(120, 171, 97, 23);
		panel_head.add(chbx_ammo);

		JCheckBox chbx_hv = new JCheckBox("HV");
		chbx_hv.setBounds(120, 93, 97, 23);

		JCheckBox chbx_nonhv = new JCheckBox("NON-HV");
		chbx_nonhv.setBounds(219, 93, 97, 23);

		chbx_hv.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!chbx_hv.isSelected() && !chbx_nonhv.isSelected())
					chbx_nonhv.setSelected(true);
			}
		});

		chbx_nonhv.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!chbx_nonhv.isSelected() && !chbx_hv.isSelected())
					chbx_hv.setSelected(true);
			}
		});

		chbx_hv.setSelected(true);
		chbx_nonhv.setSelected(true);
		panel_head.add(chbx_hv);
		panel_head.add(chbx_nonhv);

		Object[][] data = {};

		JComboBox comboBox_atypes = new JComboBox();
		comboBox_atypes.setBounds(157, 34, 159, 23);
		for (int i = 0; i < Variables.abilityTypes.length; i++) {
			comboBox_atypes.addItem(Variables.abilityTypes[i]);
		}
		comboBox_atypes.setSelectedItem(null);

		JComboBox comboBox_anames = new JComboBox();
		comboBox_anames.setEnabled(false);
		comboBox_anames.setBounds(157, 68, 159, 23);
		comboBox_anames.setSelectedItem(null);

		comboBox_atypes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (comboBox_atypes.getSelectedIndex() == -1) {
						comboBox_anames.removeAllItems();
						comboBox_anames.setEnabled(false);
						return;
					}

					ArrayList<String> anames = db.getAbilityNames("head", comboBox_atypes.getSelectedItem().toString());

					for (int i = 0; i < anames.size(); i++) {
						comboBox_anames.addItem(anames.get(i));
					}

					comboBox_anames.setEnabled(true);
					comboBox_anames.setSelectedIndex(-1);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_head.add(comboBox_atypes);
		panel_head.add(comboBox_anames);

		JLabel lblNewLabel = new JLabel("Ability Type");
		lblNewLabel.setBounds(10, 38, 115, 14);
		panel_head.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Ability Name");
		lblNewLabel_2.setBounds(10, 72, 115, 14);
		panel_head.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 228, 800, 321);
		panel_head.add(scrollPane);

		table = new JTable(data, Variables.columnNames);
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton btn_clear_atype = new JButton("");
		btn_clear_atype.setIcon(new ImageIcon(Window.class.getResource("/res/icons8-trash-30.png")));
		btn_clear_atype.setBackground(Color.WHITE);
		btn_clear_atype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_atypes.setSelectedIndex(-1);
				comboBox_anames.setSelectedIndex(-1);
			}
		});
		btn_clear_atype.setBounds(323, 34, 27, 31);
		panel_head.add(btn_clear_atype);

		JButton btn_clear_aname = new JButton("");
		btn_clear_aname.setIcon(new ImageIcon(Window.class.getResource("/res/icons8-trash-30.png")));
		btn_clear_aname.setBackground(Color.WHITE);
		btn_clear_aname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_anames.setSelectedIndex(-1);
			}
		});
		btn_clear_aname.setBounds(323, 68, 27, 31);
		panel_head.add(btn_clear_aname);

		JButton btn_execute = new JButton("Execute");
		btn_execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox[] jcba = { chbx_armor, chbx_success, chbx_power, chbx_heating, chbx_cooldown, chbx_ammo,
						chbx_hv, chbx_nonhv };
				try {
					DefaultTableModel tableModel = db.queryHeads(getCheckedBoxes(jcba),
							comboBox_atypes.getSelectedItem(), comboBox_anames.getSelectedItem());
					table.setModel(tableModel);
					
					TableColumnModel tcm = table.getColumnModel();
					for (int i = 0; i < tcm.getColumnCount(); i++) {
						tcm.getColumn(i).setPreferredWidth(Variables.headColumnSize[i]);
					}
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment( JLabel.CENTER );
					table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
					table.setColumnModel(tcm);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_execute.setBounds(214, 194, 89, 23);
		panel_head.add(btn_execute);

		/***/

		JPanel panel_larms = new JPanel();
		tabbedPane.addTab("L.Arm", null, panel_larms, null);

		JPanel panel_rarms = new JPanel();
		tabbedPane.addTab("New tab", null, panel_rarms, null);

		JPanel panel_legs = new JPanel();
		tabbedPane.addTab("New tab", null, panel_legs, null);
	}
}
