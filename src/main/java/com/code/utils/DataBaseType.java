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
		}else if(meta.equalsIgnoreCase("INT") || meta.equalsIgnoreCase("INT UNSIGNED")){
			type = "int";
		}else if(meta.equalsIgnoreCase("FLOAT")){
			type = "float";
		}else if(meta.equalsIgnoreCase("TIMESTAMP")){
			type = "java.util.Date";
		}else if(meta.equalsIgnoreCase("BIGINT") || meta.equalsIgnoreCase("BIGINT UNSIGNED")){
			type = "long";
		}else if(meta.equalsIgnoreCase("TEXT")){
			type = "String";
		}else if(meta.equalsIgnoreCase("DECIMAL")){
			type = "BigDecimal";
		}else{
			System.out.println("meta:"+meta);
			throw new Exception("没有找到对应的字段类型");
		}
		return type;
	}
}
