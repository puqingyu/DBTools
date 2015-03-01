/**
 * 
 */
package com.code.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author puqingyu
 *
 */
public class FileUtils {
	private final static String path = "/home/puqingyu/project_other/tanwei-bar/src/main/java/com/tanwei/bar/base/model";
	public static void stringToFile(String filename,String content) {
		try {
			File f = new File(path + File.separator + filename);
			if(!f.exists()){
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
