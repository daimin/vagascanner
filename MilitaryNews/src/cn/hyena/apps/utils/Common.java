package cn.hyena.apps.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hyena.apps.mititarynews.R;
import cn.hyena.apps.net.HttpConnection;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

public class Common {
	public static String apnType ="0";//手机联网方式0:非cmwap 1:cmwap
	public static int screenwidth = 320;
	public static int NETTYPE=1;    //联网状态    0无网络  1为gprs 2为wifi
	public static int[] wordSize = {20,16,12};//新闻内容字体大小
	public static long cacheSize =0; 	//缓存大小
	
	private static final String TAG = "Common";
	
	static{
		
	}
	
	// 判断字符串是否为空
	public static boolean isNullOrBlank(String param) {
		return param == null  || "".equals(param);
	}
	
	//获取SharedPreferences中保存数据
    public static String getCacheStr(Context context,String strKey){
		if( context!=null && !"".equals(strKey)){
			SharedPreferences SharedCache = context.getSharedPreferences("CACHE",Activity.MODE_PRIVATE);
			return SharedCache.getString(strKey, "");
		}else{
			return "";
		}
	}
    public static int getCacheInt(Context context,String strKey){
    	if( context!=null && !"".equals(strKey)){
			SharedPreferences SharedCache = context.getSharedPreferences("CACHE",Activity.MODE_PRIVATE);
			return SharedCache.getInt(strKey, 0);
		}else{
			return 0;
		}
	}
    
	//将数据写入SharedPreferences
	public static boolean setCacheStr(Context context,String strKey,String strValue){
		boolean isOK=false;
		if( context!=null && !"".equals(strKey)){
			SharedPreferences SharedCache = context.getSharedPreferences("CACHE",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor=SharedCache.edit();
			editor.putString(strKey, strValue);
			editor.commit();//提交
			isOK=true;
		}
		return isOK;
	}
	public static boolean setCacheInt(Context context,String strKey,int intValue){
		boolean isOK=false;
		if( context!=null && !"".equals(strKey)){
			SharedPreferences SharedCache = context.getSharedPreferences("CACHE",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor=SharedCache.edit();
			editor.putInt(strKey, intValue);
			editor.commit();//提交
			isOK=true;
		}
		return isOK;
	}
	
	/**
	 * 获取图片
	 * @param imageName
	 * @return
	 */
	public static Bitmap getResource(String imageName,String imageType)
	{
		Bitmap bmp = null;
		if(imageName == null || imageName.equals(""))
			return bmp;
		boolean isHas = FileUtils.getInstance().isSDCard();
		String name = Integer.toString(imageName.hashCode());
		if (imageName != null && !"".equals(imageName)) {// 图片名称不为空
			if(bmp==null){
				if(isHas)
				{
					try {
						bmp = ImageUtils.getImage(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (bmp == null) {
			try {
				bmp = HttpConnection.getImage(imageName);//ImageUtil.btye2Bitmap(HttpDataService.getResource(imageName);//现网读取
				 
					if (imageName != null && !"".equals(imageName)) {// 图片名称不为空
						if(isHas)
						{
							if(bmp != null)
							{
								ImageUtils.saveImage(name, bmp,imageType);
							}
							 
						}
					}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return bmp;
	}
	
	public static Matcher getMatcher(String str1,String str2){
		    Pattern pt=Pattern.compile(str2);
		    Matcher mt=pt.matcher(str1);
		    return mt;
	}
	 
    //窗口宽度
    public static int GetW(Context context){
		int Sw=0;
		DisplayMetrics dm=new DisplayMetrics();
		WindowManager wMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wMgr.getDefaultDisplay().getMetrics(dm);
		Sw = dm.widthPixels;
		return Sw;
	}
    
	//窗口高度
	public static int GetH(Context context){
		int Sh=0;
		DisplayMetrics dm=new DisplayMetrics();
		WindowManager wMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wMgr.getDefaultDisplay().getMetrics(dm);
		Sh = dm.heightPixels;
		return Sh;
	}
	
	public static int NetWorkGet(final Context context) {

		int st = 0;
		
		ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		//wifi 是否连上AP
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		//是否开启gprs 
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		
		if(internet){
			st=1;
		}else if(wifi){
			st=2;
		}
		
		LogUtils.i("netType","NetWorkGet:"+st);
		NETTYPE = st;
		return st;
	}
	
	// 获取Mobile网络下的cmwap
	public static void getCurrentApnInUse(Context context) {
		Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");
		Cursor cursor = context.getContentResolver().query(PREFERRED_APN_URI,
				new String[] { "_id", "apn", "type" }, null, null, null);
		cursor.moveToFirst();
		int counts = cursor.getCount();
		if (counts != 0) {
			if (!cursor.isAfterLast()) {
				String apn = cursor.getString(1);
				if (apn.equalsIgnoreCase("cmwap")
						|| apn.equalsIgnoreCase("3gwap")
						|| apn.equalsIgnoreCase("uniwap")) {
					WebView.enablePlatformNotifications();
					apnType = "1";
					ConnectivityManager con = (ConnectivityManager) context
							.getSystemService(Activity.CONNECTIVITY_SERVICE);
					boolean wifi = con.getNetworkInfo(
							ConnectivityManager.TYPE_WIFI)
							.isConnectedOrConnecting();
					if (wifi) {
						apnType = "0";
					}
				} else {
					apnType = "0";
				}
			}
		}
		cursor.close();
	}

	// 获取软件版本号
	public static String VersonGet(final Context context) {
		PackageInfo info;
		String VersionID;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (info == null) {
				VersionID = "unknown";
			} else {
				VersionID = info.versionName; // 03.00.10
			}
		} catch (Exception e) {
			VersionID = "unknown";
			LogUtils.e(TAG, "Commond  ex:" + e);
		}
		return VersionID;
	}

	// 获取应用程序信息 (缓存大小)
	public static void Getpkginfo(Context context, String pkg, Handler handler) {
		Message message = Message.obtain();
		message.what = 1;
		try {
			String dir = Environment.getExternalStorageDirectory()
					+ "/MilitarNews/cache/";
			File dirFile = new File(dir);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			cacheSize = FileUtils.calculateDirectorySize(dirFile);
			message.obj = FileUtils.formatFileSize(cacheSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			handler.sendMessage(message);
		}
	}
			
	// 提示
	public static void ShowInfo(Activity paramActivity, String showmsg) {
		ShowInfo(paramActivity, showmsg, "0");
	}

	public static void ShowInfo(Activity paramActivity, String showmsg,
			String type) {
		if (type.equals("1")) {
			Toast.makeText(paramActivity, showmsg, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(paramActivity, showmsg, Toast.LENGTH_SHORT).show();
		}
	}
			
	public static String toHTMl(String str) {
		return str.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">").replaceAll("&nbsp;", "  ");
	}

	public static String convSpecialChar(String str) {
		return str.replaceAll("&quot;", "\"");
	}

	public static String trimLineBreakChar(String str) {

		return str.replace("\n\r", "").replace("\n", "").replace("\r", "");
	}
         
	// 翻页动画效果
	private static Method overridePendingTransition;
	static {
		try {
			overridePendingTransition = Activity.class
					.getMethod(
							"overridePendingTransition", new Class[] { Integer.TYPE, Integer.TYPE }); //$NON-NLS-1$
		} catch (NoSuchMethodException e) {
			overridePendingTransition = null;
		}
	}

	public static void overridePendingTransition(Context mContext,
			int animEnter, int animExit) {
		if (overridePendingTransition != null) {
			try {
				overridePendingTransition.invoke(mContext, animEnter, animExit);
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public static void appExit(final Activity activity) {
		// TODO Auto-generated method stub
		Builder b = new AlertDialog.Builder(activity).setTitle(
				R.string.exit_pro).setMessage(R.string.exit_pro_sure);
		b.setPositiveButton(R.string.exit_pro_yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						activity.finish();
					}
				})
				.setNeutralButton(R.string.exit_pro_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						}).show();
	}
}
