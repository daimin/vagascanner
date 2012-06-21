package com.cu.apps.mititarynews.activity;

import com.cu.apps.mititarynews.R;
import com.cu.apps.utils.LogUtils;
import com.cu.apps.view.NewsContentView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class BrowserActivity extends BaseActivity {
	 //Views
	private ProgressBar mProgressBar;
	private WebView mWebView;
		
	private Context mContext;
	private String url;
		
	protected static final String TAG = "BrowserActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mContext = BrowserActivity.this;
    	setContentView(R.layout.browser_activity);
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		this.mProgressBar = (ProgressBar) findViewById(R.id.loadingbar);
		this.mWebView = (WebView) findViewById(R.id.webview);
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");
		LogUtils.i(TAG, "url:"+url);
		mWebView.loadUrl(url);
	}
}