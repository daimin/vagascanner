package com.cu.apps.net;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.cu.apps.mititarynews.analysis.NewsContentAnalysis;
import com.cu.apps.mititarynews.analysis.NewsItemAnalysis;
import com.cu.apps.utils.Common;
import com.cu.apps.utils.ImageUtils;
/**
 * 通讯协议接口拼装
 *
 */
public class HttpDataService {
	 
//	public static String HOST_URL = "http://192.168.0.111/hyena_ft/";
//	public static String HOST_URL = "http://192.168.211.129/hyena_ft/";
	public static String HOST_URL = "http://vagasnail.13.tiantanggou.com/hyena_ft/";
	 
	public static void getNewsContent(String id,Handler handler,int what) throws ClientProtocolException, IOException,NetWorkException {
		Message message = Message.obtain();
		message.what = what;
		
		StringBuffer sb = new StringBuffer();
		sb.append(HOST_URL);
		sb.append("get_content.php?");
		sb.append("id=" + id);
		
        try {
			 String jsonString = null;
		     jsonString  = Common.toHTMl(HttpConnection.GetHttpData(sb.toString()));
		     NewsContentAnalysis newsItemAnalysis = new NewsContentAnalysis();
		     newsItemAnalysis.parser(jsonString);//解析数据
			 message.obj = newsItemAnalysis.newsBean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			handler.sendMessage(message);
		}
	}

    //获取主界面新闻列表
	public static void getNewsList(String n,Handler handler,int what) throws ClientProtocolException, IOException,NetWorkException,Exception {
		Message message = Message.obtain();
		message.what = what;
		
		StringBuffer sb = new StringBuffer();
		sb.append(HOST_URL);
		sb.append("get_title.php?");
		//sb.append("&n=" + n);
		
        try {
			 String jsonString = null;
		     jsonString  =  Common.toHTMl(HttpConnection.GetHttpData(sb.toString()));
		     NewsItemAnalysis newsItemAnalysis = new NewsItemAnalysis();
		     newsItemAnalysis.parser(jsonString);//解析数据
			 message.obj = newsItemAnalysis.newsBeanArr;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			handler.sendMessage(message);
		}
        
	}
	
	/**
	 * 获取图片
	 * @param imgurl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetWorkException
	 */
	public static Bitmap getResource(String imgurl) throws ClientProtocolException,IOException, NetWorkException {
		HttpGet getMethod = new HttpGet(imgurl);
		return ImageUtils.btye2Bitmap(HttpConnection.getImageResource(getMethod));
	}
}
