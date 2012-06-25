package cn.hyena.apps.mititarynews.activity;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import cn.hyena.apps.mititarynews.R;

import cn.hyena.apps.mititarynews.bean.NewsBean;
import cn.hyena.apps.net.HttpDataService;
import cn.hyena.apps.net.NetWorkException;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.LogUtils;
import cn.hyena.apps.view.NewsContentView;

public class NewsContentActivity extends BaseActivity {
    //Views
	private ProgressBar mProgressBar;
	private NewsContentView mNewsContentView;
	private ScrollView mScrollView;
	private LinearLayout newsLayout;
	
	private Context mContext;
	private String newsId = "";
	private NewsBean newsBean;
	
	protected static final String TAG = "NewsContentActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mContext = NewsContentActivity.this;
    	setContentView(R.layout.newscontent_activity);
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		this.mProgressBar = (ProgressBar) findViewById(R.id.loadingbar);
		this.mScrollView = (ScrollView) findViewById(R.id.content_scroll);
		this.newsLayout = (LinearLayout) findViewById(R.id.newsContent_layout);
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void init() {
		Bundle bundle = getIntent().getExtras();
		newsId = bundle.getString("id");
		LogUtils.i(TAG, "MEWSID:"+newsId);
		
		// TODO Auto-generated method stub
		Common.NetWorkGet(mContext);
		Common.getCurrentApnInUse(mContext);
		if (Common.NETTYPE !=0){
			mProgressBar.setVisibility(View.VISIBLE);
			   new Thread(){
				   public void run() {
					   try {
						HttpDataService.getNewsContent(newsId,handler,1);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NetWorkException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }}.start();
		}
	}
	
	 public Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					if (null == msg.obj || "".equals(msg.obj)) {
						return;
					}
					
					if (mNewsContentView != null){
						mNewsContentView.removeAllViews();
					}
					newsLayout.removeAllViews();
					mScrollView.setVisibility(View.VISIBLE);
					//newsContentLayout.setVisibility(View.VISIBLE);
					newsBean = (NewsBean) msg.obj;
					
	                
					mNewsContentView = new NewsContentView(mContext,newsBean);
					mNewsContentView.doView();
					newsLayout.addView(mNewsContentView);
					//new ZoomContentTextView(scrollView);
					mScrollView.scrollTo(0, 0);
					mProgressBar.setVisibility(View.GONE); 
					break;
	            case 2:			
					break;
				default:
					break;
				}
			}
		};
}