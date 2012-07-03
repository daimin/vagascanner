package cn.hyena.apps.mititarynews.activity;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.hyena.apps.mititarynews.R;
import cn.hyena.apps.mititarynews.adapter.NewsItemAdapter;
import cn.hyena.apps.mititarynews.bean.NewsBean;
import cn.hyena.apps.net.HttpDataService;
import cn.hyena.apps.net.NetWorkException;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.LogUtils;
import cn.hyena.apps.view.DragRefreshListView;

public class MainActivity extends BaseActivity {
	//Views
	private DragRefreshListView mListView;
	private ProgressBar mProgressBar;
	private RelativeLayout startPage;
	private ProgressBar startProgressBar;
	
	private Context mContext;
	private ArrayList<NewsBean> newsBeanArr = new ArrayList<NewsBean>();
	private ArrayList<NewsBean> newsBeanArrTemp = new ArrayList<NewsBean>();
	private NewsItemAdapter mNewsItemAdapter;
	private int pageMark = 1;
	private int progress = 0;
	
	protected static final int MENU_FIRST = Menu.FIRST;
	protected static final int MENU_SECOND = Menu.FIRST + 1;
	protected static final int MENU_THIRD = Menu.FIRST + 2;
	protected static final int MENU_FOURTH = Menu.FIRST + 3;
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
		this.mListView = (DragRefreshListView) findViewById(R.id.newsItemLv);
		this.mProgressBar = (ProgressBar) findViewById(R.id.loadingbar);
		this.startPage = (RelativeLayout) findViewById(R.id.startPage);
		this.startProgressBar = (ProgressBar) findViewById(R.id.startProgressBar);
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
		
		mListView.setOnRefreshListener(new DragRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				if (mListView != null) {
					try {
						if (Common.NETTYPE != 0) {
							getData();
						} else {
						}
					} catch (Exception ex) {
						LogUtils.e(TAG, "ex:" + ex);
					}
				}
			}
		});
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Common.NetWorkGet(mContext);
		Common.getCurrentApnInUse(mContext);
		new Thread() {
			public void run() {
				while (progress < 100) {
					try {
						progress += 10;
						Message message = Message.obtain();
						message.what = 3;
						handler.sendMessage(message);
						Thread.sleep(150);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		getData();
	}
	
	private void getData(){
		if (Common.NETTYPE !=0){
			mProgressBar.setVisibility(View.VISIBLE);
			new Thread() {
				public void run() {
					try {
						HttpDataService.getNewsList(String.valueOf(pageMark),handler, 1);
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
				}
			}.start();
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
				progress = 100;
	            startPage.setVisibility(View.GONE);
	            mListView.onRefreshComplete();
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
            case 3:			
            	startProgressBar.setProgress(progress);
				break;
			default:
				break;
			}
		}
	};
	
	// 添加菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_FIRST, 0, R.string.menu_1).setIcon(R.drawable.menu01);
		menu.add(0, MENU_SECOND, 0, R.string.menu_2).setIcon(R.drawable.menu02);
		menu.add(0, MENU_THIRD, 0, R.string.menu_3).setIcon(R.drawable.menu03);
		menu.add(0, MENU_FOURTH, 0, R.string.menu_4).setIcon(R.drawable.menu04);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单项被选中
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();

		switch (item.getItemId()) {
		case MENU_FIRST:// 刷新
			try {
				if (Common.NETTYPE != 0) {
					this.mProgressBar.setVisibility(View.VISIBLE);
					getData();
				} else {
				}
			} catch (Exception ex) {
				LogUtils.e(TAG, "ex:" + ex);
			}
			return true;
		case MENU_SECOND:// 设置
			intent.setClass(mContext, ActivitySetting.class);
			intent.putExtras(bundle);
			this.startActivity(intent);
			Common.overridePendingTransition(mContext,R.anim.fade, R.anim.hold);
			return true;
		case MENU_THIRD://反馈
			intent.setClass(mContext, ActivitySetting.class);
			intent.putExtras(bundle);
			this.startActivity(intent);
			Common.overridePendingTransition(mContext,R.anim.fade, R.anim.hold);
			return true;
		case MENU_FOURTH:// 退出
			Common.appExit(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// 设置回退
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Common.appExit(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}