package com.hz.ui;

import org.apache.log4j.Logger;

import com.hz.ui.panel.ToolBarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * 程序入口，主窗口Frame
 *
 * @author huangzhuo
 */
public class MainWindow {
	private static Logger logger = Logger.getLogger(MainWindow.class);

	private JFrame frame;

	private static JPanel mainPanel;
	public static JPanel mainPanelCenter;


	// 新建备份dialog
	public static JDialog dialog;

	/**
	 * 程序入口main
	 */
	public static void main(String[] args) {

		MainWindow window = new MainWindow();
		window.frame.setVisible(true);

//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
	}

	/**
	 * 构造，创建APP
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * 初始化frame内容
	 */
	private void initialize() {
//        PropertyConfigurator
//                .configure(ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator + "log4j.properties");
		logger.info("==================MainWindowInitStart");
		// 设置系统默认样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 初始化主窗口
		frame = new JFrame();
		frame.setBounds(ConstantsUI.MAIN_WINDOW_X, ConstantsUI.MAIN_WINDOW_Y, ConstantsUI.MAIN_WINDOW_WIDTH,
				ConstantsUI.MAIN_WINDOW_HEIGHT);
		frame.setTitle(ConstantsUI.APP_NAME);
		frame.setIconImage(ConstantsUI.IMAGE_ICON);
		frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);


		mainPanel = new JPanel(true);
		mainPanel.setBackground(Color.white);
		mainPanel.setLayout(new BorderLayout());

		ToolBarPanel toolbar = new ToolBarPanel();
		mainPanel.add(toolbar, BorderLayout.WEST);


		mainPanelCenter = new JPanel(true);
		mainPanelCenter.setLayout(new BorderLayout());

		mainPanel.add(mainPanelCenter, BorderLayout.CENTER);

		frame.add(mainPanel);


		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		//监听系统事件，点关闭时关闭软件
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});


		logger.info("==================MainWindowInitEnd");


	}

	public void btnMin_ActionEvent() {
		frame.setExtendedState(JFrame.ICONIFIED);
	}

}
