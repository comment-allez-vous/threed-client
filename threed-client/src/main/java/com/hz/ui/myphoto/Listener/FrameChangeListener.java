package com.hz.ui.myphoto.Listener;

import com.hz.ui.MyPhotoShow;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.ui.myphoto.control.AppControl;
import com.hz.ui.myphoto.panel.CategoryPanel;
import com.hz.ui.myphoto.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by hasee on 2017/9/1.
 */
public class FrameChangeListener extends ComponentAdapter {
	private MyPhotoShow mainWindow;
	
    private JFrame jFrame;
    private java.util.List<JPanel> panels;
    private String userName;
    private AppControl appControl;

    public FrameChangeListener(JFrame jFrame, java.util.List<JPanel> panels,String userName,AppControl appControl,MyPhotoShow mainWindow) {
    	this.setUserName(userName);
        this.jFrame = jFrame;
        this.panels = panels;
        this.appControl=appControl;
        this.mainWindow=mainWindow;
    }



    @Override
    public void componentResized(ComponentEvent evt) {
        this.jFrame.getContentPane().removeAll();
        try {
            System.out.println(1);
        	for (JPanel panel: panels) {
                String panelClass=panel.getClass().getCanonicalName();
                Dimension dimension=evt.getComponent().getSize();
                switch (panelClass){
                    case "com.hz.ui.myphoto.panel.CategoryPanel":
                        panel=new CategoryPanel(MyPhotoConstantsUI.CALALOG_WIDTH,dimension.height,this.getUserName());
                        this.jFrame.add(panel,"West");
                        break;
                    case "com.hz.ui.myphoto.panel.MainPanel":
                        MainPanel mainPanel=new MainPanel(dimension.width-MyPhotoConstantsUI.CALALOG_WIDTH,dimension.height,mainWindow);
                        this.jFrame.add(mainPanel,"East");
                        break;
                }
            }
            this.jFrame.setVisible(true);
        }catch (Exception e){
        }

    }



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}
