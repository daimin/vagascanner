package cn.hyena.apps.mititarynews.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.hyena.apps.mititarynews.R;

import cn.hyena.apps.mititarynews.adapter.NewsItemAdapter;
import cn.hyena.apps.mititarynews.bean.NewsBean;
import cn.hyena.apps.net.HttpDataService;
import cn.hyena.apps.net.NetWorkException;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.LogUtils;

public class MainActivity extends BaseActivity {
	//Views
	private ListView mListView;
	private ProgressBar mProgressBar;
	
	private Context mContext;
	private ArrayList<NewsBean> newsBeanArr = new ArrayList<NewsBean>();
	private ArrayList<NewsBean> newsBeanArrTemp = new ArrayList<NewsBean>();
	private NewsItemAdapter mNewsItemAdapter;
	
	protected static final String TAG = "MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mContext = MainActivity.this;
    	setContentView(R.layout.main_activity); 
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		this.mListView = (ListView) findViewById(R.id.newsItemLv);
		this.mProgressBar = (ProgressBar) findViewById(R.id.loadingbar);
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,long id) {
				LogUtils.i(TAG, "onListItemClick() id:" + id);
				if (newsBeanArr != null) {
					int arrPos = position;
					if(position > 0){
					    arrPos = position - 1;
					}
					NewsBean bean = newsBeanArr.get(arrPos);
					if (bean != null && !Common.isNullOrBlank(bean.id)) {
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("id", bean.id);
						intent.putExtras(bundle);
						intent.setClass(mContext, NewsContentActivity.class);
						mContext.startActivity(intent);
					}
				}else{
					Toast.makeText(mContext, "数据暂未加载完成，请稍后操作",Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Common.NetWorkGet(mContext);
		Common.getCurrentApnInUse(mContext);
		if (Common.NETTYPE !=0){
			mProgressBar.setVisibility(View.VISIBLE);
			   new Thread(){
				   public void run() {
					   try {
						HttpDataService.getNewsList("",handler,1);
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
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (null == msg.obj || "".equals(msg.obj)) {
					return;
				}
				mProgressBar.setVisibility(View.GONE);
				newsBeanArrTemp = (ArrayList<NewsBean>) msg.obj;
				newsBeanArr.clear();
				newsBeanArr.addAll(newsBeanArrTemp);
				mNewsItemAdapter = new NewsItemAdapter(mContext,newsBeanArr); 
				Animation animation = (Animation)AnimationUtils.loadAnimation(mContext, R.anim.list_anim);
				LayoutAnimationController lac = new LayoutAnimationController(animation);
				lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
				lac.setDelay(0.4f);
				mListView.setVisibility(View.VISIBLE);
				mListView.setLayoutAnimation(lac);
				mListView.setAdapter(mNewsItemAdapter); 
				break;
            case 2:			
				break;
			default:
				break;
			}
		}
	};
}