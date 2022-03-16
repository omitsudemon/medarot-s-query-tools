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
	
	public DbHandler (String p) throws IOException {
		this.path = p;
	}
	
    public void connect() throws IOException {
        
        if (this.path == null) throw new IOException("No se ha seleccionado DB");
        
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
	 * @param c Connection
	 * @param v Checkbox values. [armor, success, power, heating, cooldown, ammo, hv]
	 * @param a_type Ability Type
	 * @param a_name Ability Name
	 */
	
	private boolean emptyCheckboxes (int[] v) {
		return (v[0] == 0 && v[1] == 0 && v[2] == 0 && v[3] == 0 && v[4] == 0 && v[5] == 0);
	}
	
	/**
	 * 
	 * @param v
	 * @param a_type
	 * @param a_name
	 * @return
	 * @throws IOException
	 */
    public DefaultTableModel queryHeads(int[] v, Object a_type, Object a_name) throws IOException{
    	this.connect();
    	
        String sql = "SELECT ";
        
        if (this.emptyCheckboxes(v)) sql += "(model) as criteria, ";
        else {
        	sql += "(";
        	
        	StringJoiner sj = new StringJoiner("+");
        	
        	/*
        	 * Joins all the parameters checked
        	 */
        	if (v[0] == 1) sj.add("armor");
        	if (v[1] == 1) sj.add("success");
        	if (v[2] == 1) sj.add("power");
        	if (v[3] == 1) sj.add("heating");
        	if (v[4] == 1) sj.add("cooldown");
        	if (v[5] == 1) sj.add("ammo");
        	
        	sql += sj.toString();
        	sql += ") as criteria, ";
        }

        
        sql += "model, id, name, armor, success, power, heating, cooldown, ammo, hv, ability_type, ability_name, ability, rank_5 FROM head ";
        
        System.out.println(a_name + " " + a_type);
        
        if (v[6] == 1 && v[7] == 1) sql += " WHERE hv >= 0 ";
        if (v[6] == 1 && v[7] == 0) sql += " WHERE hv = 1 ";
        if (v[6] == 0 && v[7] == 1) sql += " WHERE hv = 0 ";
        
        if (a_type != null) {
        	String s_a_type = a_type.toString();
        	sql += " AND ability_type = '" + s_a_type + "' ";
        }
        
        if (a_name != null) {
        	String s_a_name = a_name.toString();
        	sql += " AND ability_name = '" + s_a_name + "' ";
        }
        
        if (this.emptyCheckboxes(v)) sql += "ORDER BY criteria ASC";
        else sql += "ORDER BY criteria DESC";
        
        System.out.println("Query: " + sql);
        
        try (Connection conn = this.conn;
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
        	
        	DefaultTableModel tableModel = new DefaultTableModel(Variables.columnNames, 0);
        	
            while (rs.next()) {
            	Object[] objs = {
            			rs.getString("model"),
            			rs.getString("name"),
            			rs.getInt("armor"),
            			rs.getInt("success"),
            			rs.getInt("power"),
            			rs.getInt("heating"),
            			rs.getInt("cooldown"),
            			rs.getInt("ammo"),
            			rs.getInt("hv"),
            			rs.getString("ability_type"),
            			rs.getString("ability_name"),
            			rs.getString("ability"),
            			rs.getString("rank_5"),
            	};
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
    
    public ArrayList<String> getAbilityNames (String part, String abilityType) throws IOException {
    	this.connect();
    	
        String sql = "SELECT DISTINCT ability_name FROM " + part + " WHERE ability_type = '" + abilityType + "'ORDER BY ability_name ASC";

        
        try (Connection conn = this.conn;
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
        	
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