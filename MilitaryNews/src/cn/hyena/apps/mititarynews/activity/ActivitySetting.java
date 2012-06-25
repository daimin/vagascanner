package cn.hyena.apps.mititarynews.activity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.hyena.apps.mititarynews.R;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.FileUtils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivitySetting extends PreferenceActivity {
	private String newspushSwitch = "on";//新闻推送开关
	private String newspicSwitch = "off";//是否下载新闻图片开关
	private String downloadSwitch = "2";//离线下载开关 2:wifi情况下打开
	private String wordSizeStr = "2";//新闻文字大小 默认2：中
	private String sinaToken = "";//新浪微博分享Token
	private String sinaSecret = "";//新浪微博分享Secret                 
	private String renrenToken = "";//人人网分享Token

	private CheckBoxPreference settingPush;//新闻推送开关按钮
	private Preference settingPic;//是否下载新闻图片开关按钮
	private Preference settingUpdate;//离线下载开关按钮
	private Preference settingClear;//清除缓存按钮
	private Preference settingFont;//文字大小选择
	private CheckBoxPreference settingSinaShare;//清除缓存按钮
	private CheckBoxPreference settingRenrenShare;//清除缓存按钮
	private Preference settingAbout;//关于项

	private String cacheSize;//当前缓存大小

	protected static final int MENU_FIRST = Menu.FIRST;
	protected static final int MENU_SECOND = Menu.FIRST + 1;
	private static final String TAG = "ActivitySetting";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置显示Preferences
		addPreferencesFromResource(R.layout.setting);
		// 获得SharedPreferences
		//pre = PreferenceManager.getDefaultSharedPreferences(this);

		settingPush = (CheckBoxPreference) findPreference(getResources().getString(R.string.setting_push_key));
		settingPic = findPreference(getResources().getString(R.string.setting_pic_key));
		settingUpdate = findPreference(getResources().getString(R.string.setting_update_key));
		settingClear = findPreference(getResources().getString(R.string.setting_clear_key));
		settingFont = findPreference(getResources().getString(R.string.setting_font_key));
		settingSinaShare = (CheckBoxPreference)findPreference(getResources().getString(R.string.setting_share_sina_key));
		settingRenrenShare = (CheckBoxPreference) findPreference(getResources().getString(R.string.setting_share_renren_key));
		settingAbout = findPreference(getResources().getString(R.string.setting_about_key));
		// 获取设置的中文数组
		/*arrPic = getResources().getStringArray(R.array.PicSettingArray);
		arrUpdate = getResources().getStringArray(R.array.OfflineSettingArray);
		arrFont = getResources().getStringArray(R.array.FontSizeSettingArray);*/
		
		initialize();
	}
	
	private void initialize(){
		//初始化新闻推送设置
		newspushSwitch = Common.getCacheStr(this,"NEWSPUSH1_Cached");
		if (newspushSwitch.equals("off")){
			settingPush.setChecked(false);
		}else{
			settingPush.setChecked(true);
		}
		//新闻推送开关启动/关闭事件
		settingPush.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				if ("true".equals(newValue.toString())){
					//Common.newspush(ActivitySetting.this,"on");
					//Common.newspush2(ActivitySetting.this,"on");
				}
					//Common.newspush(ActivitySetting.this,"off");
				   // Common.newspush2(ActivitySetting.this,"off");
				return true;
			}
         });
		
		/*//初始化新闻图片下载设置
		newspicSwitch = Common.getCacheStr(this,"NEWSPIC_Cached");
		if (newspicSwitch.equals("on")){
			settingPic.setChecked(true);
		}else{
			settingPic.setChecked(false);
		}
		//新闻图片下载开关启动/关闭事件
		settingPic.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
				Object newValue) {
				if ("true".equals(newValue.toString())){
					Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached","on");
				}else
					Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached","off");
				return true;
				}
		 });*/
		
		//初始化新闻图片下载设置
		newspicSwitch = Common.getCacheStr(this,"NEWSPIC_Cached");
		if (newspicSwitch.equals("on")){
			settingPic.setSummary("当前：仅WIFI时");
		}else{
			settingPic.setSummary("当前：任何网络");
		}
		settingPic.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					newspicSwitch = Common.getCacheStr(ActivitySetting.this,"NEWSPIC_Cached");
					initSelectList(1);
					return true;
				}
		 });
		 
		//初始化离线下载设置
		downloadSwitch  = Common.getCacheStr(this,"MEDIACLIENT_UPDATE");
		if (downloadSwitch.equals("3")){
			settingUpdate.setSummary("当前：关闭");
		}else{
			settingUpdate.setSummary("当前：WIFI时可用");
		}
		settingUpdate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					downloadSwitch  = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_UPDATE");
					initSelectList(2);
					return true;
				}
		 });
		 
		 //获取缓存大小，初始化界面
		 getCache();
		 //清除客户端缓存事件
		 settingClear.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					if (!"0B".equals(cacheSize)){
					   final ProgressDialog m_Dialog = ProgressDialog.show(ActivitySetting.this,null,"请稍候,正在清除缓存");
					   FileUtils.clearAllCache(ActivitySetting.this);
					   handler.postDelayed(new Runnable(){
							public void run() {
									if (m_Dialog != null) {
									    m_Dialog.dismiss();
										m_Dialog.cancel();
									}
									//getCache();
									cacheSize = "0B";
									settingClear.setSummary("清除缓存中临时文件，当前缓存:" + cacheSize);
									Common.ShowInfo(ActivitySetting.this,"清除缓存成功");
							}
					   }, 5000);
					}else
						Common.ShowInfo(ActivitySetting.this,"无缓存,无须清除操作！");
					return true;
				}
		 });
     
		 //文字大小设置初始化
		 wordSizeStr = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE");
		 if (wordSizeStr.equals("1")){
			 settingFont.setSummary("当前：大");
		 }else if (wordSizeStr.equals("3")){
			 settingFont.setSummary("当前：小");
		 }else{
			 settingFont.setSummary("当前：中");
		 }
		 settingFont.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					wordSizeStr = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE");
					initSelectList(3);
					return true;
				}
		 });
		 
		 //初始化新浪微博绑定设置
		 sinaToken = Common.getCacheStr(ActivitySetting.this,"MACCESSTOKEN");
		 sinaSecret = Common.getCacheStr(ActivitySetting.this,"mTokenSecret");
		 if(!Common.isNullOrBlank(sinaToken) && !Common.isNullOrBlank(sinaSecret)){
			settingSinaShare.setChecked(true);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		 }else
			settingSinaShare.setChecked(false);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
		 //新浪微博绑定事件
//		 settingSinaShare.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//				public boolean onPreferenceClick(Preference preference) {
//					settingSinaShare.setChecked(false);
//					if(!Common.isNullOrBlank(sinaToken) && !Common.isNullOrBlank(sinaSecret)){
//						//settingSinaShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
//						Common.setCacheStr(ActivitySetting.this,"MACCESSTOKEN","");
//						Common.setCacheStr(ActivitySetting.this,"mTokenSecret","");
//						sinaToken = "";
//						sinaSecret = "";
//						Common.ShowInfo(ActivitySetting.this,"成功取消新浪微博绑定！");
//					}else{
//						if (Common.NetWorkGet(ActivitySetting.this) != 0) {
//							Common.WeiboBound(ActivitySetting.this);
//						}else
//							Common.ShowInfo(ActivitySetting.this,"无网络连接,无法绑定操作");
//						return false;
//					}
//					return true;
//				}
//		 });
		 
		 //初始化人人网绑定设置
		 renrenToken = Common.getCacheStr(ActivitySetting.this,"RENRENTOKEN");
		 if(!Common.isNullOrBlank(renrenToken)){
			settingRenrenShare.setChecked(true);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		 }else
			settingRenrenShare.setChecked(false);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
		 //人人网绑定事件
		 settingRenrenShare.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					settingRenrenShare.setChecked(false);
					if(!Common.isNullOrBlank(renrenToken)){
						//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
						Common.setCacheStr(ActivitySetting.this,"RENRENTOKEN","");
						renrenToken = ""; 
						//Common.ShowInfo(ActivitySetting.this,"成功取消人人网绑定！");
					}else{
						if (Common.NetWorkGet(ActivitySetting.this) != 0) {
							//Common.renrenBound(ActivitySetting.this);
						}else
							//Common.ShowInfo(ActivitySetting.this,"无网络连接,无法绑定操作");
						return false;
					}
					return true;
				}
		 });
		
		//关于title加上客户端版本号
		settingAbout.setTitle(settingAbout.getTitle() + Common.VersonGet(this));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.fade, R.anim.hold);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		sinaToken = Common.getCacheStr(ActivitySetting.this,"MACCESSTOKEN");
		sinaSecret = Common.getCacheStr(ActivitySetting.this,"mTokenSecret");
		if(!Common.isNullOrBlank(sinaToken) && !Common.isNullOrBlank(sinaSecret)){
			settingSinaShare.setChecked(true);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		}else
			settingSinaShare.setChecked(false);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
		
	    renrenToken = Common.getCacheStr(ActivitySetting.this,"RENRENTOKEN");
		if(!Common.isNullOrBlank(renrenToken)){
			settingRenrenShare.setChecked(true);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		}else
			settingRenrenShare.setChecked(false);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);

//		LogNews.log(TAG, TAG + " mExiting:" + Common.mExiting);
//
//		if (Common.mExiting == 4) {
//			LogNews.log(TAG, TAG + " mExiting==4 mExiting:" + Common.mExiting);
//			this.finish();
//		}
//		LogNews.log(TAG, "onResume~~~");
	}

	private void getCache() {
		new Thread() {
			public void run() {
			    Common.Getpkginfo(ActivitySetting.this,"com.jiubang.app.news",handler);
			}
		}.start();
	}
	
	Handler handler = new Handler(){
    	public void handleMessage(Message m){ 
    		switch(m.what){
    		case 0:
    			break;
    		case 1://
    			 if (m.obj ==null){
					 return;
				 }
				cacheSize = (String) m.obj;
    			settingClear.setSummary("清除缓存中临时文件，当前缓存:" + cacheSize);
    			break;
    		}
    	}
    };
	
	private void initSelectList(final int type) {
		// 获取ListView
		LayoutInflater factory = LayoutInflater.from(ActivitySetting.this);
		View view = factory.inflate(R.layout.settinglistview, null);
		final ListView list = (ListView) view.findViewById(R.id.ListView01);
		// 把数据项添加到listItem里面
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map0 = new HashMap<String, Object>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		switch (type) {
		case 1:
			if (newspicSwitch.equals("on")) {
				map0.put("ItemText", "仅WIFI时");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "任何网络");
				map1.put("ItemSelect", false);
				listItem.add(map1);
			} else {
				map0.put("ItemText", "仅WIFI时");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "任何网络");
				map1.put("ItemSelect", true);
				listItem.add(map1);
			}
			break;
		case 2:
			if (downloadSwitch.equals("3")) {
				map0.put("ItemText", "WIFI时可用");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "关闭");
				map1.put("ItemSelect", true);
				listItem.add(map1);
			} else {
				map0.put("ItemText", "WIFI时可用");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "关闭");
				map1.put("ItemSelect", false);
				listItem.add(map1);
			}
			break;
		case 3:
			if (wordSizeStr.equals("1")) {
				map0.put("ItemText", "大");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "中");
				map1.put("ItemSelect", false);
				listItem.add(map1);

				map2.put("ItemText", "小");
				map2.put("ItemSelect", false);
				listItem.add(map2);
			} else if (wordSizeStr.equals("3")) {
				map0.put("ItemText", "大");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "中");
				map1.put("ItemSelect", false);
				listItem.add(map1);

				map2.put("ItemText", "小");
				map2.put("ItemSelect", true);
				listItem.add(map2);
			} else {
				map0.put("ItemText", "大");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "中");
				map1.put("ItemSelect", true);
				listItem.add(map1);

				map2.put("ItemText", "小");
				map2.put("ItemSelect", false);
				listItem.add(map2);
			}
			break;
		}

		// 获得SimpleAdapter，并且把它添加到listView中
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,R.layout.settingitem,
			new String[] { "ItemText", "ItemSelect" },
			new int[] { R.id.textView, R.id.checkedTextView }
		);

		list.setAdapter(listItemAdapter);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetting.this);
		switch (type) {
		case 1:
			builder.setTitle("显示正文图片");
			break;
		case 2:
			builder.setTitle("离线阅读");
			break;
		case 3:
			builder.setTitle("文章字体大小");
			break;
		}
		builder.setView(list);
		builder.setNegativeButton("取消", null);
		final AlertDialog alertDialog= builder.create();
		alertDialog.show();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
				// 把所有的单选全部设为非选中
				for (int i = 0; i < arg0.getCount(); i++)
				{
					View v = list.getChildAt(i);
					CheckedTextView checkText = (CheckedTextView) v.findViewById(R.id.checkedTextView);
					checkText.setChecked(false);

				}
				
				// 获得点击项的CheckedTextView，并设为选中
				CheckedTextView check = (CheckedTextView) arg1.findViewById(R.id.checkedTextView);
				check.setChecked(true);
				
				switch (type) {
				case 1:
					switch (arg2) {
					case 0:
						Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached", "on");
						settingPic.setSummary("当前：仅WIFI时");
						break;
					case 1:
						Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached", "off");
						settingPic.setSummary("当前：任何网络");
						break;
					}
					break;
				case 2:
					switch (arg2) {
					case 0:
						Common.setCacheStr(ActivitySetting.this,"MEDIACLIENT_UPDATE", "2");
						settingUpdate.setSummary("当前：WIFI时可用");
						break;
					case 1:
					   break;
					}
					break;
				case 3:
					Common.setCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE", arg2 + 1 + "");
					switch (arg2) {
					case 0:
						settingFont.setSummary("当前：大");
						break;
					case 1:
						settingFont.setSummary("当前：中");
						break;
					case 2:
						settingFont.setSummary("当前：小");
						break;
					}
				/*	if (ContentView.textArrayList!=null && ContentView.textArrayList.size()>0 && Common.wordSize.length > arg2){
              			for(TextView tv : ContentView.textArrayList){
	     	               tv.setTextSize(Common.wordSize[arg2]);
	                	}
              	    }*/
					break;
				}

				alertDialog.dismiss();
				alertDialog.cancel();
			}
		});
	}
}
