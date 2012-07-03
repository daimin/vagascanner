package cn.hyena.apps.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.util.Log;
import cn.hyena.apps.utils.BitmapFactoryUtil;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.LogUtils;

public class HttpConnection {
	private static final String TAG = "HttpConnection";
	public static HttpParams httpParameters;
	public static final Integer DEFAULT_GET_REQUEST_TIMEOUT = 20000;
	public static final Integer DEFAULT_POST_REQUEST_TIMEOUT = 20000;
	public static final int HTTP_TIMEOUT = 20000;
	private static final int MAX_BYTE = 10240;

	// 初始化
	public static void InitNetWork() {
		httpParameters = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);// 不检测状态
		HttpConnectionParams.setConnectionTimeout(httpParameters, 20000); // 连接超时
		HttpConnectionParams.setSoTimeout(httpParameters, 120000); // 读取超时120秒
		HttpConnectionParams.setSocketBufferSize(httpParameters, 8192);
	}

	// 获取网络数据
	public static String GetHttpData(String urls) {
		HttpClient httpclient = null;
		HttpResponse httpResponse = null;
		try {
			httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httpRequest;
			if ("1".equals(Common.apnType)) {
				Matcher mt = Common.getMatcher(urls, "http://.*?/");
				String temStr = "";
				while (mt.find()) {
					temStr = mt.group().replace("http://", "").replace("/", "");
				}
				urls = urls.replace(temStr, "10.0.0.172");
				httpRequest = new HttpPost(urls);
				httpRequest.addHeader("X-Online-Host", temStr);
			} else {
				httpRequest = new HttpPost(urls);
			}

			httpRequest.addHeader("Accept-Encoding", "gzip");
			httpResponse = httpclient.execute(httpRequest);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
					&& entity != null) {
				InputStream in = entity.getContent();
				Header contentEncoding = httpResponse
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) // gzip压缩
				{

					in = new GZIPInputStream(in);

				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int ch = 0;
				byte[] resp = new byte[128];
				while ((ch = in.read(resp)) != -1) {
					bos.write(resp, 0, ch);
				}
				bos.flush();
				String str = new String(bos.toByteArray(), "utf-8");
				LogUtils.i(TAG, "urls:" + urls + "len:" + str.length()
						+ " str:" + str + " GetHttpData()");
				return str;
			}
			if (entity != null) {
				entity.consumeContent();// 释放连接回连接池，后续可继续使用该连接
			}
		} catch (SocketException e) {
			Log.e(TAG, "SocketException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown(); // 关闭当前连接器，释放资源
				httpResponse = null;
				httpclient = null;
			}
		}
		// }
		return "";
	}

	// 获取网络数据
	public static String GetHttpData(String urls,int num) {
		HttpClient httpclient = null;
		HttpResponse httpResponse = null;
		try {
			httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httpRequest;
			if ("1".equals(Common.apnType)) {
				Matcher mt = Common.getMatcher(urls, "http://.*?/");
				String temStr = "";
				while (mt.find()) {
					temStr = mt.group().replace("http://", "").replace("/", "");
				}
				urls = urls.replace(temStr, "10.0.0.172");
				httpRequest = new HttpPost(urls);
				httpRequest.addHeader("X-Online-Host", temStr);
			} else {
				httpRequest = new HttpPost(urls);
			}

			httpRequest.addHeader("Accept-Encoding", "gzip");
			httpResponse = httpclient.execute(httpRequest);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
					&& entity != null) {
				InputStream in = entity.getContent();
				Header contentEncoding = httpResponse
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) // gzip压缩
				{

					in = new GZIPInputStream(in);

				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int ch = 0;
				byte[] resp = new byte[128];
				while ((ch = in.read(resp)) != -1) {
					bos.write(resp, 0, ch);
				}
				bos.flush();
				String str = new String(bos.toByteArray(), "utf-8");
				LogUtils.i(TAG, "urls:" + urls + "len:" + str.length()
						+ " str:" + str + " GetHttpData()");
				return str;
			}
			if (entity != null) {
				entity.consumeContent();// 释放连接回连接池，后续可继续使用该连接
			}
		} catch (SocketException e) {
			Log.e(TAG, "SocketException");
			if (num < 5 && Common.NETTYPE != 0) {
				return GetHttpData(urls, num++);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown(); // 关闭当前连接器，释放资源
				httpResponse = null;
				httpclient = null;
			}
		}
		// }
		return "";
	}
	
	 public static Bitmap getImage (String imageUrl){   
		 HttpURLConnection conn;
         Bitmap bm = null;
         try {
		        	
		        	if ("1".equals(Common.apnType)){
		        		Matcher mt= Common.getMatcher(imageUrl,"http://.*?/");
		        		String temStr = ""; 
		        	    while(mt.find()){
		        	    	temStr = mt.group().replace("http://","").replace("/", "");
		        	    }
		        	    imageUrl = imageUrl.replace(temStr, "10.0.0.172");
		    		    URL url = new URL(imageUrl);  
		    		    conn = (HttpURLConnection) url.openConnection();  
		  	            conn.setRequestProperty("X-Online-Host", temStr);
		        	}else{
		        		URL url = new URL(imageUrl); 
		        		conn = (HttpURLConnection) url.openConnection();
		        	}
		        	conn.setDoInput(true); 
					conn.setRequestMethod("GET");   
		            conn.setConnectTimeout(2 * 1000); 
		            conn.connect();
		            if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();
     			        bm = BitmapFactoryUtil.decodeStream(is);
     			        Log.i(TAG, "图片获取成功");
     		            return bm;
	                }else
	                	Log.i(TAG, "图片获取失败");
		            conn.disconnect();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					LogUtils.e(TAG, "Commond  getNews:"+e);
				} catch(OutOfMemoryError e){
					if(bm != null && !bm.isRecycled()){
		               bm.recycle();
		               bm = null;
		            }
					e.printStackTrace();
					System.gc();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					LogUtils.e(TAG, "Commond  getNews:"+e);
				}
         return null;
      }
	
	public static byte[] getImageResource(HttpGet getMethod) throws IllegalStateException, IOException ,NetWorkException{
		HttpResponse httpResponse = null;
		byte buffer[] = null;

		try {
			HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), HTTP_TIMEOUT);
			HttpClient client = new DefaultHttpClient(new BasicHttpParams());
			client.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, DEFAULT_POST_REQUEST_TIMEOUT);
			client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, DEFAULT_POST_REQUEST_TIMEOUT);
			httpResponse = client.execute(getMethod);
			buffer = ImageInputStream(httpResponse.getEntity());
			
		} catch (SocketTimeoutException e) {
			throw e;
		} finally {
			getMethod.abort();
		}
		return buffer;
	}
	
	protected static byte[] ImageInputStream(HttpEntity httpEntity) {
		byte imgbyte[] = null;
		try {
			InputStream in = httpEntity.getContent();
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			byte buffer[] = new byte[MAX_BYTE];
			int count;
			while ((count = in.read(buffer)) != -1) {
				bytestream.write(buffer, 0, count);
			}
			imgbyte = bytestream.toByteArray();
			bytestream.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return imgbyte;
	}

}
