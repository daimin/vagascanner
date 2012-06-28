package cn.hyena.apps.net;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import cn.hyena.apps.mititarynews.analysis.NewsContentAnalysis;
import cn.hyena.apps.mititarynews.analysis.NewsItemAnalysis;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.ImageUtils;
/**
 * 通讯协锟斤拷涌锟狡醋?
 *
 */
public class HttpDataService {
	 
	public static String HOST_URL = "http://vagasnail.13.tiantanggou.com/hyena_ft/";
	 
	//鑾峰彇鏂伴椈鍐呭
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
             jsonString = Common.trimLineBreakChar(jsonString);
		     newsItemAnalysis.parser(jsonString);//锟斤拷锟斤拷锟斤拷锟?
			 message.obj = newsItemAnalysis.newsBean;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			handler.sendMessage(message);
		}
	}

    //鑾峰彇鏂伴椈鍒楄〃
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
		     newsItemAnalysis.parser(jsonString);//锟斤拷锟斤拷锟斤拷锟?
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
	 * 锟斤拷取图片
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
