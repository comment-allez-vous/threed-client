package com.hz.ui.myphoto.panel;

import com.hz.ui.LoginUi;
import com.hz.ui.MyPhotoShow;
import com.hz.ui.myphoto.Label.LinkLabel;
import com.hz.ui.myphoto.Label.MypohotoJLabel;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.utils.CheckLoginUtil;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by hasee on 2017/9/2.
 * 主体上部面板
 */
public class MainTopPanel extends JPanel {
	private MyPhotoShow mainWindow;
	
	public JButton btnClose; //关闭按钮
	public JButton btnMin;  //最小化按钮
	public JButton btnMax; //最大化按钮
	public LinkLabel btnLogo; //Logo，点击可以进入网站首页
	public JLabel searchLb;//搜索展示标签
	public JTextField searchTxt;//搜索栏
	
	public JButton btnMf;//免费按钮
	public JButton btnFh;//付费按钮
	public JButton btnArd;//按热度按钮
	public JButton btnAgxsj;//按更新时间按钮
	public JLabel lbMf;//免费文本标签
	public JLabel lbFh;//付费文本标签
	public JLabel lbArd;//按热度文本标签
	public JLabel lbAgxsj;//按更新时间文本标签
	
	 //用于处理拖动事件，表示鼠标按下时的坐标，相对于JFrame
	 int xOld = 0;
	 int yOld = 0;
    //宽度
    private int width;
    
    private int mfFlag=0;//免费标签，1:选中，0：未选中
    private int fhFlag=0;//付费标签，1:选中，0：未选中
    private int ardFlag=0;//按热度标签，1:选中，0：未选中
    private int agxsjFlag=0;//按更新时间标签，1:选中，0：未选中

    public MainTopPanel(int width,MyPhotoShow _mainWindow){
        this.width=width;
        this.mainWindow=_mainWindow;
        init();
    }

    private void init(){
    	this.setLayout(null);//设置面板布局管理
//        this.setLayout(new BorderLayout());
//
//        this.add(searchPanel(),BorderLayout.WEST);
//        this.add(categoryPanel(),BorderLayout.CENTER);
//        this.add(timePanel(),BorderLayout.EAST);
    	//Logo和搜索按钮
    	logoAndSeach();
    	//其他的搜索条件
    	otherSearch();
    	//显示关闭和最小化按钮
    	showCloseBtn();
    	
    	
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
	        System.out.println(xx);
	        System.out.println(yy);
	        mainWindow.setLocation(33, 44);//设置拖拽后，窗口的位置
	        }
	      });
    	
//	      this.addMouseMotionListener(new MouseMotionAdapter() {
//		        @Override
//		        public void mouseDragged(MouseEvent e) {
//		        int xOnScreen = e.getXOnScreen();
//		        int yOnScreen = e.getYOnScreen();
//		        System.out.println(xOnScreen);
//		        mainWindow.changeStation();
//		        }
//		      });
    }
    
    /**
     * 显示关闭和最小化按钮
     */
    public void showCloseBtn(){
    	//关闭按钮
    	this.btnClose=new JButton();
    	this.btnClose.setText("");
		this.btnClose.setIcon(new ImageIcon("icon/11.png")); 
		this.btnClose.setBounds(990,0,33,33);//设置大小和位置
		this.btnClose.setContentAreaFilled(false);  //设置按钮透明
		this.btnClose.setBorder(null);//设置没有边框
		this.btnClose.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){btnClose_ActionEvent(e);}});
		this.btnClose.addMouseListener(new MouseListener() {  
	         public void mouseReleased(MouseEvent e) {}            
	         public void mousePressed(MouseEvent e) {}             
	         public void mouseExited(MouseEvent e) {
	        	 btnClose.setIcon(new ImageIcon("icon/11.png")); 
	         }          
	         public void mouseEntered(MouseEvent e) {
	        	 btnClose.setIcon(new ImageIcon("icon/12.png")); 
	         }         
	         public void mouseClicked(MouseEvent e) {}  
	    });
    	//最大化按钮
		this.btnMax=new JButton();
    	this.btnMax.setText("");
		this.btnMax.setIcon(new ImageIcon("icon/27.png")); 
		this.btnMax.setBounds(this.width-66,0,33,33);//设置大小和位置
		this.btnMax.setContentAreaFilled(false);  //设置按钮透明
		this.btnMax.setBorder(null);//设置没有边框
		this.btnMax.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){btnMax_ActionEvent();}});
		this.btnMax.addMouseListener(new MouseListener() {  
	         public void mouseReleased(MouseEvent e) {}            
	         public void mousePressed(MouseEvent e) {}             
	         public void mouseExited(MouseEvent e) {
	        	 btnMax.setIcon(new ImageIcon("icon/27.png")); 
	         }          
	         public void mouseEntered(MouseEvent e) {
	        	 btnMax.setIcon(new ImageIcon("icon/26.png")); 
	         }         
	         public void mouseClicked(MouseEvent e) {}  
	    });
    	//最小化按钮
	   this.btnMin=new JButton();
	   this.btnMin.setText("");
	   this.btnMin.setIcon(new ImageIcon("icon/09.png")); 
	   this.btnMin.setBounds(this.width-99,0,33,33);//设置大小和位置
	   this.btnMin.setContentAreaFilled(false);  //设置按钮透明
	   this.btnMin.setBorder(null);//设置没有边框
	   this.btnMin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){btnMin_ActionEvent(e);}});
	   this.btnMin.addMouseListener(new MouseListener() {  
            public void mouseReleased(MouseEvent e) {}            
            public void mousePressed(MouseEvent e) {}             
            public void mouseExited(MouseEvent e) {
            	btnMin.setIcon(new ImageIcon("icon/09.png")); 
            }          
            public void mouseEntered(MouseEvent e) {
            	btnMin.setIcon(new ImageIcon("icon/10.png")); 
            }         
            public void mouseClicked(MouseEvent e) {}  
      });
	   this.add(btnMin);
	   this.add(btnMax);
	   this.add(btnClose);
    }
    //logo和Search
    public void logoAndSeach(){
    	this.btnLogo=new LinkLabel("",CheckLoginUtil.getRegisterUrl()); 
    	this.btnLogo.setBounds(20,15,60,30);
		this.btnLogo.setBorder(null);
		this.btnLogo.setOpaque(false);
		this.btnLogo.setIcon(new ImageIcon("icon/24.png")); 
		this.add(btnLogo);
		this.searchLb=new JLabel();
		this.searchLb.setBounds(80, 10, 276, 40);
		this.searchLb.setIcon(new ImageIcon("icon/31.png"));
		this.add(searchLb);
		this.searchTxt=new JTextField();
		this.searchTxt.setBorder(null);
		this.searchTxt.setBounds(130, 18, 210, 25);
		this.searchTxt.setBackground(null);
		this.add(searchTxt);
    }
    
    //展示其他搜索条件
    public void otherSearch(){
    	//免费
    	this.btnMf=new JButton();
    	this.btnMf.setText(null);
    	this.btnMf.setBounds(this.width-540, 16, 40, 30);
    	this.btnMf.setBorder(null);
    	this.btnMf.setContentAreaFilled(false);  //设置按钮透明
    	this.btnMf.setIcon(new ImageIcon("icon/19.png"));
    	this.btnMf.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){otherSearchChange(1);}});
    	this.add(btnMf);
    	this.lbMf=new JLabel("免费");
    	this.lbMf.setBounds(this.width-500, 16, 40, 30);
    	this.lbMf.setFont(new Font("宋体",Font.BOLD ,18));//设置标签字体
    	this.lbMf.setForeground(Color.gray);
    	this.add(lbMf);
    	//收费
    	this.btnFh=new JButton();
    	this.btnFh.setText(null);
    	this.btnFh.setBounds(this.width-460, 16, 40, 30);
    	this.btnFh.setBorder(null);
    	this.btnFh.setContentAreaFilled(false);  //设置按钮透明
    	this.btnFh.setIcon(new ImageIcon("icon/19.png"));
    	this.btnFh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){otherSearchChange(2);}});
    	this.add(btnFh);
    	this.lbFh=new JLabel("付费");
    	this.lbFh.setBounds(this.width-420, 16, 40, 30);
    	this.lbFh.setFont(new Font("宋体",Font.BOLD ,18));//设置标签字体
    	this.lbFh.setForeground(Color.gray);
    	this.add(lbFh);
    	//按热度
    	this.btnArd=new JButton();
    	this.btnArd.setText(null);
    	this.btnArd.setBounds(this.width-380, 16, 40, 30);
    	this.btnArd.setBorder(null);
    	this.btnArd.setContentAreaFilled(false);  //设置按钮透明
    	this.btnArd.setIcon(new ImageIcon("icon/19.png"));
    	this.btnArd.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){otherSearchChange(3);}});
    	this.add(btnArd);
    	this.lbArd=new JLabel("按热度");
    	this.lbArd.setBounds(this.width-340, 16, 60, 30);
    	this.lbArd.setFont(new Font("宋体",Font.BOLD ,18));//设置标签字体
    	this.lbArd.setForeground(Color.gray);
    	this.add(lbArd);
    	//按更新时间
    	this.btnAgxsj=new JButton();
    	this.btnAgxsj.setText(null);
    	this.btnAgxsj.setBounds(this.width-270, 16, 40, 30);
    	this.btnAgxsj.setBorder(null);
    	this.btnAgxsj.setContentAreaFilled(false);  //设置按钮透明
    	this.btnAgxsj.setIcon(new ImageIcon("icon/19.png"));
    	this.btnAgxsj.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){otherSearchChange(4);}});
    	this.add(btnAgxsj);
    	this.lbAgxsj=new JLabel("按更新时间");
    	this.lbAgxsj.setBounds(this.width-230, 16, 110, 30);
    	this.lbAgxsj.setFont(new Font("宋体",Font.BOLD ,18));//设置标签字体
    	this.lbAgxsj.setForeground(Color.gray);
    	this.add(lbAgxsj);
    }
    
    /**************************************下面是按钮点击事件*********************************************/	
     //关闭
	 public void btnClose_ActionEvent(ActionEvent e){
		 System.exit(0);  
	 }
	 
	 
	 //最小化
	 public void btnMin_ActionEvent(ActionEvent e){
		 mainWindow.btnMin_ActionEvent();
		 System.out.println(12);
	 }	
    
	 /**
	  * 最大化
	  */
	 public void btnMax_ActionEvent(){
		 mainWindow.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH); 
		 mainWindow.resetSize();
	 }
    private void otherSearchChange(int type) {
    	if(type==1){
    		if(this.mfFlag==0){
    			this.mfFlag=1;
        		this.btnMf.setIcon(new ImageIcon("icon/20.png"));
        	}else{
        		this.mfFlag=0;
        		this.btnMf.setIcon(new ImageIcon("icon/19.png"));
        	}
    	}else if(type==2){
    		if(this.fhFlag==0){
    			this.fhFlag=1;
        		this.btnFh.setIcon(new ImageIcon("icon/20.png"));
        	}else{
        		this.fhFlag=0;
        		this.btnFh.setIcon(new ImageIcon("icon/19.png"));
        	}
    	}else if(type==3){
    		if(this.ardFlag==0){
    			this.ardFlag=1;
        		this.btnArd.setIcon(new ImageIcon("icon/20.png"));
        	}else{
        		this.ardFlag=0;
        		this.btnArd.setIcon(new ImageIcon("icon/19.png"));
        	}
    	}else if(type==4){
    		if(this.agxsjFlag==0){
    			this.agxsjFlag=1;
        		this.btnAgxsj.setIcon(new ImageIcon("icon/20.png"));
        	}else{
        		this.agxsjFlag=0;
        		this.btnAgxsj.setIcon(new ImageIcon("icon/19.png"));
        	}
    	}
	}
    
    
    
    
    
    
    
    
    
    
    

    private JPanel searchPanel(){
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,20,10));
        JLabel searchPic= new MypohotoJLabel(MyPhotoConstantsUI.SEARCH_PIC_PATH);
       // JTextField searchField =new JTextField(35);
        panel.add(searchPic);
        panel.setBackground(Color.red);
      //  panel.add(searchField);
        return panel;
    }

   
    private JPanel categoryPanel(){
//        JPanel panel=new JPanel();
//        panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,15));
//        JButton allButton =new JButton("全部");
//        allButton.setContentAreaFilled(false);
//        JButton freeButton =new JButton("免费");
//        freeButton.setContentAreaFilled(false);
//        JButton payButton=new JButton("付费");
//        payButton.setContentAreaFilled(false);
//        panel.add(allButton);
//        panel.add(freeButton);
//        panel.add(payButton);
//        return panel;
    	
    	 JPanel panel=new JPanel();
         panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,10));
         JLabel searchPic= new MypohotoJLabel(MyPhotoConstantsUI.SEARCH_PIC_PATH);
         JTextField searchField =new JTextField(35);
         searchField.setBackground(Color.GRAY);
         panel.add(searchPic);
         panel.add(searchField);
         return panel;
    }


    private JPanel timePanel(){
    	JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,15));
        JButton allButton =new JButton("全部1");
        allButton.setContentAreaFilled(false);
        JButton freeButton =new JButton("免费");
        freeButton.setContentAreaFilled(false);
        JButton payButton=new JButton("付费");
        payButton.setContentAreaFilled(false);
        panel.add(allButton);
        panel.add(freeButton);
        panel.add(payButton);
        return panel;
//        JPanel panel=new JPanel();
//        panel.setLayout(new FlowLayout(FlowLayout.LEFT,20,15));
//        JComboBox timeBox=new JComboBox(new String []{"今天","一天前","三天前","半个月前"});
//        timeBox.setOpaque(false);
//        timeBox.setUI(new BasicComboBoxUI() {
//
//            public void installUI(JComponent comboBox) {
//
//                super.installUI(comboBox);
//
//                //listBox.setForeground(Color.WHITE);
//
//                //listBox.setSelectionBackground(new Color(0,0,0,0));
//
//                //listBox.setSelectionForeground(Color.BLACK);
//
//            }
//            /**
//
//             * 该方法返回右边的按钮
//
//             */
//
//            protected JButton createArrowButton() {
//
//                return super.createArrowButton();
//
//            }
//
//        });
//        panel.add(timeBox);
//        return panel;
    }

}
