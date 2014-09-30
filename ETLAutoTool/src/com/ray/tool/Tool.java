package com.ray.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.wc.SVNRevision;

import schemasMicrosoftComOfficeOffice.STInsetMode;

public class Tool {
	private static Properties props;
	private static FileInputStream fis;

	static {
		props = new Properties();
		try {
			fis = new FileInputStream("res/SDM.properties");
			props.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SVNUtil.getInstance().update(props.getProperty("rootpath"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		SVNUtil.getInstance().update(props.getProperty("sdmfilsall"),
				SVNRevision.BASE, SVNDepth.INFINITY);
	}

	private static void copy(String source, String target) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldFile = new File(source);
			if (oldFile.exists()) {
				InputStream inStream = new FileInputStream(oldFile);
				FileOutputStream fs = new FileOutputStream(target);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Map<String, ArrayList<String>> getChangeVersion() {
		// TODO Auto-generated method stub
		SVNUtil.getInstance().update(props.getProperty("rootpath"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		Map<String, ArrayList<String>> versions = new HashMap<String, ArrayList<String>>();
		ArrayList<String> filesName = null;

		try {
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(props
					.getProperty("filepath")));
			XSSFSheet sheet = xwb.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);

			for (int i = sheet.getFirstRowNum() + 1; i < sheet
					.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (!cell.toString().equals("")
						&& versions.get(cell.toString()) == null) {
					filesName = new ArrayList<String>();
					String[] simpleFile = row.getCell(9).toString().split("\n");
					for (int j = 0; j < simpleFile.length; j++) {
						String tempFile = simpleFile[j] + "(" + row.getCell(7)
								+ ").xls";
						filesName.add(tempFile);
					}
					versions.put(cell.toString(), filesName);
				} else if (!cell.toString().equals("")
						&& versions.get(cell.toString()) != null) {
					filesName = versions.get(cell.toString());
					String[] simpleFile = row.getCell(9).toString().split("\n");
					for (int j = 0; j < simpleFile.length; j++) {
						String tempFile = simpleFile[j] + "(" + row.getCell(7)
								+ ").xls";
						filesName.add(tempFile);
					}
					versions.put(cell.toString(), filesName);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return versions;
	}

	public String[] readFolder() {
		File file = new File(props.getProperty("rootpath"));
		if (file.isDirectory()) {
			String[] fileList = file.list();
			return fileList;
		}
		return null;
	}

	public void createFileTree(String root, ArrayList<String> filesList)
			throws Exception {
		SVNUtil.getInstance().update(props.getProperty("rootpath"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		SVNUtil.getInstance().update(props.getProperty("sdmfilsall"),
				SVNRevision.BASE, SVNDepth.INFINITY);

		File rootFolder = new File(props.getProperty("rootpath") + root);
		if (!rootFolder.exists()) {
			if (!rootFolder.mkdir()) {
				throw new Exception("目录不存在，创建失败！");
			}
			copy(props.getProperty("modelfilepath").toString()
					+ props.getProperty("modelfile1name").toString(), props
					.getProperty("rootpath")
					+ root + "/" + props.getProperty("modelfile1name"));
		}
		new File(props.getProperty("rootpath") + root + "/PDM").mkdir();
		new File(props.getProperty("rootpath") + root + "/PDM/SDM").mkdir();

		File SDMFolder = new File(props.getProperty("sdmfilsall"));
		String[] existFilesList = removeNotExist((String[]) filesList
				.toArray(new String[filesList.size()]), SDMFolder
				.list(new XlsFilter()));

		for (int i = 0; i < existFilesList.length; i++) {
			copy(
					props.getProperty("sdmfilsall").toString()
							+ existFilesList[i], props.getProperty("rootpath")
							+ root + "/PDM/SDM/" + existFilesList[i]);
		}
		new File(props.getProperty("rootpath") + root + "/PDM/SDM/log").mkdir();
		new File(props.getProperty("rootpath") + root + "/PDM/初始化").mkdir();
		new File(props.getProperty("rootpath") + root + "/PDM/初始化/log").mkdir();
		new File(props.getProperty("rootpath") + root + "/SA").mkdir();
		new File(props.getProperty("rootpath") + root + "/打包").mkdir();
		new File(props.getProperty("rootpath") + root + "/应用").mkdir();
		copy(props.getProperty("modelfilepath").toString()
				+ props.getProperty("modelfile2name").toString(), props
				.getProperty("rootpath")
				+ root + "/应用/" + props.getProperty("modelfile2name"));
		new File(props.getProperty("rootpath") + root + "/应用/初始化").mkdir();
		new File(props.getProperty("rootpath") + root + "/应用/初始化/log").mkdir();
		new File(props.getProperty("rootpath") + root + "/应用/周期脚本").mkdir();
		new File(props.getProperty("rootpath") + root + "/应用/周期脚本/log").mkdir();

		SVNUtil.getInstance().add(props.getProperty("rootpath") + root);
		SVNUtil.getInstance().commit(props.getProperty("rootpath") + root,
				true, root);

		for (int i = 0; i < existFilesList.length; i++) {
			SVNUtil.getInstance().lock(
					props.getProperty("sdmfilsall").toString()
							+ existFilesList[i], root);
		}
		System.out.println("OK");
	}

	private static String[] removeNotExist(String[] target, String[] source) {
		HashMap<String, Integer> isExistMap = new HashMap<String, Integer>();
		for (int i = 0; i < target.length; i++) {
			isExistMap.put(target[i], 0);
		}

		for (int j = 0; j < source.length; j++) {
			if (isExistMap.get(source[j]) != null) {
				isExistMap.put(source[j], 1);
			}
		}

		ArrayList<String> existFiles = new ArrayList<String>();
		Iterator<String> iterator = isExistMap.keySet().iterator();
		while (iterator.hasNext()) {
			String filename = iterator.next();
			if (isExistMap.get(filename) == 1) {
				existFiles.add(filename);
			}
			if (isExistMap.get(filename) == 0) {
				System.err.println("ERROR: " + filename + "文件没找到！");
			}
		}

		return (String[]) existFiles.toArray(new String[existFiles.size()]);
	}

	public void updateFileTree(String root, ArrayList<String> filesList)
			throws Exception {
		SVNUtil.getInstance().update(props.getProperty("rootpath"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		SVNUtil.getInstance().update(props.getProperty("sdmfilsall"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		
		if (!new File(props.getProperty("rootpath") + root).exists()) {
			throw new Exception("目录不存在，更新失败！");
		}

		File updateFolder = new File(props.getProperty("rootpath") + root
				+ "/PDM/SDM/");
		String[] oldFilesList = updateFolder.list(new XlsFilter());
		for (int i = 0; i < oldFilesList.length; i++) {
			filesList.remove(oldFilesList[i]);
		}

		String[] existFilesList = removeNotExist((String[]) filesList
				.toArray(new String[filesList.size()]), new File(props
				.getProperty("sdmfilsall")).list(new XlsFilter()));

		if (existFilesList.length == 0) {
			return;
		} else {
			for (int i = 0; i < existFilesList.length; i++) {
				copy(props.getProperty("sdmfilsall").toString()
						+ existFilesList[i], props.getProperty("rootpath")
						+ root + "/PDM/SDM/" + existFilesList[i]);
				System.out.println("新增：" + existFilesList[i]);
				SVNUtil.getInstance().lock(
						props.getProperty("sdmfilsall").toString()
								+ existFilesList[i], root);
				SVNUtil.getInstance().add(
						props.getProperty("rootpath") + root + "/PDM/SDM/"
								+ existFilesList[i]);
			}
		}
		SVNUtil.getInstance().commit(props.getProperty("rootpath") + root,
				true, root);
	}

	public void writeBack(String root) throws Exception {
		SVNUtil.getInstance().update(props.getProperty("rootpath"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		SVNUtil.getInstance().update(props.getProperty("sdmfilsall"),
				SVNRevision.BASE, SVNDepth.INFINITY);
		
		if (!new File(props.getProperty("rootpath") + root).exists()) {
			throw new Exception("目录不存在，写回文件失败！");
		}
		
		String[] newFilesList = new File(props.getProperty("rootpath") + root
				+ "/PDM/SDM/").list(new XlsFilter());
		for (int i = 0; i < newFilesList.length; i++) {
			SVNUtil.unlock(props.getProperty("sdmfilsall").toString()
					+ newFilesList[i]);
			copy(props.getProperty("rootpath") + root + "/PDM/SDM/"
					+ newFilesList[i], props.getProperty("sdmfilsall")
					.toString()
					+ newFilesList[i]);
		}
		SVNUtil.getInstance().commit(props.getProperty("sdmfilsall"), true,
				root);
	}

	private class XlsFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String fname) {
			// TODO Auto-generated method stub
			return fname.toLowerCase().endsWith(".xls") ? true : false;
		}

	}
}
