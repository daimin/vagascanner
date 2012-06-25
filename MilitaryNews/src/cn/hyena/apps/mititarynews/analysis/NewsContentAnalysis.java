package cn.hyena.apps.mititarynews.analysis;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import cn.hyena.apps.mititarynews.bean.NewsBean;
import cn.hyena.apps.utils.LogUtils;
import cn.hyena.apps.utils.Utils;
import android.content.res.Resources.NotFoundException;

/**
 * 新闻资讯内容解析
 */
public class NewsContentAnalysis{
	
	public NewsBean  newsBean = new NewsBean() ;
	 
	 public  void parser(String str)
	 {
		
		 try
		 {
			 JSONObject jsonObject = new JSONObject(str); 
			 
			 if(jsonObject.optString("title") != null)
			 {
				 newsBean.title = jsonObject.optString("title");//资讯标题
			 }	  
			 if(jsonObject.optString("content") != null)//资讯内容
			 {
				 //String result = Utils.StringFilter(jsonObject.optString("c").replaceAll("<br/>", "\n"));//鏇挎崲鎺夋崲琛岀,绌烘牸锟?
				 String result = Utils.StringFilter(jsonObject.optString("content").replace("&quot;", "\"").replace("border=1", ""));
				 //Log.e("result",result);
				 if(result != null && !"".equals(result))
				 {
					 if(result.indexOf("img") > 0)
					 {
						 String html = Utils.getHtml(result);//抽取html标签，
						 String xml = Utils.toXML(html);//组装成标准的xml再解析
						 xml = xml.replaceAll("src=http", "\" src=\"http");
						 ImageAnalysis imageAnalysis = new ImageAnalysis();
						 parser(imageAnalysis,xml);
						 
						 LogUtils.e("XML ",xml);
						 result = Utils.html2Tag(result);
					 }
				 }
				 newsBean.content = result;
				 //Log.e("newsBean.content ",newsBean.content );
			 }	 
			 
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	 }
	 
	 
	 public static synchronized void parser(DefaultHandler defaulthandler, String xml) {
			StringReader reader = new StringReader(xml);
			InputSource source = new InputSource(reader);
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				xr.setContentHandler(defaulthandler);
				xr.parse(source);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	  class ImageAnalysis extends DefaultHandler {	
		  public NewsBean.ImgageItem imgageItem = null;
		  private StringBuffer buf = new StringBuffer();
		  private int indexI = 0;
			@Override
			public void startDocument() throws SAXException {
				
			}

			@Override
			public void endDocument() throws SAXException {
			}

			@Override
			public void startElement(String namespaceURI, String localName,
					String qName, Attributes atts) throws SAXException {
				 
				    //Log.e("startElement","localName"+localName);
					if(localName.equalsIgnoreCase("img"))
					{
						imgageItem = new NewsBean.ImgageItem();
						imgageItem.id = indexI;
						imgageItem.url = atts.getValue("src");
						imgageItem.des = atts.getValue("alt");
						newsBean.imgageItems.add(imgageItem);
						indexI++;
					}
			}
			@Override
			public void endElement(String namespaceURI, String localName, String qName)
					throws SAXException {
				buf.setLength(0);
			}
			@Override
			public void characters(char ch[], int start, int length) {
				buf.append(ch, start, length);
			}
		}
}
