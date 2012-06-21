package com.cu.apps.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import com.cu.apps.mititarynews.task.FileAsyncTask;

public class FileUtils {
	private static final String TAG = "FileUtils";
	private static FileAsyncTask task;
	private final static String DATABASE ="bookmark";
	//private final static String DATABASE2 ="navigation";
	
	private static FileUtils _fileUtil = new FileUtils();

	public static FileUtils getInstance() {
		return _fileUtil;
	}

	
	public static boolean save(Activity activity, String key,int values)
	{
		SharedPreferences sharedPreferences = activity.getSharedPreferences(DATABASE, Activity.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, values);	
		editor.commit();
		return true;
	}
	
	public static int get(Activity activity,String key)
	{
		SharedPreferences settings = activity.getSharedPreferences(DATABASE, Activity.MODE_WORLD_WRITEABLE);
		 int resultString = settings.getInt(key, 0);
		 return resultString;
	}
	
	public static void remove(Activity activity,String key)
	{
		SharedPreferences sharedPreferences = activity.getSharedPreferences(DATABASE, Activity.MODE_WORLD_WRITEABLE);
		sharedPreferences.edit().clear().commit();
	}
	
	public static boolean saveString(Context context, String key,String values)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE, Activity.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, values);	
		editor.commit();
		return true;
	}
	
	/**
	 * 鏄惁瀛樺湪
	 * 
	 * @param filepath
	 * @return
	 */
	public boolean isExist(String filepath) throws IOException {
		File file = new File(filepath);
		return file.isFile();
	}

	/**
	 * 鍒犻櫎缂撳瓨鏂囦欢鍙婄洰褰?
	 * 
	 * @param filepath
	 * @return
	 */
	public void deleteFile(String filePath) throws IOException {
		File file = new File(filePath);
		Log.d("aa", "deleteFile--------------");
		if (file.isDirectory()) {// 澶勭悊鐩綍
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i].getAbsolutePath());
			}
		}

		if (!file.isDirectory()) {// 濡傛灉鏄枃浠讹紝鍒犻櫎
			file.delete();
		} else {// 鐩綍
			if (file.listFiles().length == 0) {// 鐩綍涓嬫病鏈夋枃浠舵垨鑰呯洰褰曪紝鍒犻櫎
				file.delete();
			}
		}
	}
	
	/**
	 * 鍒犻櫎缂撳瓨鏂囦欢鍙婄洰褰?
	 * 
	 * @param filepath
	 * @return
	 */
	public void deleteUserCacheFile(String filePath) throws IOException {
		File file = new File(filePath);
		Log.d("aa", "deleteFile--------------");
		if (file.isDirectory()) {// 澶勭悊鐩綍
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().equals("User"))
					continue;
				deleteFile(files[i].getAbsolutePath());
			}
		}

		if (!file.isDirectory()) {// 濡傛灉鏄枃浠讹紝鍒犻櫎
			file.delete();
		} else {// 鐩綍
			if (file.listFiles().length == 0) {// 鐩綍涓嬫病鏈夋枃浠舵垨鑰呯洰褰曪紝鍒犻櫎
				file.delete();
			}
		}
	}
	

	/**
	 * 璇诲彇鏂囦欢鍐呭
	 * 
	 * @param filepath
	 * @return
	 */
	public String readFileByString(String filePath) throws IOException {

		if (!isExist(filePath)) {
			return null;
		}

		File file = new File(filePath);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer xml = new StringBuffer();
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			char[] b = new char[4096];
			for (int n; (n = isr.read(b)) != -1;) {
				xml.append(new String(b, 0, n));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xml.toString();
	}

	/**
	 * 璇诲彇鏂囦欢鍐呭
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public byte[] readFileByByte(String filePath) throws IOException {

		if (!isExist(filePath)) {
			return null;
		}

		File file = new File(filePath);
		int length = (int) file.length();
		byte content[] = new byte[length];
		FileInputStream fis = new FileInputStream(file);
		fis.read(content, 0, length);
		fis.close();

		return content;
	}

	/**
	 * 鍐呭鍐欏叆鏂囦欢
	 * 
	 * @param context
	 * @param filepath
	 * @param data
	 */
	public void writeFileByString(String filePath, String data) throws IOException {
	 
		FileOutputStream fOut = null;
		OutputStreamWriter osw = null;
		File file = new File(filePath);

		try {
			if (!file.isFile()) {
				String path = filePath.substring(0, filePath.lastIndexOf("/"));
				Log.i("info", "-------------------------"+path);
				File dirFile = new File(path);
				dirFile.mkdirs();
				file.createNewFile();
				/*if (dirFile.mkdirs()) {
					file.createNewFile();
				} else {
					return;
				}*/

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut = new FileOutputStream(file);
			osw = new OutputStreamWriter(fOut, "UTF-8");
			osw.write(data);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 鍐呭鍐欏叆鏂囦欢
	 * 
	 * @param context
	 * @param filepath
	 * @param data
	 */
	public void writeFileByByte(String filePath, byte content[]) throws IOException {
		FileOutputStream fOut = null;
		File file = new File(filePath);

		try {
			if (!file.isFile()) {
				String path = filePath.substring(0, filePath.lastIndexOf("/"));
				File dirFile = new File(path);
				dirFile.mkdirs();
				file.createNewFile();
				/*if (dirFile.mkdirs()) {
					file.createNewFile();
				} else {
					return;
				}*/

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut = new FileOutputStream(file);
			fOut.write(content, 0, content.length);
			fOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * 浠巗dcard涓鍙栨暟鎹?
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String readFileFromSdcard(String filePath) throws IOException {
	
		 if (!isExist(filePath)) {
				return null;
			}

			File file = new File(filePath);
			int length = (int) file.length();
			byte content[] = new byte[length];
			FileInputStream fis = new FileInputStream(file);
			fis.read(content, 0, length);
			fis.close();

			return new String(content,"UTF-8");
		}
	 /**
	 * 鏄惁鏈塖D鍗″瓨鍦?
	 * @return
	 */
	public  boolean isSDCard(){
		try {
			String status=Environment.getExternalStorageState();
			if(status.equals(Environment.MEDIA_MOUNTED))
			{
			   return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	/**
	 * 浠庡畨瑁呭寘涓鍙栨暟鎹?
	 * @param context
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String readFileFromLocal(Context context,String filePath) throws IOException {

		AssetManager assets = context.getAssets();         
		InputStream is=assets.open(filePath);
		
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer xml = new StringBuffer();
		try {
			isr = new InputStreamReader(is, "UTF-8");
			char[] b = new char[4096];
			for (int n; (n = isr.read(b)) != -1;) {
				xml.append(new String(b, 0, n));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xml.toString();
	}
		
 
	//清除缓存
	public static void clearAllCache(Context context){
	    	String dirSdCard=Environment.getExternalStorageDirectory()+"/MilitaryNews/image/";
			task = new FileAsyncTask(context);
			task.execute("ClearSdCardData",dirSdCard);
			
			String cacheSdCard=Environment.getExternalStorageDirectory()+"/MilitaryNews/cache/";
			task = new FileAsyncTask(context);
			task.execute("ClearSdCardData",cacheSdCard);
	 }	
	
     //删除SDCard目录
	 public static void clearSdCardData(File dir) {
	        if(dir!=null && dir.isDirectory()){
		        File[] files = dir.listFiles();
		        for (File file:files) {
		            if (file.isFile()) {
		                file.delete();
		                LogUtils.i(TAG,"ClearFile -- sdcard  filename:"+file.getPath());
		            }else if (file.isDirectory()) {
		                clearSdCardData(file); // 如果遇到目录则通过递归调用继续
		            }
		            
		   		 try {
						Thread.sleep(10);
					} catch (Exception e) {
						LogUtils.i(TAG,"Getpkginfo() while e:"+e);
					}
		        }	        	
	        }
	        
	        createDir();   //重新在sdcard创建目录
	  }
	 
	 //在sdcard创建临时文件目录
	    public static boolean createDir(){
	    	  boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
	    	  if (sdCardExist){
	    		  String dir=Environment.getExternalStorageDirectory()+"/MilitaryNews/";
	              File dirFile = new File(dir);
	              if(!dirFile.exists()){
	                  dirFile.mkdir();
	              }
	              dir=Environment.getExternalStorageDirectory()+"/MilitaryNews/image/";
	              dirFile = new File(dir);
	              if(!dirFile.exists()){
	                  dirFile.mkdir();
	              }
	              return true;
	    	  }
	    	  return false;
	    }		
	 
	//读取文件(文件不存在时会异常,所以要判断)
	    public static String fileRead(String path) {  //   path:/sdcard/3GNews/imei.txt
			  boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
			  if (sdCardExist){
		          File dirFile = new File(path);
		          if(!dirFile.exists()){
		              try {
						dirFile.createNewFile();  //如果没该文件，就创建
					} catch (IOException e) {
						LogUtils.e(TAG,"FileRead()  ex:"+e);
					}
		          }
		          
		    	BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
				} catch (FileNotFoundException e1) {
					LogUtils.e(TAG,"FileRead()  ex:"+e1);
				}
		    	String data = null;
		    	try {
					if(br !=null && (data = br.readLine())!=null)
					{
						return data;
					}
				} catch (IOException e) {
					LogUtils.e(TAG,"FileRead()  ex:"+e);
				}
		    	try {
					br.close();
				} catch (IOException e) {
					LogUtils.e(TAG,"FileRead()  ex:"+e);
				}
			  }
	    	
	    	return "";
		}
	    
	    //向文件写入数据(文件不存在时会新建)
	    public static boolean fileWrite(String path,String value) {  //   path:/sdcard/imei.txt
	    	boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
			  if (sdCardExist){
				  OutputStreamWriter osw = null;
					try {
						osw = new OutputStreamWriter(new FileOutputStream(path));
					} catch (FileNotFoundException e) {
						LogUtils.e(TAG,"FileRead()  ex:"+e);
					}
			    	try {
						osw.write(value,0,value.length());
					} catch (IOException e) {
						LogUtils.e(TAG,"FileRead()  ex:"+e);
					}
			    	try {
						osw.flush();
					} catch (IOException e) {
						LogUtils.e(TAG,"FileRead()  ex:"+e);
					}
			    	try {
						osw.close();
					} catch (IOException e) {
						LogUtils.e(TAG,"FileRead()  ex:"+e);
					}
					
					return true;
			  }
			  
	    	return false;
		}
	    
	    //获取目录大小
	     public static long calculateDirectorySize(File dir) {
	         long directorySize = 0;
	         if(dir!=null && dir.isDirectory()){
		         File[] files = dir.listFiles();
		         for (File file:files) {
		             if (file.isFile()) {
		                 directorySize += file.length();
		                 LogUtils.i(TAG,"directorySize:"+directorySize);
		             }else if (file.isDirectory()) {
		                 directorySize += file.length();
		                 directorySize += calculateDirectorySize(file); // 如果遇到目录则通过递归调用继续统计
		             }
		         }	        	
	         }
	         return directorySize;
	     }
		 //获取文件大小
		 public static String formatFileSize(long length) {
			String result = null;
			int sub_string = 0;
			if (length >= 1073741824) {
				sub_string = String.valueOf((float) length / 1073741824).indexOf(".");
				result = ((float) length / 1073741824 + "000").substring(0,sub_string + 3)+ "GB";
			} else if (length >= 1048576) {
				sub_string = String.valueOf((float) length / 1048576).indexOf(".");
				result = ((float) length / 1048576 + "000").substring(0,sub_string + 3)+ "MB";
			} else if (length >= 1024) {
				sub_string = String.valueOf((float) length / 1024).indexOf(".");
				result = ((float) length / 1024 + "000").substring(0,sub_string + 3)+ "KB";
			} else if (length < 1024)
				result = Long.toString(length) + "B";
			return result;
		 }
}
