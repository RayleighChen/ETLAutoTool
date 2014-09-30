package com.ray.tool.ui;

import javax.swing.*;

import com.ray.tool.util.Consts;
import com.ray.tool.util.PropertiesUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

class SVNConfigDialog implements ActionListener {
	private JFrame svnFrame;
	private JButton oKButtom, cancelButton, svnRootSelect;
	private JFileChooser fc = new JFileChooser();
	private JTextField svnRootField, svnUsernameField, svnPasswordField;
	private JLabel svnRoot, svnUsername, svnPassword;

	public SVNConfigDialog() {
		svnFrame = new JFrame("SVN配置");
		oKButtom = new JButton("确认");
		cancelButton = new JButton("取消");

		svnRootSelect = new JButton("浏览");

		svnRoot = new JLabel("本地SVN根地址:");
		svnUsername = new JLabel("SVN用户名:");
		svnPassword = new JLabel("SVN密码:");

		svnRootField = new JTextField();
		svnUsernameField = new JTextField();
		svnPasswordField = new JTextField();
	}

	public void display() {
		Container contentPane = svnFrame.getContentPane(); // 获取内容面板
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);

		svnRoot.setFont(new Font("Dialog", 1, 13));
		svnRoot.setSize(120, 15);
		svnRoot.setLocation(65, 20);
		svnFrame.add(svnRoot);

		svnRootField.setSize(400, 25);
		svnRootField.setLocation(180, 15);
		svnFrame.add(svnRootField);

		svnRootSelect.setSize(60, 25);
		svnRootSelect.setLocation(600, 15);
		svnRootSelect.addActionListener(this);
		svnFrame.add(svnRootSelect);

		svnUsername.setFont(new Font("Dialog", 1, 13));
		svnUsername.setSize(120, 15);
		svnUsername.setLocation(65, 58);
		svnFrame.add(svnUsername);

		svnUsernameField.setSize(400, 25);
		svnUsernameField.setLocation(180, 55);
		svnFrame.add(svnUsernameField);

		svnPassword.setFont(new Font("Dialog", 1, 13));
		svnPassword.setSize(120, 15);
		svnPassword.setLocation(65, 100);
		svnFrame.add(svnPassword);

		svnPasswordField.setSize(400, 25);
		svnPasswordField.setLocation(180, 95);
		svnFrame.add(svnPasswordField);

		oKButtom.addActionListener(this);
		oKButtom.setSize(140, 25);
		oKButtom.setLocation(430, 300);
		svnFrame.add(oKButtom);

		cancelButton.addActionListener(this);
		cancelButton.setSize(140, 25);
		cancelButton.setLocation(580, 300);
		svnFrame.add(cancelButton);

		readProperties();

		// 设置窗体的一些属性值
		svnFrame.setSize(750, 380);
		svnFrame.setResizable(false);
		svnFrame.setLocationRelativeTo(null);
		svnFrame.setVisible(true);
	}

	private void readProperties() {
		String path = PropertiesUtils.getPropertiesPath();
		Properties p = new Properties();
		try {
			InputStream is = new FileInputStream(path + Consts.SVN_PROPERTIES);
			p.load(is);
			svnRootField.setText(p.getProperty(Consts.SVN_ROOT));
			svnUsernameField.setText(p.getProperty(Consts.SVN_USER));
			svnPasswordField.setText(p.getProperty(Consts.SVN_PW));
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
					+ Consts.SVN_PROPERTIES);
			p.setProperty(Consts.SVN_ROOT, svnRootField.getText());
			p.setProperty(Consts.SVN_USER, svnUsernameField.getText());
			p.setProperty(Consts.SVN_PW, svnPasswordField.getText());
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
			svnFrame.dispose();
		}
		if (e.getSource() == cancelButton) {
			svnFrame.dispose();
		}

		if (e.getSource() == svnRootSelect) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int intRetVal = fc.showOpenDialog(svnFrame);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				svnRootField.setText("file:///"
						+ fc.getSelectedFile().getPath().replace("\\", "/"));
			}
		}
	}
	
	public static void main(String args[]) {
		SVNConfigDialog frame = new SVNConfigDialog();
		frame.display();
	}
	
}
