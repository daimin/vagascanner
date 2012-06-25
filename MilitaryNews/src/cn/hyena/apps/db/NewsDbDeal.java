package cn.hyena.apps.db;

import cn.hyena.apps.utils.LogUtils;

import android.content.Context;
import android.database.Cursor;

//只要用于离线下载的数据缓存
public class NewsDbDeal {
	private NewsDbAdapter mDbHelper;

	private static final String TAG = "NewsDbDeal";

	
	public NewsDbDeal(Context mContext) {
		this.mDbHelper = new NewsDbAdapter(mContext);
		this.mDbHelper.open();
		LogUtils.i(TAG, "NewsDbDeal()");
	}

	public String select(String keys) {
		String con="";
		
		Cursor note = this.mDbHelper.get(keys);
		if(note.getCount()>0){
			con= note.getString(note.getColumnIndexOrThrow(NewsDbAdapter.KEY_CON));
		}
		note.close();
		
		LogUtils.i(TAG, "select()");
		return con;
	}
	
	public boolean exist(String keys) {
		boolean bo=false;
		
		Cursor note = this.mDbHelper.get(keys);
		if(note.getCount()>0){
			bo= true;
		}
		note.close();
		
		LogUtils.i(TAG, "exist()");
		return bo;
	}

	public boolean update(String keys, String contents) {
		LogUtils.i(TAG, "update()");
		return this.mDbHelper.update(keys,contents);
	}
	
	public long insert(String keys,String Contents) {
		LogUtils.i(TAG, "insert()");
		return this.mDbHelper.insert(keys,Contents);
	}

	public boolean delete(long rowId) {
		LogUtils.i(TAG, "delete()");
		return this.mDbHelper.delete(rowId);
	}
	public boolean delete(String names) {
		LogUtils.i(TAG, "delete()");
		return this.mDbHelper.delete(names);
	}
	
	
	public boolean truncate() {
		LogUtils.i(TAG, "truncate()");
		boolean bo=false;
		Cursor note = this.mDbHelper.getall();
		LogUtils.i(TAG, "truncate()");
		if(note.getCount()>0){
			bo= this.mDbHelper.truncate();
		}
		note.close();
		return bo;
	}
	
	
	public void dbclose() {
		LogUtils.i(TAG, "close()");
		this.mDbHelper.close();
	}
}
