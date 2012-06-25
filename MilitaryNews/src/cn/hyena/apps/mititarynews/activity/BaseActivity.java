package cn.hyena.apps.mititarynews.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class  BaseActivity extends Activity {

	public BaseActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		findViews();
		init();
		setListeners();
	}
	
	protected abstract void findViews();
	protected abstract void init();
	protected abstract void setListeners();
}
