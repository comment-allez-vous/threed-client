package com.hz.ui.myphoto.panel;

import com.hz.ui.myphoto.Label.LoadPhotoLabel;
import com.hz.ui.myphoto.bean.PhotoBean;
import com.hz.ui.myphoto.layout.VFlowLayout;
import com.hz.utils.FileUtil;
import com.hz.utils.ProjectConfigUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by hasee on 2017/9/3.
 * 图片信息展示面板
 */
@SuppressWarnings("serial")
public class PhotoInfoPanel extends JPanel {

    //要展示的图片信息
    private PhotoBean photoBean;

    private JLabel modellabel;
    private JLabel imagelabel;

    public PhotoInfoPanel(PhotoBean photoBean) {
        this.photoBean = photoBean;
        init();
    }


    private void init(){
        this.setPreferredSize(new Dimension(photoBean.getWidth(),photoBean.getImageheight()+photoBean.getInfoheight()));
        this.setLayout(new VFlowLayout());
        addImage();
        addInfo();
        drapAndDrop();
    }


    private void addImage(){
        if(photoBean.getWidth()!=0&& photoBean.getImageheight()!=0){
//            modellabel=new JLabel();
//            ImageIcon image = new ImageIcon(photoBean.getImagePath());
//            image.setImage(image.getImage().getScaledInstance(photoBean.getWidth(),photoBean.getImageheight(),Image.SCALE_DEFAULT));
//            modellabel.setIcon(image);
        	 modellabel=new JLabel();
        	 modellabel.setText("<html><img style=\"width:"+photoBean.getWidth()+"px;height:"+photoBean.getImageheight()+"px;\" src='"+photoBean.getImagePath()+"'></img></html>");
        	 
        	 
        	 
        	 this.add(modellabel);
        }
    }


    private void addInfo(){
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new GridLayout(1,5));
        jPanel.add(new JLabel(photoBean.getName()));
        jPanel.add(new JLabel(photoBean.getChargeType()));
        //jPanel.add(new JLabel("收藏"));
        JLabel loadLabel= new JLabel("下载");
        loadLabel.addMouseListener(new MouseAdapter(){
            public void mouseClicked (MouseEvent e){
            	boolean result=ProjectConfigUtil.downloadPhoto("huangzhuo", photoBean.getPhotoId(), "image",photoBean.getImageFilePath(),photoBean.getPhotoId()); //下载图片
				if(!result){
					 JOptionPane.showMessageDialog(null,"下载图片失败，请稍后再试","错误",JOptionPane.ERROR_MESSAGE);
					 return;
				}
            	boolean madResult=ProjectConfigUtil.downloadPhoto("huangzhuo", photoBean.getPhotoId(), "3dmax",photoBean.getMax3dFilePath(),photoBean.getPhotoId()); //下载3dmax
           
            	if(!madResult){
					 JOptionPane.showMessageDialog(null,"下载3d模型失败，请稍后再试","错误",JOptionPane.ERROR_MESSAGE);
					 return ;
            	}
            }
          });
        jPanel.add(loadLabel);  //用户名暂时写死
        
        
        imagelabel=new JLabel("拖拽");
        jPanel.add(imagelabel);
        this.add(jPanel);
    }

    /**
     * 拖拽功能
     */
    private void drapAndDrop(){
        DragSource dragSource=DragSource.getDefaultDragSource();
        //拖拽之前先判断是否有下载图片
        //对图片增加3dmax拖拽功能
        boolean maxFilsIsExist=FileUtil.judeExists(photoBean.getModelPath());
        if(maxFilsIsExist){
        	
        	
        	   dragSource.createDefaultDragGestureRecognizer(modellabel, DnDConstants.ACTION_COPY,
                       new PhotoDragGestureListener(FileUtil.getMaxFile(photoBean.getModelPath())));

        }else{
        	//JOptionPane.showMessageDialog(null,"您没有下载该图片，请下载之后再拖拽图片","错误",JOptionPane.ERROR_MESSAGE);
        	return ;
        }
     
        //增加按钮添加对图片的拖拽功能
        boolean imageFilsIsExist=FileUtil.judeExists(photoBean.getImageFilePath());
        if(imageFilsIsExist){
        dragSource.createDefaultDragGestureRecognizer(imagelabel, DnDConstants.ACTION_COPY,
                new PhotoDragGestureListener(FileUtil.getFile(photoBean.getImageFilePath())));
        }else{
        	//JOptionPane.showMessageDialog(null,"您没有下载该图片，请下载之后再拖拽图片","错误",JOptionPane.ERROR_MESSAGE);
        	return ;
        }
    }


}

class PhotoDragGestureListener extends DragSourceAdapter implements DragGestureListener {
    private File file;

    Cursor cursor;

    public PhotoDragGestureListener(File file) {
        this.file = file;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {

        Transferable transferable=new PhotoTransferable(file);

        dge.startDrag(DragSource.DefaultLinkDrop,transferable);


    }
}


class PhotoTransferable implements Transferable{


    private File file;

    public PhotoTransferable(File file) {
        this.file = file;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] df = new DataFlavor[1];
        df[0] = DataFlavor.javaFileListFlavor;
        return df;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if(flavor == DataFlavor.javaFileListFlavor) {
            return true;
        }
        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        java.util.List list = new ArrayList();
        list.add(file);
       // File file=new File("F:\\necss\\fff\\threed-client\\icon\\myphoto\\photo.png");
       // list.add(file);
        return list;
    }
}