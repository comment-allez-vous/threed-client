package com.hz.ui.myphoto.panel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgruondPanel extends JPanel {
	private ImageIcon pic;
	private Image image;
	private int width;
	private int height;

	public BackgruondPanel(String imgUrl) {
		pic = new ImageIcon(imgUrl);
		image = pic.getImage();
		width = pic.getIconWidth();
		height = pic.getIconHeight();
		this.setSize(width, height);
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		arg0.drawImage(image, 0, 0, width, height, null);
	}

	//获取作为背景图片的宽度
	public int getdefaultWidth() {
		return width;
	}

	//获取作为背景图片的高度
	public int getdefaultHeight() {
		return height;
	}
}
