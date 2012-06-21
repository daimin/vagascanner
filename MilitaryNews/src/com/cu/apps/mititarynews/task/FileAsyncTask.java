package com.cu.apps.mititarynews.task;

import java.io.File;
import java.io.IOException;
import com.cu.apps.db.NewsDbDeal;
import com.cu.apps.utils.FileUtils;
import com.cu.apps.utils.LogUtils;
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
			// ��������������򵥴���(�������û�������ʾһ��������)
		}
		
		@Override
		protected String doInBackground(String... params) {
			// �÷��������ں�̨�߳��С����ｫ��Ҫ����ִ����Щ�ܺ�ʱ�ĺ�̨���㹤��
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
