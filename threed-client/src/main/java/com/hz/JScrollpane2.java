package com.hz;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

public class JScrollpane2 implements ActionListener {
	JScrollPane scrollPane;
    public final static String CURRENT_DIR = System.getProperty("user.dir");
    
	public JScrollpane2() {
		JFrame f = new JFrame("JScrollPaneDemo");
		Container contentPane = f.getContentPane();
		String path=CURRENT_DIR+File.separator +"icon"+File.separator+"about.png";
		JLabel label1 = new JLabel(new ImageIcon(path));
		JPanel panel1 = new JPanel();
		panel1.add(label1);
		scrollPane = new JScrollPane();
		/*
		 * 设置窗口显示组件为panel1
		 */
		scrollPane.setViewportView(panel1);
		/*
		 * 设置水平与垂直表头
		 */
		scrollPane.setColumnHeaderView(new JLabel("水平表头"));
		scrollPane.setRowHeaderView(new JLabel("垂直表头"));
		/*
		 * 设置scrollPane的边框凹陷立体边框。边框(Border)部份我们将在以后介绍。
		 */
		scrollPane.setViewportBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		/*
		 * 设置scrollPane的边角图案，由于JScrollPane为矩形形状，因此就有4个位置来摆放边角(Corner)
		 * 组件，这4个地方分别是左上、左下、右上、右下，对应的参数分别如下:JScrollPane.UPPER_LEFT_CORNER
		 * JScrollPane.LOWER_LEFT_CORNERJScrollPane.UPPER_RIGHT_CORNER
		 * JScrollPane.LOWER_RIGHT_CORNER
		 */
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JLabel(
				new ImageIcon(path)));
		scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JLabel(
				new ImageIcon(path)));

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		JButton b = new JButton("显示水平滚动轴");
		b.addActionListener(this);
		panel2.add(b);
		b = new JButton("不要显示水平滚动轴");
		b.addActionListener(this);
		panel2.add(b);
		b = new JButton("适时显示水平滚动轴");
		b.addActionListener(this);
		panel2.add(b);

		contentPane.add(panel2, BorderLayout.WEST);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		f.setSize(new Dimension(350, 220));
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("显示水平滚动轴"))
			scrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		if (e.getActionCommand().equals("不要显示水平滚动轴"))
			scrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		if (e.getActionCommand().equals("适时显示水平滚动轴"))
			scrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.revalidate();// 重新显示JScrollPane形状。
	}

	public static void main(String[] args) {
		new JScrollpane2();
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}