/**
 * 
 */
package com.code.utils;

/**
 * @author puqingyu
 *
 */
public class DataBaseType {
	public static String getPojoType(String meta) throws Exception{
		String type = null;
		if(meta.equalsIgnoreCase("VARCHAR")){
			type = "String";
		}else if(meta.equalsIgnoreCase("INT")){
			type = "Integer";
		}else if(meta.equalsIgnoreCase("FLOAT")){
			type = "Float";
		}else if(meta.equalsIgnoreCase("TIMESTAMP")){
			type = "java.util.Date";
		}else if(meta.equalsIgnoreCase("BIGINT")){
			type = "Long";
		}else if(meta.equalsIgnoreCase("TEXT")){
			type = "String";
		}else{
			throw new Exception("没有找到对应的字段类型");
		}
		return type;
	}
}
