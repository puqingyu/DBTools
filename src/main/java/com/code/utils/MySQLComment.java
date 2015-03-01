/**
 * 
 */
package com.code.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author puqingyu
 *
 */
public class MySQLComment {

	public static Map getCommentByTableName(Connection conn, List tableName) throws Exception {
		Map map = new HashMap();
		Statement stmt = conn.createStatement();
		for (int i = 0; i < tableName.size(); i++) {
			String table = (String) tableName.get(i);
			ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
			if (rs != null && rs.next()) {
				String create = rs.getString(2);
				String comment = parse(create);
				map.put(table, comment);
			}
			rs.close();
		}
		stmt.close();
		conn.close();
		return map;
	}


	public static String parse(String all) {
		String comment = null;
		int index = all.indexOf("COMMENT='");
		if (index < 0) {
			return "";
		}
		comment = all.substring(index + 9);
		comment = comment.substring(0, comment.length() - 1);
		return comment;
	}

}
