package com.hz.threed_client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

class MyPanelkLogin extends JPanel {
	/**
	* 自定义JPanel
	*/
	private static final long serialVersionUID = 1L;
	@Override
	public void paintComponent(Graphics g) { /* 重绘函数 */
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
//		String directory = ".//images//background3.jpg";
		URL imgURL = this.getClass().getResource("/images/background2.png");
//		this.getClass().getClassLoader().getResource(directory);
		Image img = Toolkit.getDefaultToolkit().getImage(imgURL);
//		Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(directory));
		// // 在面板上绘制背景图片/////////
		g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public static void main(String[] args) {
		JPanel  myjpanel = new MyPanelkLogin();
	}
}