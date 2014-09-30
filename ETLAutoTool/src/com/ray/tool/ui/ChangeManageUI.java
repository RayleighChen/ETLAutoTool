package com.ray.tool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ray.tool.Tool;
import com.ray.tool.log.ConsoleTextArea;

public class ChangeManageUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton singleButton, allButton;
	private JTextField changeVersionField;
	private Tool tool;
	private HashMap<String, ArrayList<String>> versions = null;
	
	public ChangeManageUI() {
		super();
		tool = new Tool();
		versions = (HashMap<String, ArrayList<String>>) tool.getChangeVersion();
		
		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder());

		JLabel title = new JLabel("变更管理");
		title.setSize(80, 20);
		title.setLocation(20, 0);
		this.add(title);
		
		changeVersionField = new JTextField();
		changeVersionField.setSize(280, 25);
		changeVersionField.setLocation(50, 40);
		this.add(changeVersionField);
		
		singleButton = new JButton("单次初始化");
		singleButton.addActionListener(this);
		singleButton.setSize(120, 25);
		singleButton.setLocation(500, 40);
		this.add(singleButton);
		
		allButton = new JButton("全部初始化");
		allButton.addActionListener(this);
		allButton.setSize(120, 25);
		allButton.setLocation(630, 40);
		this.add(allButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()  == singleButton) {
			if(!changeVersionField.getText().trim().equals("")) {
				try {
					ConsoleTextArea.getInstanc().clearLog();
					System.out.println("变更号：" + changeVersionField.getText());
					tool.createFileTree(changeVersionField.getText().trim(), versions.get(changeVersionField.getText().trim()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				ConsoleTextArea.getInstanc().clearLog();
				System.out.println("请输入变更号！");
			}
		}
		
		if(e.getSource()  == allButton) {
			if(!changeVersionField.getText().trim().equals("")) {
				try {
					ConsoleTextArea.getInstanc().clearLog();
					String[] fileList = tool.readFolder();
					
					for(int i = 0; i < fileList.length; i++) {
						if(versions.get(fileList[i]) != null) {
							versions.remove(fileList[i]);
						}
					}
					Iterator<String> iterator = versions.keySet().iterator();
					while (iterator.hasNext()) {
						try {
							String changeVersions = iterator.next().toString();
							ArrayList<String> fileNameList = versions.get(changeVersions);
							System.out.println("变更号：" + changeVersions);
							tool.createFileTree(changeVersions, fileNameList);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				ConsoleTextArea.getInstanc().clearLog();
				System.out.println("请输入变更号！");
			}
		}
	}
}
