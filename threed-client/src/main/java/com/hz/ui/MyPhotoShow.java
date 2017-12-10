/*
 * MyPhotoShow.java
 *
 * Created on __DATE__, __TIME__
 */

package com.hz.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.hz.ui.myphoto.Listener.FrameChangeListener;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.ui.myphoto.control.AppControl;
import com.hz.ui.myphoto.panel.*;
import com.hz.utils.PixelUtil;


/**
 * @author __USER__
 */
public class MyPhotoShow extends javax.swing.JFrame {
	private JFrame frame;
	private String userName;

	private MainPanel mainPanel;
	private CategoryPanel categoryPanel;

	//用于处理拖动事件，表示鼠标按下时的坐标，相对于JFrame
	int xOld = 0;
	int yOld = 0;

	public MyPhotoShow() {
		initComponents();
	}

	public MyPhotoShow(String userName) {
		this.setUserName(userName);
		initComponents();
	}

	private void initComponents() {
		// 设置系统默认样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭框架的同时结束程序
		frame.setBounds(ConstantsUI.MAIN_WINDOW_X, ConstantsUI.MAIN_WINDOW_Y, PixelUtil.scaleWidth(ConstantsUI.MAIN_WINDOW_WIDTH_PRECENT),
				PixelUtil.scaleHeight(ConstantsUI.MAIN_WINDOW_HEIFHT_PRECENT));
		frame.setTitle(ConstantsUI.APP_NAME);
		frame.setIconImage(ConstantsUI.IMAGE_ICON);
		frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);

		frame.setLayout(new BorderLayout());

		AppControl appControl = AppControl.instance();
		categoryPanel = new CategoryPanel(MyPhotoConstantsUI.CALALOG_WIDTH, frame.getSize().height, this.getUserName());

		mainPanel = new MainPanel(frame.getSize().width - MyPhotoConstantsUI.CALALOG_WIDTH, frame.getSize().height, this);

		frame.add(categoryPanel, "West");
		frame.add(mainPanel, "East"); //图片展示


		java.util.List<JPanel> panelList = new ArrayList<>();
		panelList.add(categoryPanel);
		panelList.add(mainPanel);
		FrameChangeListener listener = new FrameChangeListener(frame, panelList, this.getUserName(), appControl, this);
		frame.setResizable(true);
		appControl.setMainPanel(mainPanel);


//		  //处理拖动事件---去掉默认边框后，不能拖动了，具体实现如下
//		  this.addMouseListener(new MouseAdapter() {
//		  @Override
//		  public void mousePressed(MouseEvent e) {
//		    xOld = e.getX();//记录鼠标按下时的坐标
//		    yOld = e.getY();
//		   }
//		  });
//		  
//		    this.addMouseMotionListener(new MouseMotionAdapter() {
//		    @Override
//		    public void mouseDragged(MouseEvent e) {
//		    int xOnScreen = e.getXOnScreen();
//		    int yOnScreen = e.getYOnScreen();
//		    int xx = xOnScreen - xOld;
//		    int yy = yOnScreen - yOld;
//		    MyPhotoShow.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
//		    }
//		   });


		pack();
		this.frame.setUndecorated(true);


		this.frame.setVisible(true);


	}

	public void changeStation() {
		//处理拖动事件---去掉默认边框后，不能拖动了，具体实现如下
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();//记录鼠标按下时的坐标
				yOld = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				MyPhotoShow.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
			}
		});
	}


	public static void main(String args[]) {
		//	MyPhotoShow window = new MyPhotoShow();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				MyPhotoShow window = new MyPhotoShow();
				MyPhotoShow window = new MyPhotoShow("yizhiqiang");
				//  window.frame.setVisible(true);
			}
		});
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 关闭窗口
	 */
	public void btnMin_ActionEvent() {
		frame.setExtendedState(JFrame.ICONIFIED);
	}

	/**
	 * 最大化
	 */
	public void btnMax_ActionEvent() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void resetSize() {
		int width = frame.getWidth();
		int height = frame.getHeight();
		mainPanel.resetSize(width, height);
		categoryPanel.resetSize(width, height);
	}

	public JFrame getFrame() {
		return frame;
	}
}