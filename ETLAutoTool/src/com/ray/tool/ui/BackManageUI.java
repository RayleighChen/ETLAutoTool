package com.ray.tool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ray.tool.Tool;
import com.ray.tool.log.ConsoleTextArea;

public class BackManageUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton backButton;
	private JTextField backVersionField;
	private Tool tool;
	private HashMap<String, ArrayList<String>> versions = null;
	
	public BackManageUI () {
		super();
		tool = new Tool();
		versions = (HashMap<String, ArrayList<String>>) tool.getChangeVersion();
		
		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder());

		JLabel title = new JLabel("写回变更");
		title.setSize(80, 20);
		title.setLocation(20, 0);
		this.add(title);
		
		backVersionField = new JTextField();
		backVersionField.setSize(280, 25);
		backVersionField.setLocation(50, 40);
		this.add(backVersionField);
		
		backButton = new JButton("写回变更");
		backButton.setSize(120, 25);
		backButton.setLocation(500, 40);
		this.add(backButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			if(!backVersionField.getText().trim().equals("")) {
				try {
					ConsoleTextArea.getInstanc().clearLog();
					System.out.println("写回变更号：" + backVersionField.getText());
					tool.writeBack(backVersionField.getText().trim());
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
