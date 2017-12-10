package com.hz.ui.myphoto.panel;

import com.hz.ui.myphoto.Label.CatalogTopLabel;
import com.hz.ui.myphoto.Label.MypohotoJLabel;
import com.hz.ui.myphoto.XmlUtil.AnalysisCatalogXml;
import com.hz.ui.myphoto.bean.CatalogBean;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.ui.myphoto.control.AppControl;
import com.hz.ui.myphoto.layout.VFlowLayout;
import com.hz.utils.ProjectConfigUtil;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by hasee on 2017/9/1.
 */
public class CategoryPanel extends JPanel {
    private int width;
    private int height;
    private ImageIcon background;
    private String userName; //用户名

    
    private JScrollPane jScrollPane;



    public CategoryPanel(int _width, int _heigth, String userName){
    	this.setUserName(userName);
        this.width=_width;
        this.height=_heigth;
        loadCatalog();
        addComponent();
        setVisible(true);
    }

    public void loadCatalog(){
    	 List<String> catalogId=ProjectConfigUtil.getCatalogId(); //得到所有子目录，如果子目录查询失败或者为空，则不修改目录的配置文件
    	 if(catalogId.size()>0){
    	    	String result1=ProjectConfigUtil.getCatalogForServer("0"); //先加载所有目录
    	    	System.out.println("++++++++++result1+++++++"+result1);
    	    	
    	    	for(String id:catalogId){
    	    		if(!"0".equals(id)){
    	    			String result2=ProjectConfigUtil.getCatalogForServer(id); //再加载子目录
    	    			System.out.println("++++++++++id+++++++"+id);
    	    			System.out.println("++++++++++result2+++++++"+result2);
    	    		}
    	    	}
    	 }
    }

    public void addComponent(){
        this.setLocation(MyPhotoConstantsUI.CALALOG_X, MyPhotoConstantsUI.CALALOG_Y);
        this.setPreferredSize(new Dimension(MyPhotoConstantsUI.CALALOG_WIDTH,this.height));

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
        this.add(loginPanel());
        this.add(catalogPanel());
        JPanel menupanel=menuPanel();
        jScrollPane=new JScrollPane(menupanel);
        jScrollPane.setPreferredSize(new Dimension(this.width,this.height));
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        this.add(jScrollPane);

    }

    /**
     * 登录模块
     * @return
     */
    public JPanel loginPanel(){
        JPanel loginpanel=new JPanel();
        loginpanel.setOpaque(false);
        loginpanel.setSize(this.width,100);
        //width=200
        loginpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel userLabel=new MypohotoJLabel(60,60,MyPhotoConstantsUI.USER_PIC_PATH);
        loginpanel.add(userLabel);
        JLabel userName=new MypohotoJLabel(this.getUserName(),SwingConstants.CENTER,Color.WHITE);
        loginpanel.add(userName);

        return loginpanel;
    }


    private JPanel catalogPanel(){
        JPanel catalogPanel=new JPanel();
        catalogPanel.setOpaque(false);
        catalogPanel.setSize(this.width,100);
        catalogPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
        JLabel catalog2=new MypohotoJLabel(40,30,MyPhotoConstantsUI.CATALOG_BENDI);
        catalogPanel.add(catalog2);
        JLabel catalog3=new MypohotoJLabel(40,30,MyPhotoConstantsUI.CATALOG_YUN);
        catalogPanel.add(catalog3);
        return catalogPanel;
    }



    private JPanel menuPanel(){
        JPanel menuPanel=new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setSize(this.width,this.height*2);
        menuPanel.setLayout(new VFlowLayout());
        CatalogBean catalogBean=AnalysisCatalogXml.analysis();
        final java.util.List<String> catalogTypeList=catalogBean.getCatalogType();
        final Map<String, java.util.List<String[]>> map=catalogBean.getMap();
        final Map<String,Integer> cataNum=catalogBean.getCatalogNumMap();
        if(catalogTypeList.size()>0){
            for(String catalogType:catalogTypeList){
                CatalogItemPanel catalogItemPanel=new CatalogItemPanel(this.width,catalogType,cataNum.get(catalogType),map.get(catalogType));
                catalogItemPanel.setFather(menuPanel);
                catalogItemPanel.initialization();
                menuPanel.add(catalogItemPanel);

            }
        }
        JLabel catalog4=new MypohotoJLabel("自定义选项项",SwingConstants.LEFT,Color.WHITE);
        menuPanel.add(catalog4);
        return menuPanel;
    }


    @Override
    protected void paintComponent(Graphics gs) {
        setSize(this.width,this.height);
        Graphics2D g= (Graphics2D) gs;
        super.paintComponent(g);
        background=new ImageIcon(MyPhotoConstantsUI.PHOTO_TEST_BACKGOUND1);
        background.setImage(background.getImage().getScaledInstance(this.width,this.height,Image.SCALE_DEFAULT));
        g.drawImage(this.background.getImage(),0,0,this.width,this.height,this);
    }


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void resetSize(int width2, int height2) {
		this.height=height2;
		this.setPreferredSize(new Dimension(MyPhotoConstantsUI.CALALOG_WIDTH,this.height));
		jScrollPane.setPreferredSize(new Dimension(this.width,this.height));
		this.setSize(new Dimension(MyPhotoConstantsUI.CALALOG_WIDTH,this.height));
		this.jScrollPane.setSize(new Dimension(MyPhotoConstantsUI.CALALOG_WIDTH,this.height));
	}

    
}
