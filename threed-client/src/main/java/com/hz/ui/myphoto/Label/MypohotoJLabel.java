package com.hz.ui.myphoto.Label;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 线束图片的Label
 *
 * @author Administrator
 */
public class MypohotoJLabel extends JLabel {

	//设置图片的label，label的大小应与图片大小保持一致
	public MypohotoJLabel(String path) {
		ImageIcon image = new ImageIcon(path);
		int width = image.getIconWidth();
		int height = image.getIconHeight();
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		this.setIcon(image);
	}


	public MypohotoJLabel(int width, int height, String path) {
		if (width != 0 && height != 0) {
			ImageIcon image = new ImageIcon(path);
			image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
			this.setIcon(image);
		}

	}

	public MypohotoJLabel(String text, int horizontalAlignment, Color color) {
		this.setForeground(color);
		this.setText(text);
		this.setHorizontalAlignment(horizontalAlignment);
	}
}
