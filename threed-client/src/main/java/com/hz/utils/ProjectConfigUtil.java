package com.hz.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.ui.MyPhotoShow;
import com.hz.ui.myphoto.XmlUtil.AnalysisCatalogXml;
import com.hz.ui.myphoto.bean.CatalogInfoBean;
import com.hz.ui.myphoto.bean.PhotoInfoBean;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;

/**
 * 读取配置文件projectConfig.properties
 * @author ZhouPeiling
 *
 */
public class ProjectConfigUtil {
	 public final static String CURRENT_DIR = System.getProperty("user.dir");
	 public final static String PROJECTCONFIG_PATH= CURRENT_DIR+File.separator +"config"+File.separator+"projectConfig.properties";
	
	 /**
	  * 通过配置名查找配置内容
	  * @param keyName
	  * @return
	  */
	 @SuppressWarnings("unused")
	public static String getProperty(String keyName){
		
		 String propertyValue="";
		 if(StringUtil.isEmpty(keyName)){
			 return propertyValue;
		 }
		 Properties prop = new Properties();     
		//读取属性文件projectConfig.properties
         try {
			InputStream in = new BufferedInputStream (new FileInputStream(PROJECTCONFIG_PATH));
			 prop.load(in);     ///加载属性列表
			 Iterator<String> it=prop.stringPropertyNames().iterator();
			 while(it.hasNext()){
				 String key=it.next();
				 if(keyName.equals(key)){
			         propertyValue= prop.getProperty(keyName);
				 }
			  }
			 in.close();
		} catch (Exception e) {
			return propertyValue;
		} 
		 return propertyValue;
		 
	 }
	 
	 /**
	  * 查询目录，并将目录加载到配置xml中
	  * @param catalogId
	  * @return
	  */
	 public static String getCatalogForServer(String catalogId){
		 String resultInfo="";
		 List<CatalogInfoBean> catalogList=new ArrayList<CatalogInfoBean>();
		 if(StringUtil.isEmpty(catalogId)){
			 catalogId="0";  //总目录
		 }
		 String catalugUrl=StringUtil.handleNULL(ProjectConfigUtil.getProperty("project.qryCatalog.url"),"http://39.108.216.43:8088/modelType/listByPid");
		 String catalogInfo=HttpUtil.getServerResult(catalugUrl+"?pid="+catalogId);
		 if(StringUtil.isEmpty(catalogInfo)){
			 
			   return "目录ID为空";
		 }else{
			 JSONObject 	json = JSONObject.parseObject(catalogInfo);
			 if(json==null){
				 return "查询目录失败";
			 }
			 String results = json.getString("success");
			 String msg=json.getString("msg");
			 if("true".equals(results)){
				 JSONArray list=json.getJSONArray("obj");
				 if(list.size()>0){
					 Iterator<Object> it = list.iterator();
					 while (it.hasNext()) {
			                JSONObject ob = (JSONObject) it.next();
			                String classid=ob.getString("classid");  //子目录id
			                String classname=ob.getString("classname"); //目录名称
			                String bclassid=ob.getString("bclassid"); //父目录id
			                CatalogInfoBean bean=new CatalogInfoBean();
			                bean.setBclassid(bclassid);
			                bean.setClassid(classid);
			                bean.setClassname(classname);
			                catalogList.add(bean);
			            }
				 }
					
			 }else{
				 return msg;
			 }
		 }
		 if(catalogList.size()>0){
			 resultInfo= AnalysisCatalogXml.createNodes(catalogId, catalogList);
		 }
		 return resultInfo;   //等于0时才是将服务端的目录添加到了xml中
	 }
	 
	 //得到所有子目录
	 public static List<String> getCatalogId(){
		 List<String> catalogId=new ArrayList<String>();
		 String catalugUrl=StringUtil.handleNULL(ProjectConfigUtil.getProperty("project.qryCatalog.url"),"http://39.108.216.43:8088/modelType/listByPid");
		 String catalogInfo=HttpUtil.getServerResult(catalugUrl+"?pid=0");
		 
		 JSONObject 	json = JSONObject.parseObject(catalogInfo);
		 if(json==null){
			 return catalogId;
		 }
		 String results = json.getString("success");
		 String msg=json.getString("msg");
		 if(!"true".equals(results)){
			 return catalogId;
		 }
		JSONArray list=json.getJSONArray("obj");
		
		if(list.size()<=0){
			return catalogId;
		}
		Iterator<Object> it = list.iterator();
		while (it.hasNext()) {
		     JSONObject ob = (JSONObject) it.next();
		     String classid=ob.getString("classid");  //子目录id
		     String bclassid=ob.getString("bclassid"); //父目录id
		          if("0".equals(bclassid)){
		              catalogId.add(classid);
		          }
		  }
		
	  return catalogId;

	 }
	 
	 public static boolean downloadPhoto(String name,String photoId,String imgType,String path,String newFileName){
		 try {
			String catalugUrl=StringUtil.handleNULL(ProjectConfigUtil.getProperty("project.downloadPhoto.url"),"http://39.108.216.43:8088/download/getFile?username=huangzhuo&path=F");
			//catalugUrl ?username=huangzhuo&id=75&type=image
			catalugUrl+="?username="+name+"&id="+photoId+"&type="+imgType;
			String catalogInfo=HttpUtil.getServerResult(catalugUrl);
			 JSONObject 	json = JSONObject.parseObject(catalogInfo);
			 String results = json.getString("success");
			 String msg=json.getString("msg");
			 if(!"true".equals(results)){
				 return false;
			 }
			 JSONObject object=JSONObject.parseObject(json.getString("obj"));
			 if(object==null){
				 return false;
			 }
			String id=object.getString("baseStr");  
			if(StringUtil.isEmpty(id)){
				return false;
			}
			String fileName=object.getString("fileName");  
			File file=FileUtil.getFileByBaseStr(path+"photoId.zip", id);
			ZipUtil.unZipFiles(file,path,newFileName);
			return true;
		} catch (IOException e) {
			 return false;
		}
		 
	 }
	 
	 
	 /**
	  * 查询图片详情
	  * @param classid
	  * @param username
	  */
	 public static List<PhotoInfoBean> showPhotoInfo(String classid ,String username){
		 String catalugUrl=StringUtil.handleNULL(ProjectConfigUtil.getProperty("project.showPhotoinfo.url"),"http://39.108.216.43:8088/modelType/listFilesByClassId");
		 catalugUrl+="?classid="+classid+"&username="+username;
		 String catalogInfo=HttpUtil.getServerResult(catalugUrl);
		 JSONObject 	json = JSONObject.parseObject(catalogInfo);
		 List<PhotoInfoBean> photoList=new ArrayList<PhotoInfoBean>();
		 
		 
		 String results = json.getString("success");
		 String msg=json.getString("msg");
		 if(!"true".equals(results)){
			 JOptionPane.showMessageDialog(null,"查询图片为空","错误",JOptionPane.ERROR_MESSAGE);
			 return photoList;
		 }
		JSONArray list=json.getJSONArray("obj");
		
		if(list.size()<=0){
			return photoList;
		}
		Iterator<Object> it = list.iterator();
		while (it.hasNext()) {
			 PhotoInfoBean photoInfoBean=new PhotoInfoBean();
		     JSONObject ob = (JSONObject) it.next();
		     String id=ob.getString("id");  
		     String title=ob.getString("title"); 
		     String titlepic=ob.getString("titlepic"); 
		     String jifen=ob.getString("jifen"); 
		     photoInfoBean.setId(id);
		     photoInfoBean.setJifen(jifen);
		     photoInfoBean.setTitle(title);
		     photoInfoBean.setTitlepic(titlepic);
		     photoList.add(photoInfoBean);   
		  }
		 return photoList;
	 }
	 
	 public static void main(String[] args) {
		 ProjectConfigUtil.getCatalogForServer("0");
		}
}
