package cn.hyena.apps.mititarynews.task;

import java.io.File;
import java.io.IOException;
import cn.hyena.apps.db.NewsDbDeal;
import cn.hyena.apps.utils.FileUtils;
import cn.hyena.apps.utils.LogUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

@SuppressWarnings("unused")
public class FileAsyncTask extends AsyncTask<String, Integer, String> {
	    private static final String TAG = "FileAsyncTask";
	    private Context mContext;
	    
	    public FileAsyncTask(Context mContext){
	    	this.mContext = mContext;
	    }
	    
		@Override
		protected void onPreExecute() {
			// 任务启动，这里简单处理(可以在用户界面显示一个进度条)
		}
		
		@Override
		protected String doInBackground(String... params) {
			// 该方法运行在后台线程中。这里将主要负责执行那些很耗时的后台计算工作
			try {
                if("ClearSdCardData".equals(params[0])){
					NewsDbDeal newsDbDeal = new NewsDbDeal(mContext);
					newsDbDeal.truncate();
					newsDbDeal.dbclose();
					  
					File dirFile = new File(params[1]);
			    	if(dirFile!= null && dirFile.isDirectory()){ 
			    		try{
			    			FileUtils.clearSdCardData(dirFile);	        	
			    		}catch(Exception e)  { 
			    			LogUtils.e(TAG, "ClearFile -- sdcard failed" + e.toString());              
			    		}          
			    	}						
					return "";
				}
			} catch (Exception err) {
				LogUtils.e(TAG, "doInBackground() error:" + err.toString());
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

		}

		@Override
		protected void onPostExecute(String str) {
			try {
				if (isCancelled()) {
					return;
				}
				
			} catch (Exception err) {
				LogUtils.e(TAG, "onPostExecute() error:" + err.toString());
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

}
