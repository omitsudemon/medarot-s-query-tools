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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class LegsPanel extends JPanel {

	private JFrame frame;
	private DbHandler db;
	private JTable table;
	private JScrollPane scrollPane;
	private String partName;
	private String[] columnNames;
	private int[] selectedColumnSize;

	public LegsPanel(JFrame f, DbHandler d) {
		this.frame = f;
		this.db = d;
		this.partName = "leg";
		this.columnNames = Variables.legs_columnNames;
		this.selectedColumnSize = Variables.legsColumnSize;
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

	private boolean isAlone(int[] a, int index) {

		for (int i = 0; i < a.length; i++) {
			if (i == index)
				continue;
			if (a[i] != 0)
				return false;
		}

		return true;
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

		JCheckBox chbx_anti_melee = new JCheckBox("ANTI-MELEE");
		chbx_anti_melee.setBounds(10, 127, 125, 23);
		this.add(chbx_anti_melee);

		JCheckBox chbx_anti_ranged = new JCheckBox("ANTI-RANGED");
		chbx_anti_ranged.setBounds(168, 127, 115, 23);
		this.add(chbx_anti_ranged);

		JCheckBox chbx_anti_status = new JCheckBox("ANTI-STATUS");
		chbx_anti_status.setBounds(10, 153, 125, 23);
		this.add(chbx_anti_status);

		JCheckBox chbx_base_speed = new JCheckBox("BASE SPEED");
		chbx_base_speed.setBounds(168, 153, 115, 23);
		this.add(chbx_base_speed);

		JLabel lblNewLabel_1 = new JLabel("Results: ");
		lblNewLabel_1.setBounds(34, 197, 75, 14);
		add(lblNewLabel_1);
		
		JLabel lbl_results = new JLabel("");
		lbl_results.setBounds(90, 197, 46, 14);
		add(lbl_results);
		
		JCheckBox chbx_hv0 = new JCheckBox("HV 0");
		chbx_hv0.setSelected(true);
		chbx_hv0.setBounds(388, 193, 97, 23);

		JCheckBox chbx_hv1 = new JCheckBox("HV 1");
		chbx_hv1.setBounds(487, 193, 97, 23);

		JCheckBox chbx_hv2 = new JCheckBox("HV 2");
		chbx_hv2.setBounds(586, 193, 97, 23);

		JCheckBox chbx_hv3 = new JCheckBox("HV 3");
		chbx_hv3.setBounds(685, 193, 97, 23);

		int[] leg_hv_query = { 1, 0, 0, 0 };

		chbx_hv0.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = 0;
				if (!chbx_hv0.isSelected()) {
					if (isAlone(leg_hv_query, index)) {
						chbx_hv0.setSelected(true);
					} else {
						leg_hv_query[index] = 0;
					}
				} else {
					leg_hv_query[index] = 1;
				}
			}
		});

		chbx_hv1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = 1;
				if (!chbx_hv1.isSelected()) {
					if (isAlone(leg_hv_query, index)) {
						chbx_hv1.setSelected(true);
					} else {
						leg_hv_query[index] = 0;
					}
				} else {
					leg_hv_query[index] = 1;
				}
			}
		});

		chbx_hv2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = 2;
				if (!chbx_hv2.isSelected()) {
					if (isAlone(leg_hv_query, index)) {
						chbx_hv2.setSelected(true);
					} else {
						leg_hv_query[index] = 0;
					}
				} else {
					leg_hv_query[index] = 1;
				}
			}
		});

		chbx_hv3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = 3;
				if (!chbx_hv3.isSelected()) {
					if (isAlone(leg_hv_query, index)) {
						chbx_hv3.setSelected(true);
					} else {
						leg_hv_query[index] = 0;
					}
				} else {
					leg_hv_query[index] = 1;
				}
			}
		});

		this.add(chbx_hv0);
		this.add(chbx_hv1);
		this.add(chbx_hv2);
		this.add(chbx_hv3);

		JComboBox comboBox_anames = new JComboBox();
		comboBox_anames.setBounds(167, 51, 159, 23);
		comboBox_anames.setSelectedItem(null);

		try {
			ArrayList<String> anames = db.getLegAbilities();

			for (int i = 0; i < anames.size(); i++) {
				comboBox_anames.addItem(anames.get(i));
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		comboBox_anames.setEnabled(true);
		comboBox_anames.setSelectedIndex(-1);

		this.add(comboBox_anames);

		JLabel lblNewLabel_2 = new JLabel("Ability Name");
		lblNewLabel_2.setBounds(20, 55, 115, 14);
		this.add(lblNewLabel_2);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 228, 800, 321);
		this.add(this.scrollPane);

		Object[][] data = {};

		this.table = new JTable(data, this.columnNames);
		this.table.setRowSelectionAllowed(false);
		this.scrollPane.setViewportView(this.table);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton btn_clear_aname = new JButton("");
		btn_clear_aname.setIcon(new ImageIcon(Window.class.getResource("/res/icons8-trash-30.png")));
		btn_clear_aname.setBackground(Color.WHITE);
		btn_clear_aname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_anames.setSelectedIndex(-1);
			}
		});
		btn_clear_aname.setBounds(333, 46, 27, 31);
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
		obtain_types.add("Gacha");
		obtain_types.add("Fierce Robattle");
		obtain_types.add("FP Gacha");
		//
		//obtain_types.add("PU Gacha");
		//obtain_types.add("Crossover");
		//obtain_types.add("Event");
		//obtain_types.add("ML Gacha");
		//obtain_types.add("Special");

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

		chckbx_gacha.setSelected(true);
		chckbx_fiercerobattle.setSelected(true);
		chckbx_fpgacha.setSelected(true);

		ArrayList<String> leg_types = new ArrayList<String>();
		leg_types.add("Biped");
		
		JLabel lblNewLabel = new JLabel("Leg Type");
		lblNewLabel.setBounds(759, 11, 89, 14);
		add(lblNewLabel);
		
		JCheckBox chckbx_biped = new JCheckBox("Biped");
		chckbx_biped.setSelected(true);
		chckbx_biped.setBounds(695, 34, 97, 23);
		chckbx_biped.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Biped", chckbx_biped);
			}
		});
		add(chckbx_biped);
		
		JCheckBox chckbx_hover = new JCheckBox("Hover");
		chckbx_hover.setBounds(695, 63, 97, 23);
		chckbx_hover.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Hover", chckbx_hover);
			}
		});
		add(chckbx_hover);
		
		JCheckBox chckbx_vehicle = new JCheckBox("Vehicle");
		chckbx_vehicle.setBounds(695, 89, 97, 23);
		chckbx_vehicle.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Vehicle", chckbx_vehicle);
			}
		});
		add(chckbx_vehicle);
		
		JCheckBox chckbx_tank = new JCheckBox("Tank");
		chckbx_tank.setBounds(695, 115, 97, 23);
		chckbx_tank.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Tank", chckbx_tank);
			}
		});
		add(chckbx_tank);
		
		JCheckBox chckbx_flight = new JCheckBox("Flight");
		chckbx_flight.setBounds(830, 34, 97, 23);
		chckbx_flight.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Flight", chckbx_flight);
			}
		});
		add(chckbx_flight);
		
		JCheckBox chckbx_multiped = new JCheckBox("Multiped");
		chckbx_multiped.setBounds(830, 63, 97, 23);
		chckbx_multiped.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Multiped", chckbx_multiped);
			}
		});
		add(chckbx_multiped);
		
		JCheckBox chckbx_sea = new JCheckBox("Sea");
		chckbx_sea.setBounds(830, 89, 97, 23);
		chckbx_sea.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				arrayListChckbxListener(leg_types, "Sea", chckbx_sea);
			}
		});
		add(chckbx_sea);
		
		//
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(682, 11, 1, 162);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(388, 174, 531, 2);
		add(separator_1);
		
		// EXECUTE

		JButton btn_execute = new JButton("Execute");
		btn_execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox[] jcba = { chbx_armor, chbx_anti_melee, chbx_anti_ranged, chbx_anti_status, chbx_base_speed, chbx_hv0, chbx_hv1, chbx_hv2, chbx_hv3};

				try {
					DefaultTableModel tableModel = db.queryLegs(getCheckedBoxes(jcba), gender_types.toArray(),
							obtain_types.toArray(), leg_types.toArray(), comboBox_anames.getSelectedItem());
					table.setModel(tableModel);

					TableColumnModel tcm = table.getColumnModel();
					for (int i = 0; i < tcm.getColumnCount(); i++) {
						tcm.getColumn(i).setPreferredWidth(selectedColumnSize[i]);
					}
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment(JLabel.CENTER);
					table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
					table.setColumnModel(tcm);
					lbl_results.setText( ""+table.getRowCount() );


				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_execute.setBounds(261, 193, 89, 23);
		this.add(btn_execute);
		
		JButton btn_allobtained = new JButton("All");
		btn_allobtained.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!obtain_types.contains("Start of game")) {
					obtain_types.add("Start of game");
					chckbx_startgame.setSelected(true);
				}
				if (!obtain_types.contains("Gacha")) {
					obtain_types.add("Gacha");
					chckbx_gacha.setSelected(true);
				}
				if (!obtain_types.contains("Fierce Robattle")) {
					obtain_types.add("Fierce Robattle");
					chckbx_fiercerobattle.setSelected(true);
				}
				if (!obtain_types.contains("FP Gacha")) {
					obtain_types.add("FP Gacha");
					chckbx_fpgacha.setSelected(true);
				}
				if (!obtain_types.contains("PU Gacha")) {
					obtain_types.add("PU Gacha");
					chckbx_pugacha.setSelected(true);
				}
				if (!obtain_types.contains("Crossover")) {
					obtain_types.add("Crossover");
					chckbx_crossover.setSelected(true);
				}
				if (!obtain_types.contains("Event")) {
					obtain_types.add("Event");
					chckbx_event.setSelected(true);
				}
				if (!obtain_types.contains("ML Gacha")) {
					obtain_types.add("ML Gacha");
					chckbx_mlgacha.setSelected(true);
				}
				if (!obtain_types.contains("Special")) {
					obtain_types.add("Special");
					chckbx_special.setSelected(true);
				}
			}
		});
		btn_allobtained.setBounds(578, 141, 54, 23);
		add(btn_allobtained);
		
		JButton btn_alltypes = new JButton("All");
		btn_alltypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!leg_types.contains("Biped")) {
					leg_types.add("Biped");
					chckbx_biped.setSelected(true);
				}
				if (!leg_types.contains("Flight")) {
					leg_types.add("Flight");
					chckbx_flight.setSelected(true);
				}
				if (!leg_types.contains("Hover")) {
					leg_types.add("Hover");
					chckbx_hover.setSelected(true);
				}
				if (!leg_types.contains("Vehicle")) {
					leg_types.add("Vehicle");
					chckbx_vehicle.setSelected(true);
				}
				if (!leg_types.contains("Multiped")) {
					leg_types.add("Multiped");
					chckbx_multiped.setSelected(true);
				}
				if (!leg_types.contains("Sea")) {
					leg_types.add("Sea");
					chckbx_sea.setSelected(true);
				}
				if (!leg_types.contains("Tank")) {
					leg_types.add("Tank");
					chckbx_tank.setSelected(true);
				}
			}
		});
		btn_alltypes.setBounds(830, 127, 54, 23);
		add(btn_alltypes);
		
		JButton btn_allhv = new JButton("All");
		btn_allhv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leg_hv_query[0] = 1;
				leg_hv_query[1] = 1;
				leg_hv_query[2] = 1;
				leg_hv_query[3] = 1;
				chbx_hv0.setSelected(true);
				chbx_hv1.setSelected(true);
				chbx_hv2.setSelected(true);
				chbx_hv3.setSelected(true);
			}
		});
		btn_allhv.setBounds(783, 193, 54, 23);
		add(btn_allhv);
	}
}
