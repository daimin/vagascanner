package com.cu.apps.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;

import com.cu.apps.utils.LogUtils;

//只要用于离线下载的数据缓存
public class NewsDbAdapter {
	private static final String DATABASE_NAME = "militaryNews.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_TABLE = "newstb";
	private static final String DATABASE_CREATE = "CREATE TABLE newstb(_id INTEGER PRIMARY KEY,keys TEXT NOT NULL,contents TEXT,createtime INTEGER);";
	private static final String DATABASE_INDEX = "CREATE index key_idx on newstb (keys);";
	
	private Context mCtx = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;	

	public static final String KEY_ROWID = "_id";
	public static final String KEY_KEY = "keys";
	public static final String KEY_CON = "contents";
	public static final String KEY_CREATETIME = "createtime";
	
	String[] strCols = { KEY_ROWID, KEY_KEY,KEY_CON, KEY_CREATETIME };
	
	private static final String TAG = "NewsDbAdapter";
	
	
	public NewsDbAdapter(Context mContext) {
		this.mCtx = mContext;
		LogUtils.i(TAG, "NewsDbAdapter()");
	}
	
	public NewsDbAdapter open() throws SQLException {
		this.dbHelper = new DatabaseHelper(this.mCtx);
		this.db = this.dbHelper.getWritableDatabase();
		LogUtils.i(TAG, "open()");
		return this;
	}
	
	public void close() {
		LogUtils.i(TAG, "close()");
		this.dbHelper.close();
	}
	
	
	
	//获取所有记录
	public Cursor getall() throws SQLException{
		LogUtils.i(TAG, "getall()");
		//return this.db.rawQuery("SELECT * FROM "+DATABASE_TABLE, null);
		return this.db.query(DATABASE_TABLE, strCols, null, null, null, null, null);
	}
	
	//查询多条记录
	public Cursor getSome(String keys) throws SQLException {
		Cursor mCursor = this.db.query(DATABASE_TABLE, strCols, KEY_KEY+"='"+ keys+"'",  null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToLast();
		}
		LogUtils.i(TAG, "getone()");
		return mCursor;
	}
	
	//查询一条记录
	public Cursor get(String keys) throws SQLException {
		Cursor mCursor = this.db.query(true, DATABASE_TABLE, strCols, KEY_KEY+"='"+ keys+"'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		LogUtils.i(TAG, "getone()");
		return mCursor;
	}
	
	//更新一条记录
	public boolean update(String keys, String Contents) {
		ContentValues args = new ContentValues();
		args.put(KEY_CON, Contents);

		LogUtils.i(TAG, "upone()");
		return this.db.update(DATABASE_TABLE, args,KEY_KEY+ "='" + keys+"'", null) > 0;
	}
	
	//插入成功返回新增记录的rowID,失败返回-1
	public long insert(String keys,String Contents) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);

		ContentValues args = new ContentValues();
		args.put(KEY_KEY, keys);
		args.put(KEY_CON, Contents);
		args.put(KEY_CREATETIME, dateFormat.format(date));

		LogUtils.i(TAG, "addone");
		return this.db.insert(DATABASE_TABLE, null, args);
	}
	
	//删除一条记录,失败返回0
	public boolean delete(String keys) {
		LogUtils.i(TAG, "deleteone");
		return this.db.delete(DATABASE_TABLE, KEY_KEY+"='" + keys+"'", null) > 0;
	}	
	public boolean delete(long rowID) {
		LogUtils.i(TAG, "deleteone");
		return this.db.delete(DATABASE_TABLE, KEY_ROWID+"=" + rowID, null) > 0;
	}
	
	//删除表数据,失败返回0
	public boolean truncate() {
		LogUtils.i(TAG, "truncate");
		return this.db.delete(DATABASE_TABLE, null, null) > 0;
	}	
	
	
	
	// 创建及管理数据表
	private static class DatabaseHelper extends SQLiteOpenHelper {
		// 改写DatabaseHelper的constructor
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

		//会自动创建目录databases及在目录下创建数据库文件notes.db
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			db.execSQL(DATABASE_INDEX);
			LogUtils.i(TAG, "创建表与索引");
		}
	}
}
