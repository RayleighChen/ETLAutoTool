package com.ray.tool.ui;

import java.awt.Container;

import javax.swing.JFrame;


public class MainUI extends JFrame {
	private static final long serialVersionUID = -2778972378710074697L;
	private MenuBar mb;
	private ConsoleLogUI consoleLog;
	private ChangeManageUI changeManageUI;
	private UpdateManageUI updateManageUI;
	private BackManageUI backManageUI;
	
	public MainUI() {
		super("AutoTool");
		mb = new MenuBar();
		consoleLog = new ConsoleLogUI();
		changeManageUI = new ChangeManageUI();
		updateManageUI = new UpdateManageUI();
		backManageUI = new BackManageUI();
	}

	public void display() {
		Container c = this.getContentPane();
		c.setLayout(null);

		this.setJMenuBar(mb);
		
		consoleLog.setSize(830, 240);
		consoleLog.setLocation(10, 10);
		this.add(consoleLog);
		
		changeManageUI.setSize(830, 100);
		changeManageUI.setLocation(10, 260);
		this.add(changeManageUI);
		
		updateManageUI.setSize(830, 100);
		updateManageUI.setLocation(10, 370);
		this.add(updateManageUI);
		
		backManageUI.setSize(830, 100);
		backManageUI.setLocation(10, 480);
		this.add(backManageUI);
		
		this.setSize(850, 650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new MainUI().display();
	}
}
