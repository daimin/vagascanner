package com.cu.apps.mititarynews.activity;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.cu.apps.mititarynews.R;
import com.cu.apps.utils.Common;
import com.cu.apps.utils.FileUtils;

public class ActivitySetting extends PreferenceActivity {
	private String newspushSwitch = "on";//�������Ϳ���
	private String newspicSwitch = "off";//�Ƿ���������ͼƬ����
	private String downloadSwitch = "2";//�������ؿ��� 2:wifi����´�
	private String wordSizeStr = "2";//�������ִ�С Ĭ��2����
	private String sinaToken = "";//����΢������Token
	private String sinaSecret = "";//����΢������Secret                 
	private String renrenToken = "";//����������Token

	private CheckBoxPreference settingPush;//�������Ϳ��ذ�ť
	private Preference settingPic;//�Ƿ���������ͼƬ���ذ�ť
	private Preference settingUpdate;//�������ؿ��ذ�ť
	private Preference settingClear;//������水ť
	private Preference settingFont;//���ִ�Сѡ��
	private CheckBoxPreference settingSinaShare;//������水ť
	private CheckBoxPreference settingRenrenShare;//������水ť
	private Preference settingAbout;//������

	private String cacheSize;//��ǰ�����С

	protected static final int MENU_FIRST = Menu.FIRST;
	protected static final int MENU_SECOND = Menu.FIRST + 1;
	private static final String TAG = "ActivitySetting";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ������ʾPreferences
		addPreferencesFromResource(R.layout.setting);
		// ���SharedPreferences
		//pre = PreferenceManager.getDefaultSharedPreferences(this);

		settingPush = (CheckBoxPreference) findPreference(getResources().getString(R.string.setting_push_key));
		settingPic = findPreference(getResources().getString(R.string.setting_pic_key));
		settingUpdate = findPreference(getResources().getString(R.string.setting_update_key));
		settingClear = findPreference(getResources().getString(R.string.setting_clear_key));
		settingFont = findPreference(getResources().getString(R.string.setting_font_key));
		settingSinaShare = (CheckBoxPreference)findPreference(getResources().getString(R.string.setting_share_sina_key));
		settingRenrenShare = (CheckBoxPreference) findPreference(getResources().getString(R.string.setting_share_renren_key));
		settingAbout = findPreference(getResources().getString(R.string.setting_about_key));
		// ��ȡ���õ���������
		/*arrPic = getResources().getStringArray(R.array.PicSettingArray);
		arrUpdate = getResources().getStringArray(R.array.OfflineSettingArray);
		arrFont = getResources().getStringArray(R.array.FontSizeSettingArray);*/
		
		initialize();
	}
	
	private void initialize(){
		//��ʼ��������������
		newspushSwitch = Common.getCacheStr(this,"NEWSPUSH1_Cached");
		if (newspushSwitch.equals("off")){
			settingPush.setChecked(false);
		}else{
			settingPush.setChecked(true);
		}
		//�������Ϳ�������/�ر��¼�
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
		
		/*//��ʼ������ͼƬ��������
		newspicSwitch = Common.getCacheStr(this,"NEWSPIC_Cached");
		if (newspicSwitch.equals("on")){
			settingPic.setChecked(true);
		}else{
			settingPic.setChecked(false);
		}
		//����ͼƬ���ؿ�������/�ر��¼�
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
		
		//��ʼ������ͼƬ��������
		newspicSwitch = Common.getCacheStr(this,"NEWSPIC_Cached");
		if (newspicSwitch.equals("on")){
			settingPic.setSummary("��ǰ����WIFIʱ");
		}else{
			settingPic.setSummary("��ǰ���κ�����");
		}
		settingPic.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					newspicSwitch = Common.getCacheStr(ActivitySetting.this,"NEWSPIC_Cached");
					initSelectList(1);
					return true;
				}
		 });
		 
		//��ʼ��������������
		downloadSwitch  = Common.getCacheStr(this,"MEDIACLIENT_UPDATE");
		if (downloadSwitch.equals("3")){
			settingUpdate.setSummary("��ǰ���ر�");
		}else{
			settingUpdate.setSummary("��ǰ��WIFIʱ����");
		}
		settingUpdate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					downloadSwitch  = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_UPDATE");
					initSelectList(2);
					return true;
				}
		 });
		 
		 //��ȡ�����С����ʼ������
		 getCache();
		 //����ͻ��˻����¼�
		 settingClear.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					if (!"0B".equals(cacheSize)){
					   final ProgressDialog m_Dialog = ProgressDialog.show(ActivitySetting.this,null,"���Ժ�,�����������");
					   FileUtils.clearAllCache(ActivitySetting.this);
					   handler.postDelayed(new Runnable(){
							public void run() {
									if (m_Dialog != null) {
									    m_Dialog.dismiss();
										m_Dialog.cancel();
									}
									//getCache();
									cacheSize = "0B";
									settingClear.setSummary("�����������ʱ�ļ�����ǰ����:" + cacheSize);
									Common.ShowInfo(ActivitySetting.this,"�������ɹ�");
							}
					   }, 5000);
					}else
						Common.ShowInfo(ActivitySetting.this,"�޻���,�������������");
					return true;
				}
		 });
     
		 //���ִ�С���ó�ʼ��
		 wordSizeStr = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE");
		 if (wordSizeStr.equals("1")){
			 settingFont.setSummary("��ǰ����");
		 }else if (wordSizeStr.equals("3")){
			 settingFont.setSummary("��ǰ��С");
		 }else{
			 settingFont.setSummary("��ǰ����");
		 }
		 settingFont.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					wordSizeStr = Common.getCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE");
					initSelectList(3);
					return true;
				}
		 });
		 
		 //��ʼ������΢��������
		 sinaToken = Common.getCacheStr(ActivitySetting.this,"MACCESSTOKEN");
		 sinaSecret = Common.getCacheStr(ActivitySetting.this,"mTokenSecret");
		 if(!Common.isNullOrBlank(sinaToken) && !Common.isNullOrBlank(sinaSecret)){
			settingSinaShare.setChecked(true);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		 }else
			settingSinaShare.setChecked(false);
			//settingSinaShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
		 //����΢�����¼�
//		 settingSinaShare.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//				public boolean onPreferenceClick(Preference preference) {
//					settingSinaShare.setChecked(false);
//					if(!Common.isNullOrBlank(sinaToken) && !Common.isNullOrBlank(sinaSecret)){
//						//settingSinaShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
//						Common.setCacheStr(ActivitySetting.this,"MACCESSTOKEN","");
//						Common.setCacheStr(ActivitySetting.this,"mTokenSecret","");
//						sinaToken = "";
//						sinaSecret = "";
//						Common.ShowInfo(ActivitySetting.this,"�ɹ�ȡ������΢���󶨣�");
//					}else{
//						if (Common.NetWorkGet(ActivitySetting.this) != 0) {
//							Common.WeiboBound(ActivitySetting.this);
//						}else
//							Common.ShowInfo(ActivitySetting.this,"����������,�޷��󶨲���");
//						return false;
//					}
//					return true;
//				}
//		 });
		 
		 //��ʼ��������������
		 renrenToken = Common.getCacheStr(ActivitySetting.this,"RENRENTOKEN");
		 if(!Common.isNullOrBlank(renrenToken)){
			settingRenrenShare.setChecked(true);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_sharebind_widget);
		 }else
			settingRenrenShare.setChecked(false);
			//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
		 //���������¼�
		 settingRenrenShare.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					settingRenrenShare.setChecked(false);
					if(!Common.isNullOrBlank(renrenToken)){
						//settingRenrenShare.setWidgetLayoutResource(R.layout.setting_shareunbind_widget);
						Common.setCacheStr(ActivitySetting.this,"RENRENTOKEN","");
						renrenToken = ""; 
						//Common.ShowInfo(ActivitySetting.this,"�ɹ�ȡ���������󶨣�");
					}else{
						if (Common.NetWorkGet(ActivitySetting.this) != 0) {
							//Common.renrenBound(ActivitySetting.this);
						}else
							//Common.ShowInfo(ActivitySetting.this,"����������,�޷��󶨲���");
						return false;
					}
					return true;
				}
		 });
		
		//����title���Ͽͻ��˰汾��
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
    			settingClear.setSummary("�����������ʱ�ļ�����ǰ����:" + cacheSize);
    			break;
    		}
    	}
    };
	
	private void initSelectList(final int type) {
		// ��ȡListView
		LayoutInflater factory = LayoutInflater.from(ActivitySetting.this);
		View view = factory.inflate(R.layout.settinglistview, null);
		final ListView list = (ListView) view.findViewById(R.id.ListView01);
		// ����������ӵ�listItem����
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map0 = new HashMap<String, Object>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		switch (type) {
		case 1:
			if (newspicSwitch.equals("on")) {
				map0.put("ItemText", "��WIFIʱ");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "�κ�����");
				map1.put("ItemSelect", false);
				listItem.add(map1);
			} else {
				map0.put("ItemText", "��WIFIʱ");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "�κ�����");
				map1.put("ItemSelect", true);
				listItem.add(map1);
			}
			break;
		case 2:
			if (downloadSwitch.equals("3")) {
				map0.put("ItemText", "WIFIʱ����");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "�ر�");
				map1.put("ItemSelect", true);
				listItem.add(map1);
			} else {
				map0.put("ItemText", "WIFIʱ����");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "�ر�");
				map1.put("ItemSelect", false);
				listItem.add(map1);
			}
			break;
		case 3:
			if (wordSizeStr.equals("1")) {
				map0.put("ItemText", "��");
				map0.put("ItemSelect", true);
				listItem.add(map0);

				map1.put("ItemText", "��");
				map1.put("ItemSelect", false);
				listItem.add(map1);

				map2.put("ItemText", "С");
				map2.put("ItemSelect", false);
				listItem.add(map2);
			} else if (wordSizeStr.equals("3")) {
				map0.put("ItemText", "��");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "��");
				map1.put("ItemSelect", false);
				listItem.add(map1);

				map2.put("ItemText", "С");
				map2.put("ItemSelect", true);
				listItem.add(map2);
			} else {
				map0.put("ItemText", "��");
				map0.put("ItemSelect", false);
				listItem.add(map0);

				map1.put("ItemText", "��");
				map1.put("ItemSelect", true);
				listItem.add(map1);

				map2.put("ItemText", "С");
				map2.put("ItemSelect", false);
				listItem.add(map2);
			}
			break;
		}

		// ���SimpleAdapter�����Ұ�����ӵ�listView��
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,R.layout.settingitem,
			new String[] { "ItemText", "ItemSelect" },
			new int[] { R.id.textView, R.id.checkedTextView }
		);

		list.setAdapter(listItemAdapter);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetting.this);
		switch (type) {
		case 1:
			builder.setTitle("��ʾ����ͼƬ");
			break;
		case 2:
			builder.setTitle("�����Ķ�");
			break;
		case 3:
			builder.setTitle("���������С");
			break;
		}
		builder.setView(list);
		builder.setNegativeButton("ȡ��", null);
		final AlertDialog alertDialog= builder.create();
		alertDialog.show();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
				// �����еĵ�ѡȫ����Ϊ��ѡ��
				for (int i = 0; i < arg0.getCount(); i++)
				{
					View v = list.getChildAt(i);
					CheckedTextView checkText = (CheckedTextView) v.findViewById(R.id.checkedTextView);
					checkText.setChecked(false);

				}
				
				// ��õ�����CheckedTextView������Ϊѡ��
				CheckedTextView check = (CheckedTextView) arg1.findViewById(R.id.checkedTextView);
				check.setChecked(true);
				
				switch (type) {
				case 1:
					switch (arg2) {
					case 0:
						Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached", "on");
						settingPic.setSummary("��ǰ����WIFIʱ");
						break;
					case 1:
						Common.setCacheStr(ActivitySetting.this,"NEWSPIC_Cached", "off");
						settingPic.setSummary("��ǰ���κ�����");
						break;
					}
					break;
				case 2:
					switch (arg2) {
					case 0:
						Common.setCacheStr(ActivitySetting.this,"MEDIACLIENT_UPDATE", "2");
						settingUpdate.setSummary("��ǰ��WIFIʱ����");
						break;
					case 1:
					   break;
					}
					break;
				case 3:
					Common.setCacheStr(ActivitySetting.this,"MEDIACLIENT_SIZE", arg2 + 1 + "");
					switch (arg2) {
					case 0:
						settingFont.setSummary("��ǰ����");
						break;
					case 1:
						settingFont.setSummary("��ǰ����");
						break;
					case 2:
						settingFont.setSummary("��ǰ��С");
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
