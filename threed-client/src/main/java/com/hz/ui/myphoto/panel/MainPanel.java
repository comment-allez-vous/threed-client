package com.hz.ui.myphoto.panel;

import com.hz.ui.MainWindow;
import com.hz.ui.MyPhotoShow;
import com.hz.ui.myphoto.bean.PhotoBean;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.ui.myphoto.data.PhotoData;
import com.hz.ui.myphoto.layout.VFlowLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by hasee on 2017/9/2.
 * 主体面板：右边
 * 包含：搜索面板和展示面板
 */
public class MainPanel extends JPanel {
	private MyPhotoShow mainWindow;
	
    private int width;
    private int height;

    private JPanel photoinfo;
    private MainTopPanel topPanel;


    public MainPanel(int _width,int _heigth,MyPhotoShow _mainWindow){
        this.width=_width;
        this.height=_heigth;
        this.mainWindow=_mainWindow;
        init();
        setVisible(true);
    }

    private void init(){
        this.setPreferredSize(new Dimension(this.width,this.height));
//        this.setBorder(new LineBorder(Color.gray));
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setBackground(Color.black);
        
        //1、上部分的内容
        this.setLayout(new BorderLayout());
        topPanel=new MainTopPanel(this.width,mainWindow);
        topPanel.setPreferredSize(new Dimension(this.width, 70));//设置JPanel的大小
        topPanel.setBackground(Color.black);
        this.add(topPanel,BorderLayout.NORTH);
        //2、中间部分的主体现实图片的地方
        MainPhotoPanel photoPanel=new MainPhotoPanel(this.width-35,this.height-topPanel.getHeight()-120);
        photoPanel.setData(PhotoData.search());
        photoPanel.init();
        photoinfo=new JPanel();
        photoinfo.add(photoPanel);
        photoinfo.setBackground(Color.WHITE);
        this.add(photoinfo,BorderLayout.CENTER);
        //3、下部分显示分页的地方
        PaginationPanel paginationPanel=new PaginationPanel(this.width-35);
        JPanel panel=new JPanel();
        panel.add(paginationPanel);
        panel.setBackground(Color.WHITE);
        this.add(panel,BorderLayout.SOUTH);

    }

    @Override
    public void paint(Graphics g) {
        setSize(this.width,this.height);
        super.paint(g);
    }

    public void updateData(){
        this.photoinfo.removeAll();
        this.photoinfo.setVisible(false);
        this.addCom();
        this.photoinfo.revalidate();
        this.photoinfo.setVisible(true);
    }

    public void addCom(){
        MainPhotoPanel photoPanel=new MainPhotoPanel(this.width-35,this.height-topPanel.getHeight()-120);
        photoPanel.setData(PhotoData.search());
        photoPanel.init();
        photoinfo.add(photoPanel);
    }

    /**
     * 重置大小
     * @param height2 
     * @param width2 
     */
	public void resetSize(int width2, int height2) {
		this.height=height2;
		this.width = width2 - MyPhotoConstantsUI.CALALOG_WIDTH;
		System.out.println(this.height+ "======" + this.width);
		this.setPreferredSize(new Dimension( this.width,this.height));
		this.setSize(new Dimension(MyPhotoConstantsUI.CALALOG_WIDTH,this.height));
		topPanel.setPreferredSize(new Dimension(this.width, 70));//设置JPanel的大小
		topPanel.setSize(new Dimension(this.width, 70));
	}


}
