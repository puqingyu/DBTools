/**
 * 
 */
package com.code.mybatis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.code.utils.DBUtils;

/**
 * @author puqingyu
 * 自动生成dto dao mapper-xml
 */
public class BatisDBUtils {
	public static void createXml(String modFile){
		try {
			Connection con = DBUtils.getConnection("115.29.198.188:3306", "dev", "dev",
					"dev", DBUtils.MYSQL);
			List<String> tableNames = DBUtils.showTables(con);
			//获得table模板
			BufferedReader br = new BufferedReader(new FileReader(BatisDBUtils.class.getResource("/table.xml.mod").getPath()));
			String tableModel = br.readLine();
			StringBuffer tableStr = new StringBuffer();
			for (int i = 0, n = tableNames.size(); i < n; i++) {
				tableStr.append(tableModel.replace("{{{tableName}}}", tableNames.get(i)).replace("{{{pojoName}}}", DBUtils.getPojoName(tableNames.get(i), "_")));
				tableStr.append("\n");
			}
			System.out.println(tableStr);
			//生成根据模板生成配置文件
			BufferedReader br2 = new BufferedReader(new FileReader(BatisDBUtils.class.getResource("/generatorConfig.xml.mod").getPath()));
			StringBuffer confModel = new StringBuffer();
			String temp = null;
			while((temp=br2.readLine())!=null){
				confModel.append(temp).append("\n");
			}
			String conf = confModel.toString().replace("{{{tables}}}", tableStr);
			FileWriter fw = new FileWriter(modFile);
			fw.write(conf);
			con.close();
			br.close();
			br2.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
	    boolean overwrite = true;  
	    //读取配置文件  
//	    File configFile = new File("generatorConfig.xml");  
	    ConfigurationParser cp = new ConfigurationParser(warnings);  
	    Configuration config;
	    try {
	    	String modFile = "/generatorConfig.xml";
	    	String modFilePath = new File(BatisDBUtils.class.getResource("/generatorConfig.xml.mod").getPath()).getParentFile().getPath()+modFile;
	    	createXml(modFilePath);
	    	InputStream in = BatisDBUtils.class.getResourceAsStream(modFile);
	        config = cp.parseConfiguration(in);

	        DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
	        MyBatisGenerator myBatisGenerator;  
	        try {  
	            myBatisGenerator = new MyBatisGenerator(config, callback,  
	                    warnings);  
	            myBatisGenerator.generate(null);  
	              
	            //打印结果  
	            for(String str : warnings){  
	                System.out.println(str);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        in.close();
	    } catch (IOException e1) {  
	        e1.printStackTrace();  
	    } catch (XMLParserException e2) {  
	        e2.printStackTrace();  
	    } 
	}
}