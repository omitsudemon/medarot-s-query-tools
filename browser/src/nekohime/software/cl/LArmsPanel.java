package nekohime.software.cl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import db.DbHandler;
import util.Variables;

public class LArmsPanel extends JPanel {

	private JFrame frame;
	private DbHandler db;
	private JTable table;
	private JScrollPane scrollPane;
	private String partName;
	private String[] columnNames;
	private int[] selectedColumnSize;

	public LArmsPanel(JFrame f, DbHandler d) {
		this.frame = f;
		this.db = d;
		this.partName = "left_arm";
		this.columnNames = Variables.arms_columnNames;
		this.selectedColumnSize = Variables.armsColumnSize;
		this.initialize();
	}

	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}

	private void arrayListChckbxListener(ArrayList<String> obt, String label, JCheckBox chckbx) {
		if (obt.size() == 1 && !chckbx.isSelected()) {
			chckbx.setSelected(true);
			return;
		}
		if (obt.size() > 0 && !chckbx.isSelected() && obt.contains(label)) {
			obt.remove(label);
		}
		if (obt.size() > 0 && chckbx.isSelected() && !obt.contains(label)) {
			obt.add(label);
		}
	}

	private int[] getCheckedBoxes(JCheckBox[] jcb) {
		int[] a = new int[jcb.length];

		for (int i = 0; i < jcb.length; i++) {
			a[i] = jcb[i].isSelected() ? 1 : 0;
		}

		return a;
	}

	private void initialize() {
		this.setLayout(null);

		JLabel label_info = new JLabel("Select your Query");
		label_info.setBounds(10, 11, 115, 14);
		this.add(label_info);

		JCheckBox chbx_armor = new JCheckBox("ARMOR");
		chbx_armor.setBounds(10, 101, 97, 23);
		this.add(chbx_armor);

		JCheckBox chbx_success = new JCheckBox("SUCCESS");
		chbx_success.setBounds(10, 127, 97, 23);
		this.add(chbx_success);

		JCheckBox chbx_power = new JCheckBox("POWER");
		chbx_power.setBounds(120, 127, 97, 23);
		this.add(chbx_power);

		JCheckBox chbx_heating = new JCheckBox("HEATING");
		chbx_heating.setBounds(10, 153, 97, 23);
		this.add(chbx_heating);

		JCheckBox chbx_cooldown = new JCheckBox("COOLDOWN");
		chbx_cooldown.setBounds(120, 153, 97, 23);
		this.add(chbx_cooldown);

		JCheckBox chbx_hv = new JCheckBox("HV");
		chbx_hv.setBounds(120, 101, 97, 23);

		JCheckBox chbx_nonhv = new JCheckBox("NON-HV");
		chbx_nonhv.setBounds(219, 101, 97, 23);

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
		this.add(chbx_hv);
		this.add(chbx_nonhv);

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

					comboBox_anames.removeAllItems();

					ArrayList<String> anames = db.getAbilityNames(partName,
							comboBox_atypes.getSelectedItem().toString());

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

		this.add(comboBox_atypes);
		this.add(comboBox_anames);

		JLabel lblNewLabel = new JLabel("Ability Type");
		lblNewLabel.setBounds(10, 38, 115, 14);
		this.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Ability Name");
		lblNewLabel_2.setBounds(10, 72, 115, 14);
		this.add(lblNewLabel_2);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 228, 800, 321);
		this.add(this.scrollPane);

		this.table = new JTable(data, this.columnNames);
		this.table.setRowSelectionAllowed(false);
		this.scrollPane.setViewportView(this.table);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton btn_clear_atype = new JButton("");
		btn_clear_atype.setIcon(new ImageIcon(Window.class.getResource("/res/icons8-trash-30.png")));
		btn_clear_atype.setBackground(Color.WHITE);
		btn_clear_atype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_atypes.setSelectedIndex(-1);
				comboBox_anames.setSelectedIndex(-1);
			}
		});
		btn_clear_atype.setBounds(323, 26, 27, 31);
		this.add(btn_clear_atype);

		JButton btn_clear_aname = new JButton("");
		btn_clear_aname.setIcon(new ImageIcon(Window.class.getResource("/res/icons8-trash-30.png")));
		btn_clear_aname.setBackground(Color.WHITE);
		btn_clear_aname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_anames.setSelectedIndex(-1);
			}
		});
		btn_clear_aname.setBounds(323, 63, 27, 31);
		this.add(btn_clear_aname);

		ArrayList<String> gender_types = new ArrayList<>();
		gender_types.add("Male");
		gender_types.add("Female");
		gender_types.add("Neutral");

		JCheckBox chckbx_male = new JCheckBox("Male");
		chckbx_male.setSelected(true);
		chckbx_male.setBounds(323, 101, 97, 23);
		chckbx_male.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(gender_types, "Male", chckbx_male);
			}
		});
		this.add(chckbx_male);

		JCheckBox chckbx_female = new JCheckBox("Female");
		chckbx_female.setSelected(true);
		chckbx_female.setBounds(323, 127, 97, 23);
		chckbx_female.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(gender_types, "Female", chckbx_female);
			}
		});
		this.add(chckbx_female);

		JCheckBox chckbx_neutral = new JCheckBox("Neutral");
		chckbx_neutral.setSelected(true);
		chckbx_neutral.setBounds(323, 153, 97, 23);
		chckbx_neutral.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(gender_types, "Neutral", chckbx_neutral);
			}
		});
		this.add(chckbx_neutral);

		JLabel lblNewLabel_3 = new JLabel("Obtainability");
		lblNewLabel_3.setBounds(516, 11, 97, 14);
		this.add(lblNewLabel_3);

		/**
		 * Obtainability Checkboxes
		 */

		ArrayList<String> obtain_types = new ArrayList<>();
		obtain_types.add("Start of game");

		JCheckBox chckbx_startgame = new JCheckBox("Start of game");
		chckbx_startgame.setSelected(true);
		chckbx_startgame.setBounds(448, 34, 124, 23);
		chckbx_startgame.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Start of game", chckbx_startgame);
			}
		});
		this.add(chckbx_startgame);

		JCheckBox chckbx_gacha = new JCheckBox("Gacha");
		chckbx_gacha.setBounds(448, 63, 97, 23);
		chckbx_gacha.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Gacha", chckbx_gacha);
			}
		});
		this.add(chckbx_gacha);

		JCheckBox chckbx_pugacha = new JCheckBox("PU Gacha");
		chckbx_pugacha.setBounds(448, 89, 97, 23);
		chckbx_pugacha.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "PU Gacha", chckbx_pugacha);
			}
		});
		this.add(chckbx_pugacha);

		JCheckBox chckbx_crossover = new JCheckBox("Crossover");
		chckbx_crossover.setBounds(448, 115, 97, 23);
		chckbx_crossover.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Crossover", chckbx_crossover);
			}
		});
		this.add(chckbx_crossover);

		JCheckBox chckbx_fiercerobattle = new JCheckBox("Fierce Robattle");
		chckbx_fiercerobattle.setBounds(448, 141, 124, 23);
		chckbx_fiercerobattle.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Fierce Robattle", chckbx_fiercerobattle);
			}
		});
		this.add(chckbx_fiercerobattle);

		JCheckBox chckbx_event = new JCheckBox("Event");
		chckbx_event.setBounds(574, 34, 97, 23);
		chckbx_event.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Event", chckbx_event);
			}
		});
		this.add(chckbx_event);

		JCheckBox chckbx_fpgacha = new JCheckBox("FP Gacha");
		chckbx_fpgacha.setBounds(574, 63, 97, 23);
		chckbx_fpgacha.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "FP Gacha", chckbx_fpgacha);
			}
		});
		this.add(chckbx_fpgacha);

		JCheckBox chckbx_mlgacha = new JCheckBox("ML Gacha");
		chckbx_mlgacha.setBounds(574, 89, 97, 23);
		chckbx_mlgacha.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "ML Gacha", chckbx_mlgacha);
			}
		});
		this.add(chckbx_mlgacha);

		JCheckBox chckbx_special = new JCheckBox("Special");
		chckbx_special.setBounds(574, 115, 97, 23);
		chckbx_special.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(obtain_types, "Special", chckbx_special);
			}
		});
		this.add(chckbx_special);

		// EXECUTE

		JButton btn_execute = new JButton("Execute");
		btn_execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox[] jcba = { chbx_armor, chbx_success, chbx_power, chbx_heating, chbx_cooldown,
						chbx_hv, chbx_nonhv };

				try {
					DefaultTableModel tableModel = db.queryArms(getCheckedBoxes(jcba), gender_types.toArray(),
							obtain_types.toArray(), comboBox_atypes.getSelectedItem(),
							comboBox_anames.getSelectedItem());
					table.setModel(tableModel);

					TableColumnModel tcm = table.getColumnModel();
					for (int i = 0; i < tcm.getColumnCount(); i++) {
						tcm.getColumn(i).setPreferredWidth(selectedColumnSize[i]);
					}
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment(JLabel.CENTER);
					table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
					table.setColumnModel(tcm);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_execute.setBounds(261, 193, 89, 23);
		this.add(btn_execute);
	}

}
