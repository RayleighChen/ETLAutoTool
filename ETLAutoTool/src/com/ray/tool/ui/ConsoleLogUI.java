package com.ray.tool.ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ray.tool.log.ConsoleTextArea;

public class ConsoleLogUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane shellLog;
	
	public ConsoleLogUI() {
		super();
		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder());

		JLabel title = new JLabel("输出日志");
		title.setSize(80, 20);
		title.setLocation(20, 0);
		this.add(title);
		
		ConsoleTextArea.getInstanc().setFont(java.awt.Font.decode("monospaced"));
		ConsoleTextArea.getInstanc().setEditable(false);
		shellLog = new JScrollPane(ConsoleTextArea.getInstanc());
		
		shellLog.setSize(800, 200);
		shellLog.setLocation(15, 25);
		this.add(shellLog);
	}
}
