package com.hz.ui.myphoto.XmlUtil;

import com.hz.ui.myphoto.bean.CatalogBean;
import com.hz.ui.myphoto.bean.CatalogInfoBean;
import com.hz.ui.myphoto.constants.MyPhotoConstantsUI;
import com.hz.utils.StringUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by hasee on 2017/9/2.
 * 解析目录的xml
 */
public class AnalysisCatalogXml {

    public static CatalogBean analysis(){
        CatalogBean catalogBean=null;
        try {
            SAXReader sax = new SAXReader();//创建一个SAXReader对象
            File xmlFile = new File(MyPhotoConstantsUI.CALALOG_XML_PATH);//根据指定的路径创建file对象
            Document document = sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
            Element root=document.getRootElement();//获取根节点
            catalogBean= getNodes(root);//从根节点开始遍历所有节点
            return catalogBean;
        }catch (Exception e){
            e.printStackTrace();
        }
        return catalogBean;
    }

    /**
     * 从指定节点开始,递归遍历所有子节点
     * @author chenleixing
     */
    public static CatalogBean getNodes(Element node){
        CatalogBean catalogBean=new CatalogBean();
        List<String> catalogLong=new ArrayList<String>();
        Map<String,List<String []>> map=new HashMap<String,List<String []>>();
        Map<String,Integer> catalogNumMap=new HashMap<>();
        Iterator iter = node.elementIterator("type"); // 获取根节点下的子节点head
        while (iter.hasNext()) {
            List<String []> content=new ArrayList<String []>();
            Element recordEle = (Element) iter.next();
            String catalogType= recordEle.attribute("editor").getValue();
            String catalogNum= recordEle.attribute("editorId").getValue();
            if(catalogType.length()>8){
                catalogType=catalogType.substring(0,8);
            }else if(catalogType.length()<8){
                int i=8-catalogType.length();
                for(int j=0;j<i;j++){
                    catalogType+="  ";
                }
            }
            catalogLong.add(catalogType);
			catalogNumMap.put(catalogType,Integer.parseInt(catalogNum));
            Iterator iters = recordEle.elementIterator("typeVale"); // 获取子节点head下的子节点script
            while (iters.hasNext()) {
                Element recordEle2 = (Element) iters.next();
                String [] contentValue=new String [2];
                String catalogContent="  "+recordEle2.getTextTrim();
                String editorId=recordEle2.attribute("editorId").getValue();
                if(catalogContent.length()>10){
                    catalogContent=catalogContent.substring(0,10);
                }else if(catalogContent.length()<10){
                    int i=6-catalogContent.length();
                    for(int j=0;j<i;j++){
                        catalogContent+=" ";
                    }
                }

                contentValue[0]=editorId;
                contentValue[1]=catalogContent;
                content.add(contentValue);

            }
            map.put(catalogType, content);
        }

        catalogBean.setCatalogType(catalogLong);
        catalogBean.setMap(map);
        catalogBean.setCatalogNumMap(catalogNumMap);
        return catalogBean;
    }

    public static String createNodes(String catalogId, List<CatalogInfoBean> catalogList){
    	if(catalogList.size()<=0){
    		return "查询目录为空";
    	}
    
    	try {
			SAXReader sax = new SAXReader();//创建一个SAXReader对象
			File xmlFile = new File(MyPhotoConstantsUI.CALALOG_XML_PATH);//根据指定的路径创建file对象
			Document document = sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
			Element root=document.getRootElement();//获取根节点
			Iterator iter = root.elementIterator("type"); // 获取根节点下的子节点head


//			OutputFormat format = OutputFormat.createPrettyPrint();  
//			format.setEncoding("UTF-8");  
			OutputFormat format = OutputFormat.createPrettyPrint();  
	        format.setEncoding("UTF-8");//设置编码格式 
			if("0".equals(catalogId)){    //父节点的情况
				List<CatalogInfoBean>  interatorToListForFather=AnalysisCatalogXml.interatorToListForFather(iter);
				for(CatalogInfoBean catalogBean:catalogList){
					 String classid=catalogBean.getClassid();
					 String classname=catalogBean.getClassname();
					 String bclassid=catalogBean.getBclassid();
					 boolean isOrNotCatalog=false; //是否存在现有的目录
					 if(interatorToListForFather.size()>=0){
						 for(CatalogInfoBean bean:interatorToListForFather){
							 String editorId=bean.getClassid();
							 if(classid.equals(editorId)){
					        	 isOrNotCatalog=true;
					        	 break;
					         }
						 }
					 }
					 
					 if(!isOrNotCatalog){
						 writeNote(root,classname,classid, "type"); //添加子节点
					 }
				}
				//添加  
//				XMLWriter writer = new XMLWriter(  
//					        new OutputStreamWriter(new FileOutputStream(MyPhotoConstantsUI.CALALOG_XML_PATH)), format); 
				 XMLWriter writer = new XMLWriter(  new OutputStreamWriter(new FileOutputStream(MyPhotoConstantsUI.CALALOG_XML_PATH),"UTF-8") ,format);
				writer.write(document);
			    writer.flush();  
				writer.close();

			}else{  //子节点
				while (iter.hasNext()) {
					 Element recordEle = (Element) iter.next();
					 String catalogTypeName= recordEle.attribute("editor").getValue();
			         String editorId=recordEle.attribute("editorId").getValue();
			         String editorAll=recordEle.attribute("editorAll").getValue(); //目录的全部名字
				         if(catalogId.equals(editorId)){
				        	 Iterator iters = recordEle.elementIterator("typeVale"); // 获取子节点head下的子节点script
				        	 //为避免出现重复数据，将Iterator转成list来判断
				        	 List<CatalogInfoBean>  interatorToListForSon=AnalysisCatalogXml.interatorToListForSon(iters);
							 boolean isNUll=iters.hasNext();
							 
				        	 for(CatalogInfoBean catalogBean:catalogList){
				   				 String classid=catalogBean.getClassid();
				   				 String classname=catalogBean.getClassname();
				   				 String bclassid=catalogBean.getBclassid(); 
				   				 boolean isOrNotCatalog=false; //是否存在现有的目录
				   				 if(interatorToListForSon.size()<=0){
				   					 writeNote(recordEle,classname,classid, "typeVale"); //添加子节点	    			
				   				 }else{
				   					 for(CatalogInfoBean catalog:interatorToListForSon){
				   						 String sonEditorAll=catalog.getClassname();
					   					 if(classname.equals(sonEditorAll)){
					                		 isOrNotCatalog=true;
					                		 break;
						                  }
				   					 }
				   					 if(!isOrNotCatalog){
				   						 writeNote(recordEle,classname,classid, "typeVale"); //添加子节点	   
				   					 }
				   				 }
				        	 }
				        	//添加  
//				        	 XMLWriter	 writer = new XMLWriter(  
//								        new OutputStreamWriter(new FileOutputStream(MyPhotoConstantsUI.CALALOG_XML_PATH)), format); 
				        	 XMLWriter writer = new XMLWriter(  new OutputStreamWriter(new FileOutputStream(MyPhotoConstantsUI.CALALOG_XML_PATH),"UTF-8") ,format);
				        	 writer.write(document);
				   			writer.flush();  
				   			writer.close();
				         }
				     }
					}
			return "0";
		} catch (Exception e) {
			return "查询目录异常"+e.getMessage();
		} 
    }
  
    private static void writeNote(Element element,String classname,String classid,String typeName){
    	// 创建文档并设置文档的根元素节点   
		Element type=element.addElement(typeName);
		   //根节点  
		   type.addAttribute("editor",classname); 
		  // if("type".equals(typeName)){
			   type.addAttribute("editorId",classid); 
		  // }
		   type.addAttribute("editorAll",classname);
		   if("typeVale".equals(typeName)){
			   type.setText(classname);
		   }
    }
    
    //子目录
    private static List<CatalogInfoBean>  interatorToListForSon( Iterator iters){
    	List<CatalogInfoBean> catalogList=new ArrayList<CatalogInfoBean>();
			while (iters.hasNext()) {  
				 Element recordEle2 = (Element) iters.next();
			     String catalogContent=recordEle2.getTextTrim();
			     String sonEditorAll=recordEle2.attribute("editorAll").getValue();
			     String editor=recordEle2.attribute("editor").getValue();
			     CatalogInfoBean bean =new CatalogInfoBean();
			     bean.setClassname(sonEditorAll);
			     catalogList.add(bean);
			 }
    	 return catalogList;

    }
    
    //父目录
    private static List<CatalogInfoBean>  interatorToListForFather( Iterator iters){
    	List<CatalogInfoBean> catalogList=new ArrayList<CatalogInfoBean>();
			while (iters.hasNext()) {  
				 Element recordEle = (Element) iters.next();
			     String catalogTypeName= recordEle.attribute("editor").getValue();
			     String editorId=recordEle.attribute("editorId").getValue();
			     String editorAll=recordEle.attribute("editorAll").getValue(); //目录的全部名字
			     
			     CatalogInfoBean bean =new CatalogInfoBean();
			     bean.setClassname(editorAll);
			     bean.setClassid(editorId);
			     bean.setBclassid("0");
			     catalogList.add(bean);
			 }
    	 return catalogList;

    }
   
}
