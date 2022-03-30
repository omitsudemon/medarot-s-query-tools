package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.swing.table.DefaultTableModel;
import util.Variables;

public class DbHandler {

	String path = null;
	Connection conn = null;

	public DbHandler(String p) throws IOException {
		this.path = p;
	}

	public void connect() throws IOException {

		if (this.path == null)
			throw new IOException("No se ha seleccionado DB");

		try {
			String url = "jdbc:sqlite:" + this.path;
			this.conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void disconnect() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param c      Connection
	 * @param v      Checkbox values. [armor, success, power, heating, cooldown,
	 *               ammo, hv]
	 * @param a_type Ability Type
	 * @param a_name Ability Name
	 */

	private boolean emptyCheckboxesHead(int[] v) {
		return (v[0] == 0 && v[1] == 0 && v[2] == 0 && v[3] == 0 && v[4] == 0 && v[5] == 0);
	}

	private boolean emptyCheckboxesArms(int[] v) {
		return (v[0] == 0 && v[1] == 0 && v[2] == 0 && v[3] == 0 && v[4] == 0);
	}

	private boolean emptyCheckboxesLegs(int[] v) {
		return (v[0] == 0 && v[1] == 0 && v[2] == 0 && v[3] == 0 && v[4] == 0);
	}

	/**
	 * 
	 * @param v      Parameters selected to rank
	 * @param a_type Ability type
	 * @param a_name Ability name
	 * @param g      Genders
	 * @param o      Obtained at
	 * @return
	 * @throws IOException
	 */
	public DefaultTableModel queryHeads(int[] v, Object[] g, Object[] o, Object a_type, Object a_name)
			throws IOException {
		this.connect();

		String sql = "SELECT ";

		if (this.emptyCheckboxesHead(v))
			sql += "(model) as criteria, ";
		else {
			sql += "(";

			StringJoiner sj = new StringJoiner("+");

			/*
			 * Joins all the parameters checked
			 */
			if (v[0] == 1)
				sj.add("armor");
			if (v[1] == 1)
				sj.add("success");
			if (v[2] == 1)
				sj.add("power");
			if (v[3] == 1)
				sj.add("heating");
			if (v[4] == 1)
				sj.add("cooldown");
			if (v[5] == 1)
				sj.add("ammo");

			sql += sj.toString();
			sql += ") as criteria, ";
		}

		sql += "model, id, name, armor, success, power, heating, cooldown, ammo, hv, gender, ability_type, ability_name, ability, rank_5, obtained FROM head ";

		System.out.println(a_name + " " + a_type);

		if (v[6] == 1 && v[7] == 1)
			sql += " WHERE hv >= 0 ";
		if (v[6] == 1 && v[7] == 0)
			sql += " WHERE hv = 1 ";
		if (v[6] == 0 && v[7] == 1)
			sql += " WHERE hv = 0 ";

		if (a_type != null) {
			String s_a_type = a_type.toString();
			sql += " AND ability_type = '" + s_a_type + "' ";
		}

		if (a_name != null) {
			String s_a_name = a_name.toString();
			sql += " AND ability_name = '" + s_a_name + "' ";
		}

		sql += " AND gender in (";
		for (int i = 0; i < g.length; i++) {
			if (i == g.length - 1)
				sql += "'" + g[i].toString().toCharArray()[0] + "'";
			else
				sql += "'" + g[i].toString().toCharArray()[0] + "'" + ",";
		}
		sql += ") ";

		sql += " AND obtained in (";
		for (int i = 0; i < o.length; i++) {
			if (i == o.length - 1)
				sql += "'" + o[i] + "'";
			else
				sql += "'" + o[i] + "'" + ",";
		}
		sql += ") ";

		if (this.emptyCheckboxesHead(v))
			sql += "ORDER BY criteria ASC";
		else
			sql += "ORDER BY criteria DESC";

		System.out.println("Query: " + sql);

		try (Connection conn = this.conn;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			DefaultTableModel tableModel = new DefaultTableModel(Variables.head_columnNames, 0);

			while (rs.next()) {
				Object[] objs = { rs.getString("model"), rs.getString("name"), rs.getInt("armor"), rs.getInt("success"),
						rs.getInt("power"), rs.getInt("heating"), rs.getInt("cooldown"), rs.getInt("ammo"),
						rs.getInt("hv"), rs.getString("gender"), rs.getString("ability_type"),
						rs.getString("ability_name"), rs.getString("ability"), rs.getString("rank_5"),
						rs.getString("obtained"), };
				tableModel.addRow(objs);
			}

			this.disconnect();
			return tableModel;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		this.disconnect();
		return null;
	}

	/**
	 * 
	 * @param v      Parameters selected to rank
	 * @param a_type Ability type
	 * @param a_name Ability name
	 * @param g      Genders
	 * @param o      Obtained at
	 * @return
	 * @throws IOException
	 */
	public DefaultTableModel queryArms(String armtype, int[] v, Object[] g, Object[] o, Object a_type, Object a_name)
			throws IOException {
		this.connect();

		String sql = "SELECT ";

		if (this.emptyCheckboxesArms(v))
			sql += "(model) as criteria, ";
		else {
			sql += "(";

			StringJoiner sj = new StringJoiner("+");

			/*
			 * Joins all the parameters checked
			 */
			if (v[0] == 1)
				sj.add("armor");
			if (v[1] == 1)
				sj.add("success");
			if (v[2] == 1)
				sj.add("power");
			if (v[3] == 1)
				sj.add("heating");
			if (v[4] == 1)
				sj.add("cooldown");

			sql += sj.toString();
			sql += ") as criteria, ";
		}

		sql += "model, id, name, armor, success, power, heating, cooldown, hv, gender, ability_type, ability_name, ability, rank_5, obtained FROM " + armtype;

		System.out.println(a_name + " " + a_type);

		if (v[5] == 1 && v[6] == 1)
			sql += " WHERE hv >= 0 ";
		if (v[5] == 1 && v[6] == 0)
			sql += " WHERE hv = 1 ";
		if (v[5] == 0 && v[6] == 1)
			sql += " WHERE hv = 0 ";

		if (a_type != null) {
			String s_a_type = a_type.toString();
			sql += " AND ability_type = '" + s_a_type + "' ";
		}

		if (a_name != null) {
			String s_a_name = a_name.toString();
			sql += " AND ability_name = '" + s_a_name + "' ";
		}

		sql += " AND gender in (";
		for (int i = 0; i < g.length; i++) {
			if (i == g.length - 1)
				sql += "'" + g[i].toString().toCharArray()[0] + "'";
			else
				sql += "'" + g[i].toString().toCharArray()[0] + "'" + ",";
		}
		sql += ") ";

		sql += " AND obtained in (";
		for (int i = 0; i < o.length; i++) {
			if (i == o.length - 1)
				sql += "'" + o[i] + "'";
			else
				sql += "'" + o[i] + "'" + ",";
		}
		sql += ") ";

		if (this.emptyCheckboxesArms(v))
			sql += "ORDER BY criteria ASC";
		else
			sql += "ORDER BY criteria DESC";

		System.out.println("Query: " + sql);

		try (Connection conn = this.conn;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			DefaultTableModel tableModel = new DefaultTableModel(Variables.arms_columnNames, 0);

			while (rs.next()) {
				Object[] objs = { rs.getString("model"), rs.getString("name"), rs.getInt("armor"), rs.getInt("success"),
						rs.getInt("power"), rs.getInt("heating"), rs.getInt("cooldown"), rs.getInt("hv"),
						rs.getString("gender"), rs.getString("ability_type"), rs.getString("ability_name"),
						rs.getString("ability"), rs.getString("rank_5"), rs.getString("obtained"), };
				tableModel.addRow(objs);
			}

			this.disconnect();
			return tableModel;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		this.disconnect();
		return null;
	}

	/**
	 * 
	 * @param v      Parameters selected to rank
	 * @param a_type Ability type
	 * @param a_name Ability name
	 * @param g      Genders
	 * @param o      Obtained at
	 * @return
	 * @throws IOException
	 */
	public DefaultTableModel queryLegs(int[] v, Object[] g, Object[] o, Object[] types, Object a_name)
			throws IOException {
		this.connect();

		String sql = "SELECT ";

		if (this.emptyCheckboxesLegs(v))
			sql += "(model) as criteria, ";
		else {
			sql += "(";

			StringJoiner sj = new StringJoiner("+");

			/*
			 * Joins all the parameters checked
			 */
			if (v[0] == 1)
				sj.add("armor");
			if (v[1] == 1)
				sj.add("anti_melee");
			if (v[2] == 1)
				sj.add("anti_ranged");
			if (v[3] == 1)
				sj.add("anti_status");
			if (v[4] == 1)
				sj.add("base_speed");

			sql += sj.toString();
			sql += ") as criteria, ";
		}

		sql += "model, id, name, armor, anti_melee, anti_ranged, anti_status, base_speed, hv, type, gender, ability_name, ability, rank_5, obtained FROM leg ";

		StringJoiner sj2 = new StringJoiner(",");
		sql += " WHERE hv in (";
		
		if (v[5] == 1)
			sj2.add("0");
		if (v[6] == 1)
			sj2.add("1");
		if (v[7] == 1)
			sj2.add("2");
		if (v[8] == 1)
			sj2.add("3");
		sql += sj2.toString();
		
		sql += ") ";

		if (a_name != null) {
			String s_a_name = a_name.toString();
			sql += " AND ability_name = '" + s_a_name + "' ";
		}

		sql += " AND gender in (";
		for (int i = 0; i < g.length; i++) {
			if (i == g.length - 1)
				sql += "'"  + g[i].toString().toCharArray()[0] + "'";
			else
				sql += "'"  + g[i].toString().toCharArray()[0] + "'" + ",";
		}
		sql += ") ";

		sql += " AND obtained in (";
		for (int i = 0; i < o.length; i++) {
			if (i == o.length - 1)
				sql += "'"  + o[i] + "'";
			else
				sql += "'"  + o[i] + "'" + ",";
		}
		sql += ") ";
		
		sql += " AND type in (";
		for (int i = 0; i < types.length; i++) {
			if (i == types.length - 1)
				sql += "'"  + types[i].toString() + "'";
			else
				sql += "'"  + types[i].toString() + "'" + ",";
		}
		sql += ") ";

		if (this.emptyCheckboxesLegs(v))
			sql += "ORDER BY criteria ASC";
		else
			sql += "ORDER BY criteria DESC";

		System.out.println("Query: " + sql);

		try (Connection conn = this.conn;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			DefaultTableModel tableModel = new DefaultTableModel(Variables.legs_columnNames, 0);

			while (rs.next()) {
				Object[] objs = { rs.getString("model"), rs.getString("name"), rs.getInt("armor"), rs.getInt("anti_melee"),
						rs.getInt("anti_ranged"), rs.getInt("anti_status"), rs.getInt("base_speed"),
						rs.getInt("hv"), rs.getString("gender"), rs.getString("type"),
						rs.getString("ability_name"), rs.getString("ability"), rs.getString("rank_5"),
						rs.getString("obtained"), };
				tableModel.addRow(objs);
			}

			this.disconnect();
			return tableModel;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		this.disconnect();
		return null;
	}

	public ArrayList<String> getLegAbilities() throws IOException {
		this.connect();

		String sql = "SELECT DISTINCT ability_name from leg order by ability_name asc";

		try (Connection conn = this.conn;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			ArrayList<String> arr = new ArrayList<>();

			while (rs.next()) {
				arr.add(rs.getString("ability_name"));
			}

			this.disconnect();
			return arr;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		this.disconnect();
		return null;

	}

	public ArrayList<String> getAbilityNames(String part, String abilityType) throws IOException {
		this.connect();

		String sql = "SELECT DISTINCT ability_name FROM " + part + " WHERE ability_type = '" + abilityType
				+ "' ORDER BY ability_name ASC";

		try (Connection conn = this.conn;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			ArrayList<String> arr = new ArrayList<>();

			while (rs.next()) {
				arr.add(rs.getString("ability_name"));
			}

			this.disconnect();
			return arr;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		this.disconnect();
		return null;

	}
}