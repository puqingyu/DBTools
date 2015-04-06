/**
 * 
 */
package com.code.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.code.utils.DataBaseType;
import com.code.utils.FileUtils;

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
	 * 根据表名获得pojo类名
	 * @param connection
	 * @param tableName
	 * @param dbType
	 * @param path
	 * @param isCreateFile
	 * @param split
	 * @return
	 */
	public static String getPojoName(String tableName,String split) {
		if(split!=null && !split.trim().equals("")){
			String[] tags = tableName.split(split);
			StringBuffer pojoName = new StringBuffer();
			for (int i = 0, n = tags.length; i < n; i++) {
				pojoName.append(tags[i].substring(0, 1).toUpperCase() + tags[i].subSequence(1, tags[i].length()));
			}
			return pojoName.toString();
		}
		return tableName = tableName.substring(0, 1).toUpperCase() + tableName.subSequence(1, tableName.length());
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
			String head = "package com.tanwei.bar.base.model;";
			sb.append(head);
			sb.append(LINE);
			//获得表的注释
			sql = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'dev' and table_name = '"+tableName+"'";
			PreparedStatement ps1 = null;
			ResultSet rs1 = null;
			ps1 = connection.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			rs1.next();
			sb.append("/**").append(LINE);
			sb.append(" * @author pqy").append(LINE);
			sb.append(" * ").append(rs1.getString(1)).append(LINE);
			sb.append("**/").append(LINE);
			String oldName = tableName;
			tableName = getPojoName(tableName, "_");
			sb.append("public class " + tableName + " {");
			sb.append(LINE);
			boolean isBigCia = false;
			for (int i = 1; i <= columnCount; i++) {
//				sb.append(TAB);
				sql = "SELECT COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+oldName+"' AND table_schema = 'dev' and COLUMN_NAME = '"+md.getColumnName(i)+"'";
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				ps2 = connection.prepareStatement(sql);
				rs2 = ps2.executeQuery();
				rs2.next();
				sb.append(TAB).append("/**").append(LINE);
				sb.append(TAB).append(" * ").append(rs2.getString(1)).append(LINE);
				sb.append(TAB).append("**/").append(LINE);
				sb.append(TAB).append("private "
						+ DataBaseType.getPojoType(md.getColumnTypeName(i)) + " "
						+ md.getColumnName(i) + ";");
//				System.out.println(md.getColumnTypeName(i));
				// System.out.println("name : " + md.getColumnName(i) +
				// " ,type :"
				// + md.getColumnTypeName(i));
				sb.append(LINE);
			}
			for (int i = 1; i <= columnCount; i++) {
				sb.append(TAB);
				String pojoType = DataBaseType.getPojoType(md.getColumnTypeName(i));
				if(pojoType.equals("BigDecimal")){
					isBigCia = true;
				}
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
//			System.out.println(sb.toString());
			if(isBigCia){
				sb = new StringBuffer(sb.toString().replace(head, head+LINE+"import java.math.BigDecimal;"));
				sb.append(LINE);
			}
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
		return getConnection("115.29.198.188:3306", "dev", "dev",
				"dev", MYSQL);
	}
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		Connection con = getConnection();
		List<String> tables = showTables(con);
		for (int i = 0, n = tables.size(); i < n; i++) {
			table2pojo(con, tables.get(i), MYSQL, "", true);
		}
		con.close();
	}
}
