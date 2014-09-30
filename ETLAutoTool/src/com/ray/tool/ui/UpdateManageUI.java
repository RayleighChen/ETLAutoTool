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

public class UpdateManageUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton updateButton;
	private JTextField updateVersionField;
	private Tool tool;
	private HashMap<String, ArrayList<String>> versions = null;
	
	public UpdateManageUI() {
		super();
		tool = new Tool();
		versions = (HashMap<String, ArrayList<String>>) tool.getChangeVersion();
		
		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder());

		JLabel title = new JLabel("变更更新");
		title.setSize(80, 20);
		title.setLocation(20, 0);
		this.add(title);
		
		updateVersionField = new JTextField();
		updateVersionField.setSize(280, 25);
		updateVersionField.setLocation(50, 40);
		this.add(updateVersionField);
		
		updateButton = new JButton("更新变更");
		updateButton.addActionListener(this);
		updateButton.setSize(120, 25);
		updateButton.setLocation(500, 40);
		this.add(updateButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == updateButton) {
			if(!updateVersionField.getText().trim().equals("")) {
				try {
					ConsoleTextArea.getInstanc().clearLog();
					System.out.println("更新变更号：" + updateVersionField.getText());
					tool.updateFileTree(updateVersionField.getText().trim(), versions.get(updateVersionField.getText().trim()));
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
