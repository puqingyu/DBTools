/**
 * 
 */
package com.code.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * @author puqingyu
 * 
 */
public class DBUtils {
	public static final int MYSQL = 1;
	private static final String LINE = "\r\n";
	private static final String TAB = "\t";

	/**
	 * 经由过程jdbc获取响应的数据库链接connection
	 */
	public static Connection getConnection(String ipport, String dbName,
			String username, String password, int type)
			throws ClassNotFoundException, SQLException {
		String jdbcString = null;
		if (type == MYSQL) {
			jdbcString = "jdbc:mysql://" + ipport + "/" + dbName;
			Class.forName("com.mysql.jdbc.Driver");
		}
		Connection connection = null;
		connection = DriverManager.getConnection(jdbcString, username, password);
		return connection;
	}

	/**
	 * 数据库表生成响应的java类,生陈规矩 类名= 表名(第一个字母大写) 属性名= 数据库列名 get/set办法 = 按照标准生成
	 * 此中生成的根蒂根基类型均为包装类,例如Integer ,Long ,Boolean ,String ＠param connection
	 * ＠param tableName ＠param dbType ＠param path ＠param isCreateFile ＠return
	 * ＠throws SQLException
	 */
	public static String table2pojo(Connection connection, String tableName,
			int dbType, String path, boolean isCreateFile) {
		try {
			String sql = "select * from " + tableName + " where 1 <> 1";
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			StringBuffer sb = new StringBuffer();
			tableName = tableName.substring(0, 1).toUpperCase() + tableName.subSequence(1, tableName.length());
			sb.append("public class " + tableName + " {");
			sb.append(LINE);
			for (int i = 1; i <= columnCount; i++) {
				sb.append(TAB);
				sb.append("private "
						+ DataBaseType.getPojoType(md.getColumnTypeName(i)) + " "
						+ md.getColumnName(i) + ";");
				System.out.println(md.getColumnTypeName(i));
				// System.out.println("name : " + md.getColumnName(i) +
				// " ,type :"
				// + md.getColumnTypeName(i));
				sb.append(LINE);
			}
			for (int i = 1; i <= columnCount; i++) {
				sb.append(TAB);
				String pojoType = DataBaseType.getPojoType(md.getColumnTypeName(i));
				String columnName = md.getColumnName(i);
				String getName = null;
				String setName = null;
				if (columnName.length() > 1) {
					getName = "public " + pojoType + " get"
							+ columnName.substring(0, 1).toUpperCase()
							+ columnName.substring(1, columnName.length()) + "() {";
					setName = "public void set"
							+ columnName.substring(0, 1).toUpperCase()
							+ columnName.substring(1, columnName.length()) + "("
							+ pojoType + " " + columnName + ") {";
				} else {
					getName = "public get" + columnName.toUpperCase() + "() {";
					setName = "public set" + columnName.toUpperCase() + "("
							+ pojoType + " " + columnName + ") {";
				}
				sb.append(LINE).append(TAB).append(getName);
				sb.append(LINE).append(TAB).append(TAB);
				sb.append("return " + columnName + ";");
				sb.append(LINE).append(TAB).append("}");
				sb.append(LINE);
				sb.append(LINE).append(TAB).append(setName);
				sb.append(LINE).append(TAB).append(TAB);
				sb.append("this." + columnName + " = " + columnName + ";");
				sb.append(LINE).append(TAB).append("}");
				sb.append(LINE);
			}
			sb.append("}");
			System.out.println(sb.toString());
			if (isCreateFile)
				FileUtils.stringToFile(tableName + ".java", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> showTables(Connection connection){
		List<String> tables = new LinkedList<String>();
		try {
			String sql = "show tables";
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				tables.add(rs.getString(1));
				System.out.println(tables.get(tables.size()-1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return tables;
		}
	}
	
	/**
	 * 用于调试,懒得写响应的参数
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		return getConnection("121.40.70.137:3306", "fbcircle", "wificrm",
				"wificrm", MYSQL);
	}
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		Connection con = getConnection();
		List<String> tables = showTables(con);
		for (int i = 0, n = tables.size(); i < n; i++) {
			table2pojo(con, tables.get(i), MYSQL, "", true);
		}
	}
}
