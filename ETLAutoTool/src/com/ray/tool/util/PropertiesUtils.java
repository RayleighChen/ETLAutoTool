package com.ray.tool.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils{
	/**
	 * 读取res/db.properties文件,根据key获取value
	 * @param key
	 * @return
	 */
	public static String getValue(String propertiesFile, String key) {
		Properties p = System.getProperties();
		String path = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(p.getProperty("os.name") != null && p.getProperty("os.name").toUpperCase().startsWith("WINDOW")) {
			path = path.substring(1, path.length() - 1);
		} else {
			path = path.substring(0, path.length() - 1);
		}
		path = path.substring(0, path.lastIndexOf('/'));
		p = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(path + propertiesFile);
			p.load(is);
			return p.getProperty(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取属性文件的路径,支持window和linux系统的路径选取
	 * @return
	 */
	public static String getPropertiesPath() {
		Properties p = System.getProperties();
		String path = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(p.getProperty("os.name") != null && p.getProperty("os.name").toUpperCase().startsWith("WINDOW")) {
			path = path.substring(1, path.length() - 1);
		} else {
			path = path.substring(0, path.length() - 1);
		}
		path = path.substring(0, path.lastIndexOf('/'));
		return path;
	}
}
