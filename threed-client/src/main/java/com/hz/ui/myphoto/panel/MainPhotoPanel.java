package com.hz.ui.myphoto.panel;

import com.hz.ui.myphoto.bean.PhotoBean;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/9/2.
 * 主体部分：内容展示部分
 */
@SuppressWarnings("serial")
public class MainPhotoPanel extends JScrollPane {

    private int width;

    private int height;

    private JPanel photopanel;

    private List<PhotoBean> data=new ArrayList<PhotoBean>();


    public void setData(List<PhotoBean> data) {
        this.data = data;
    }

//    private void createData(){
//        for (int i=0;i<10;i++){
//            PhotoBean photoBean=new PhotoBean();
//            photoBean.setWidth(200);
//            photoBean.setImageheight(300);
//            photoBean.setInfoheight(30);
//            photoBean.setName("");
//            photoBean.setImagePath(MyPhotoConstantsUI.PHOTO_TEST_SHOW);
//            photoBean.setModelPath(MyPhotoConstantsUI.MAX_TEST_SHOW);
//            data.add(photoBean);
//        }
//    }


    public MainPhotoPanel(int width,int height){
        this.width=width;
        this.height=height;
//        createData();
//        init();
    }

    public void init(){
        this.setPreferredSize(new Dimension(this.width,this.height));
        int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        photopanel=photoPanel();
        this.setViewportView(photopanel);
        this.setVisible(true);
    }

    private JPanel photoPanel(){
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,10));
        jPanel.setPreferredSize(new Dimension(this.width-10,getPanelHeight()));

        if(data.size()>0){
        	  for (PhotoBean photo:data) {
                  PhotoInfoPanel infoPanel = new PhotoInfoPanel(photo);
                  jPanel.add(infoPanel);
              }
        }
      

        jPanel.setVisible(true);
        return jPanel;

    }


    /**
     * 根据要显示的图片数量计算要设置的高度
     * @return
     */
    private int getPanelHeight(){
    	if(data.size()>0){
    		int photowidth=data.get(0).getWidth();
            int photoheigth=data.get(0).getImageheight()+data.get(0).getInfoheight();
            //每行显示多少个
            int colNum=(this.width-30)/photowidth;
            double rownum=Math.ceil((float)data.size()/colNum);
            int height= (int) ((rownum*photoheigth)+20*(rownum+1));
            return height;
    	}
        
        return height;
    }





}
