package com.ray.tool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ray.tool.util.Consts;
import com.ray.tool.util.PropertiesUtils;

public class SDMConfigDialog implements ActionListener {
	private JFrame sdmFrame;
	private JButton oKButtom, cancelButton;
	private JButton changeFilePathSelect, changeRootPathSelect,
			modelFilePathSelect, sdmFilesAllSelect;
	private JFileChooser fc = new JFileChooser();
	private JTextField changeFilePathField, changeRootPathField,
			modelFilePathField, modelFile1Field, modelFile2Field,
			sdmFilesAllField;
	private JLabel changeFilePath, changeRootPath, modelFilePath, modelFile1,
			modelFile2, sdmFilesAll;

	public SDMConfigDialog() {
		sdmFrame = new JFrame("SDM配置");
		oKButtom = new JButton("确认");
		cancelButton = new JButton("取消");

		changeFilePathSelect = new JButton("浏览");
		changeRootPathSelect = new JButton("浏览");
		modelFilePathSelect = new JButton("浏览");
		sdmFilesAllSelect = new JButton("浏览");

		changeFilePath = new JLabel("变更总控文件路径：");
		changeRootPath = new JLabel("变更文件存放路径：");
		modelFilePath = new JLabel("变更总控模板路径：");
		modelFile1 = new JLabel("模板文件1：");
		modelFile2 = new JLabel("模板文件2：");
		sdmFilesAll = new JLabel("ALL_SDM存放路径：");

		changeFilePathField = new JTextField();
		changeRootPathField = new JTextField();
		modelFilePathField = new JTextField();
		modelFile1Field = new JTextField();
		modelFile2Field = new JTextField();
		sdmFilesAllField = new JTextField();
	}

	public void display() {
		Container contentPane = sdmFrame.getContentPane(); // 获取内容面板
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);

		changeFilePath.setFont(new Font("Dialog", 1, 13));
		changeFilePath.setSize(140, 15);
		changeFilePath.setLocation(30, 20);
		sdmFrame.add(changeFilePath);

		changeFilePathField.setSize(400, 25);
		changeFilePathField.setLocation(180, 15);
		sdmFrame.add(changeFilePathField);

		changeFilePathSelect.setSize(60, 25);
		changeFilePathSelect.setLocation(600, 15);
		changeFilePathSelect.addActionListener(this);
		sdmFrame.add(changeFilePathSelect);

		changeRootPath.setFont(new Font("Dialog", 1, 13));
		changeRootPath.setSize(140, 15);
		changeRootPath.setLocation(30, 58);
		sdmFrame.add(changeRootPath);

		changeRootPathField.setSize(400, 25);
		changeRootPathField.setLocation(180, 55);
		sdmFrame.add(changeRootPathField);

		changeRootPathSelect.setSize(60, 25);
		changeRootPathSelect.setLocation(600, 55);
		changeRootPathSelect.addActionListener(this);
		sdmFrame.add(changeRootPathSelect);

		modelFilePath.setFont(new Font("Dialog", 1, 13));
		modelFilePath.setSize(140, 15);
		modelFilePath.setLocation(30, 100);
		sdmFrame.add(modelFilePath);

		modelFilePathField.setSize(400, 25);
		modelFilePathField.setLocation(180, 95);
		sdmFrame.add(modelFilePathField);

		modelFilePathSelect.setSize(60, 25);
		modelFilePathSelect.setLocation(600, 95);
		modelFilePathSelect.addActionListener(this);
		sdmFrame.add(modelFilePathSelect);

		modelFile1.setFont(new Font("Dialog", 1, 13));
		modelFile1.setSize(140, 15);
		modelFile1.setLocation(30, 140);
		sdmFrame.add(modelFile1);

		modelFile1Field.setSize(400, 25);
		modelFile1Field.setLocation(180, 135);
		sdmFrame.add(modelFile1Field);

		modelFile2.setFont(new Font("Dialog", 1, 13));
		modelFile2.setSize(140, 15);
		modelFile2.setLocation(30, 180);
		sdmFrame.add(modelFile2);

		modelFile2Field.setSize(400, 25);
		modelFile2Field.setLocation(180, 175);
		sdmFrame.add(modelFile2Field);

		sdmFilesAll.setFont(new Font("Dialog", 1, 13));
		sdmFilesAll.setSize(140, 15);
		sdmFilesAll.setLocation(30, 220);
		sdmFrame.add(sdmFilesAll);

		sdmFilesAllField.setSize(400, 25);
		sdmFilesAllField.setLocation(180, 215);
		sdmFrame.add(sdmFilesAllField);

		sdmFilesAllSelect.setSize(60, 25);
		sdmFilesAllSelect.setLocation(600, 215);
		sdmFilesAllSelect.addActionListener(this);
		sdmFrame.add(sdmFilesAllSelect);
		
		oKButtom.addActionListener(this);
		oKButtom.setSize(140, 25);
		oKButtom.setLocation(430, 300);
		sdmFrame.add(oKButtom);

		cancelButton.addActionListener(this);

		cancelButton.setSize(140, 25);
		cancelButton.setLocation(580, 300);
		sdmFrame.add(cancelButton);

		// 设置窗体的一些属性值
		sdmFrame.setSize(750, 380);
		sdmFrame.setResizable(false);
		sdmFrame.setLocationRelativeTo(null);
		sdmFrame.setVisible(true);
		readProperties();
	}
	
	private void readProperties() {
		String path = PropertiesUtils.getPropertiesPath();
		Properties p = new Properties();
		try {
			InputStream is = new FileInputStream(path + Consts.SDM_PROPERTIES);
			p.load(is);
			changeFilePathField.setText(p.getProperty(Consts.FILE_PATH));
			changeRootPathField.setText(p.getProperty(Consts.ROOT_PATH));
			modelFilePathField.setText(p.getProperty(Consts.MODEL_PATH));
			modelFile1Field.setText(p.getProperty(Consts.MODEL_FILE1));
			modelFile2Field.setText(p.getProperty(Consts.MODEL_FILE2));
			sdmFilesAllField.setText(p.getProperty(Consts.SDM_ALL));
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeProperties() {
		String path = PropertiesUtils.getPropertiesPath();
		Properties p = new Properties();
		try {
			OutputStream fos = new FileOutputStream(path
					+ Consts.SDM_PROPERTIES);
			p.setProperty(Consts.FILE_PATH, changeFilePathField.getText());
			p.setProperty(Consts.ROOT_PATH, changeRootPathField.getText());
			p.setProperty(Consts.MODEL_PATH, modelFilePathField.getText());
			p.setProperty(Consts.MODEL_FILE1, modelFile1Field.getText());
			p.setProperty(Consts.MODEL_FILE2, modelFile2Field.getText());
			p.setProperty(Consts.SDM_ALL, sdmFilesAllField.getText());
			p.store(fos, "update");
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == oKButtom) {
			writeProperties();
			sdmFrame.dispose();
		}
		if (e.getSource() == cancelButton) {
			sdmFrame.dispose();
		}
		if (e.getSource() == changeFilePathSelect) {
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int intRetVal = fc.showOpenDialog(sdmFrame);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				changeFilePathField.setText(fc.getSelectedFile().getPath()
						.replace("\\", "/"));
			}
		}

		if (e.getSource() == changeRootPathSelect) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int intRetVal = fc.showOpenDialog(sdmFrame);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				changeRootPathField.setText(fc.getSelectedFile().getPath()
						.replace("\\", "/"));
			}
		}

		if (e.getSource() == modelFilePathSelect) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int intRetVal = fc.showOpenDialog(sdmFrame);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				modelFilePathField.setText(fc.getSelectedFile().getPath()
						.replace("\\", "/"));
			}
		}

		if (e.getSource() == sdmFilesAllSelect) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int intRetVal = fc.showOpenDialog(sdmFrame);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				sdmFilesAllField.setText(fc.getSelectedFile().getPath()
						.replace("\\", "/"));
			}
		}
	}
	
	public static void main(String args[]) {
		SDMConfigDialog frame = new SDMConfigDialog();
		frame.display();
	}
}
