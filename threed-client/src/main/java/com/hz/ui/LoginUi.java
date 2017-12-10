package com.hz.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.alibaba.fastjson.JSONObject;
import com.hz.RButton;
import com.hz.ui.myphoto.Label.LinkLabel;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.utils.CheckLoginUtil;
import com.hz.utils.OpenBrowserUtil;
import com.hz.utils.StringUtil;

@SuppressWarnings("serial")
public class LoginUi extends JFrame{
	 public JPanel pnluser;
	 public JLabel lbluserLogIn;
	 public JLabel lbluserName;
	 public JLabel lbluserPWD;
	 public JLabel lblBeh;
	 public JTextField txtName;
	 public JPasswordField pwdPwd;
	 public JButton btnSub;
	 public LinkLabel btnReset;
	 public JButton btnMin;
	 public JButton btnClose;
	//用于处理拖动事件，表示鼠标按下时的坐标，相对于JFrame
	 int xOld = 0;
	 int yOld = 0;
	 public LoginUi(){
		  pnluser = new JPanel(){
	             public void paintComponent(Graphics g) {
		                ImageIcon icon =
		                         new ImageIcon(MyPhotoConstantsUI.LOGIN_BACKGROUND);
	                // 图片随窗体大小而变化
		                g.drawImage(icon.getImage(), 0, 0, this.getSize().width,this.getSize().height,this);
		            }
		    };
		    txtName = new JTextField(){
	            public void paint(Graphics g) {
	                super.paint(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setStroke(new BasicStroke(3.0f));
	                g2d.setPaint(Color.WHITE);
	                g2d.drawLine(0, txtName.getHeight() - 1, txtName.getWidth(), txtName.getHeight() - 1);
	            }
	      };
		    pwdPwd = new JPasswordField(){
	            public void paint(Graphics g) {
	                super.paint(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setStroke(new BasicStroke(3.0f));
	                g2d.setPaint(Color.WHITE);
	                g2d.drawLine(0, pwdPwd.getHeight() - 1, pwdPwd.getWidth(), pwdPwd.getHeight() - 1);
	            }
	        };
		  lbluserLogIn = new JLabel();
		  lbluserName = new JLabel();
		  lbluserPWD = new JLabel();
		  btnSub = new JButton(){
			  public void paint(Graphics g) {
	                super.paint(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setStroke(new BasicStroke(3.0f));
	                g2d.setPaint(Color.WHITE);
	                g2d.drawArc(0,0 , 40,  40, 90, 180);
	                g2d.drawArc(btnSub.getWidth()-40,0 , 40, 40, -270, -180);
	                g2d.drawLine(20, btnSub.getHeight() - 1, btnSub.getWidth()-20, btnSub.getHeight() - 1);
	                g2d.drawLine(20, btnSub.getHeight() - 40, btnSub.getWidth()-20, btnSub.getHeight() - 40);
	            }
		  };
		  btnReset = new LinkLabel("",CheckLoginUtil.getRegisterUrl());
		  btnMin = new JButton();
		  btnClose = new JButton();
		  lblBeh = new JLabel();
		  userInit();
		}
		 
		 public void userInit(){
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭框架的同时结束程序
		//  this.setBounds(250,250,250,250);
		  //设置去掉边框  
	      this.setUndecorated(true);  
	      
	      
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
	        LoginUi.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
	        }
	      });
	        
	      this.setVisible(true);  
		  this.setSize(540,410);//设置框架大小为长300,宽200
		  this.setResizable(false);//设置框架不可以改变大小
		  this.setTitle("用户登录");//设置框架标题
		  //设置位置居中
		  int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		  int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 200) / 2;
		  this.setLocation(w, h);
		  
		  this.pnluser.setLayout(null);//设置面板布局管理
		  this.pnluser.setBackground(Color.cyan);//设置面板背景颜色

		  //关闭按钮
		  this.btnClose.setText("");
		  this.btnClose.setIcon(new ImageIcon("icon/11.png")); 
		  this.btnClose.setBounds(507,0,33,33);//设置大小和位置
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
		  //最小化按钮
		  this.btnMin.setText("");
		  this.btnMin.setIcon(new ImageIcon("icon/09.png")); 
		  this.btnMin.setBounds(474,0,33,33);//设置大小和位置
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
		  //注册按钮
		  this.btnReset.setBounds(470,380,60,20);
		  this.btnReset.setBorder(null);
		  this.btnReset.setOpaque(false);
		  this.btnReset.setIcon(new ImageIcon("icon/05.png")); 
		  //登录按钮
		  this.btnSub.setText("登录");
		  this.btnSub.setForeground(Color.WHITE);
		  this.btnSub.setFont(new Font("宋体",Font.BOLD ,18));//设置标签字体
		  this.btnSub.setBounds(140,290,250,40);
		  this.btnSub.setBorder(null);
		  this.btnSub.setContentAreaFilled(false);  //设置按钮透明
		  this.btnSub.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){btnsub_ActionEvent(e);}}); 
		  //用户名标题
		  this.lbluserName.setText("用户名:");
		  this.lbluserName.setForeground(Color.WHITE);
		  this.lbluserName.setFont(new Font("宋体",Font.BOLD ,22));//设置标签字体
		  this.lbluserName.setBounds(120,155,140,20);
		  this.lbluserName.setBorder(null);
		  this.lbluserName.setOpaque(false);
		  //用户名输入框
		  this.txtName.setBorder(null);
		  this.txtName.setOpaque(false);
		  this.txtName.setBounds(200,150,180,30);
		  this.txtName.setFont(new Font("宋体", Font.PLAIN,24));
		  //密码标题
		  this.lbluserPWD.setText("密  码:");
		  this.lbluserPWD.setForeground(Color.WHITE);
		  this.lbluserPWD.setFont(new Font("宋体",Font.BOLD ,22));//设置标签字体
		  this.lbluserPWD.setBounds(120,195,140,25);
		  this.lbluserPWD.setBorder(null);
		  this.lbluserPWD.setOpaque(false);
		  //密码输入框
		  this.pwdPwd.setBorder(null);
		  this.pwdPwd.setOpaque(false);
		  this.pwdPwd.setBounds(200,190,180,30);
		  this.pwdPwd.setFont(new Font("宋体", Font.PLAIN,24));
		  //LOGO样式
		  this.lbluserLogIn.setText("用户登录");//设置标签标题
		  this.lbluserLogIn.setFont(new Font("宋体",Font.BOLD | Font.ITALIC,14));//设置标签字体
		  this.lbluserLogIn.setForeground(Color.RED);//设置标签字体颜色
		  this.lbluserLogIn.setBounds(162,35,175,90);//设置标签x坐标120,y坐标15,长60,宽20
		  this.lbluserLogIn.setIcon(new ImageIcon("icon/01.png")); 
		  //软件版本号
		  this.lblBeh.setIcon(new ImageIcon("icon/04.png")); 
		  this.lblBeh.setForeground(Color.WHITE);
		  this.lblBeh.setBounds(20, 380, 100, 30);
		  this.lblBeh.setBorder(null);
		  this.lblBeh.setOpaque(false);
		  
		  this.pnluser.add(lbluserLogIn);//加载标签到面板
		  this.pnluser.add(lbluserName);
		  this.pnluser.add(lbluserPWD);
		  this.pnluser.add(txtName);
		  this.pnluser.add(pwdPwd);
		  this.pnluser.add(btnSub);
		  this.pnluser.add(btnReset);
		  this.pnluser.add(btnMin);
		  this.pnluser.add(btnClose);
		  this.pnluser.add(lblBeh);
		  this.add(pnluser);//加载面板到框架
		  this.setVisible(true);//设置框架可显  
		 }
		 
		 //登陆
		 public void btnsub_ActionEvent(ActionEvent e){
		  String name = txtName.getText();
		  String pwd = String.valueOf(pwdPwd.getPassword());
		  if(name.equals("")){
			   JOptionPane.showMessageDialog(null,"账号不能为空","错误",JOptionPane.ERROR_MESSAGE);
			   return;
		  }else if (pwd.equals("")){
			   JOptionPane.showMessageDialog(null,"密码不能为空","错误",JOptionPane.ERROR_MESSAGE);
			   return;
		  }else{
			  lbluserLogIn.setText("登录中。。。");
			  String result=CheckLoginUtil.chechLogin(name, pwd);
			 if(StringUtil.isEmpty(result)){
				 lbluserLogIn.setText("登录");
				 JOptionPane.showMessageDialog(null,"账号或密码错误","错误",JOptionPane.ERROR_MESSAGE);
				   return;
			 }else{
				 JSONObject 	json = JSONObject.parseObject(result);//fromObject(result);
				 String results = json.getString("success");
				 if("true".equals(results)){
					 EventQueue.invokeLater(new Runnable() {
						public void run() {
							MyPhotoShow window = new MyPhotoShow(txtName.getText());
							//window.setVisible(true);
						}
					});
						this.dispose();//销毁登陆窗口
				 }else{
					 JOptionPane.showMessageDialog(null,"账号或密码错误","错误",JOptionPane.ERROR_MESSAGE);
					   return;
				 }
//				 EventQueue.invokeLater(new Runnable() {
//						public void run() {
//						}
//					});
//				    this.dispose();//销毁登陆窗口
			 }
			
		  }

		 }
		 
		 //注册
		 public void btnreset_ActionEvent(ActionEvent e){
			 OpenBrowserUtil.openURL(CheckLoginUtil.getRegisterUrl());
		 }
		 
		 //关闭
		 public void btnClose_ActionEvent(ActionEvent e){
			 System.exit(0);  
		 }
		 
		 
		 //最小化
		 public void btnMin_ActionEvent(ActionEvent e){
			 setExtendedState(JFrame.ICONIFIED);
		 }
		 
		 public static void main(String[] args){
		  new LoginUi();
		 }
}
