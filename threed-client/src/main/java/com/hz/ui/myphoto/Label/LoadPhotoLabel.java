package com.hz.ui.myphoto.Label;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

import com.hz.ui.myphoto.bean.PhotoBean;
import com.hz.utils.ProjectConfigUtil;

@SuppressWarnings("serial")
public class LoadPhotoLabel extends JLabel implements MouseListener {

	private String name;
	private PhotoBean photoBean;

	public LoadPhotoLabel(String name, PhotoBean photoBean, String text) {
		this.setName(name);
		this.setText(text);
		this.setPhotoBean(photoBean);
	}

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhotoBean getPhotoBean() {
		return photoBean;
	}

	public void setPhotoBean(PhotoBean photoBean) {
		this.photoBean = photoBean;
	}


}
