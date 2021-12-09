
package com.utilities.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class ConfigReader {
	

	/**
	 * will read the properties file with this function
	 * @param filePath
	 * @return
	 */
	private ConfigReader(){
		
	}
	public static Properties loadPropertyFile(String filePath) {
		File file = new File(filePath);
		Properties prop = new Properties();

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch (Exception e) {
			LogUtil.errorLog(ConfigReader.class, "Caught the exception", e);
		}
		return prop;

	}
	/**
	 * will get sting value from properties file
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {

		Properties prop = loadPropertyFile("src/main/resources/config.properties");
		 return prop.getProperty(key);
	}
	/**
	 * will get int value from properties file
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		Properties prop = loadPropertyFile("src/main/resources/config.properties");

		String strKey = prop.getProperty(key);

		return Integer.parseInt(strKey);
	}

}
