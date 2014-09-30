package com.ray.tool.ui;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -5309385967467562293L;

	public JMenu Start, Help;

	public JMenuItem SVNSetting, SDMSetting;

	// JCheckBoxMenuItem view;
	public JMenuItem About;

	public MenuBar() {
		super();
		Start = new JMenu("Preferences");
		Help = new JMenu("Help");

		this.add(Start);
		this.add(Help);

		SVNSetting = new JMenuItem("SVN配置");
		SVNSetting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SVNConfigDialog dialog = new SVNConfigDialog();
				dialog.display();
			}
		});
		
		SDMSetting = new JMenuItem("SDM文件配置");
		SDMSetting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				SDMConfigDialog dialog = new SDMConfigDialog();
				dialog.display();
			}
		});

		Start.add(SVNSetting);
		Start.addSeparator();
		Start.add(SDMSetting);

		About = new JMenuItem("About");
		About.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				AboutBOX aboutBOX = new AboutBOX();
			}
		});
		Help.add(About);
	}

	private class AboutBOX extends JFrame {
		public AboutBOX() {
			super("About AutoTool");
			Container c = this.getContentPane();
			c.setLayout(null);

			ImageIcon image = new ImageIcon("./res/logo.jpg");
			image.setImage(image.getImage().getScaledInstance(80, 80,
					Image.SCALE_DEFAULT));

			JPanel jPanel = new JPanel();
			JLabel lb = new JLabel(image);
			jPanel.add(lb);
			jPanel.setSize(80, 80);
			jPanel.setLocation(5, 20);

			c.add(jPanel);

			JLabel product = new JLabel("AutoTool v1.0.0");
			product.setSize(500, 30);
			product.setLocation(120, 30);
			JLabel copyright = new JLabel("Copyright Ray © 2014");
			copyright.setSize(500, 30);
			copyright.setLocation(90, 60);

			c.add(product);
			c.add(copyright);

			this.setSize(250, 150);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);

		}
	}
}