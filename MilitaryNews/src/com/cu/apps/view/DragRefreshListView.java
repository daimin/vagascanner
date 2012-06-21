package com.cu.apps.view;

import java.util.Date;

import com.cu.apps.mititarynews.R;
import com.cu.apps.utils.LogUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DragRefreshListView extends ListView implements OnScrollListener {
	private static final String TAG = "DragRefreshListView";
	protected int nStartY;
	protected int nFirstItemIndex;

	// ===================================================
	// 下拉刷新需要的属性
	// ===================================================

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private final static int STATE_RELEASE_TO_REFRESH = 0;
	private final static int STATE_PULL_TO_REFRESH = 1;
	private final static int STATE_REFRESHING = 2;
	private final static int STATE_REFRESH_DONE = 3;
	private final static int STATE_LOADING = 4;

	private int nCurrentState;

	private LinearLayout headView;
	private TextView headerTipsTextview;
	private TextView headerLastUpdatedTextView;
	private ImageView headerArrowImageView;
	private ProgressBar headerProgressBar;

	private RotateAnimation headerAnimation;
	private RotateAnimation headerReverseAnimation;

	// 用于保证nStartY的值在一个完整的touch事件中只被记录一次
	protected boolean bIsRecored;
	protected boolean bIsBack;

	protected int nHeadContentWidth;
	protected int nHeadContentHeight;

	protected boolean bIsRefreshable;
	protected OnRefreshListener refreshListener;

	// ===================================================
	// 滚动至底部进行新的一页加载
	// ===================================================
	protected int nCurrentPages = 1;
	protected int nLastPages = -1; // 最后一个页面
	protected boolean isLoadingMore = false; // 是否在加载更多，如果是不再响应消息

	protected boolean bIsScrollLoading;
	protected OnLoadMoreListener loadMoreListener;

	//private static final String TAG = "DragRefreshListView";
	
	public DragRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public DragRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * @desc 获取当前正在显示的page
	 * @return
	 */
	public int getCurrentPage() {
		return nCurrentPages;
	}


	/**
	 * @desc 设置最后一页
	 * @param nIndex
	 * @return
	 */
	public void setLastPage(int lastPage) {
		nLastPages = lastPage;
	}

	/**
	 * @desc 给定page是否是最后一页
	 * @param nPage
	 * @return
	 */
	public boolean isLastPage(int nPage) {
		if (nLastPages == -1)
			return false;

		return nPage == nLastPages;
	}
	
	/**
	 * 停止加载
	 */
	public void stopLoadingMore(){
		this.isLoadingMore = false;
	}

	// ============================================
	// 下拉刷新对外提供过的接口
	// ============================================
	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		bIsRefreshable = true;
	}

	public void onRefreshComplete() {
		nCurrentState = STATE_REFRESH_DONE;
		headerLastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	// ============================================
	// 滚动到底部加载对外提供过的接口
	// ============================================
	public interface OnLoadMoreListener {
		public void onLoadMore(int nNextPage);
	}

	public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
		bIsScrollLoading = true;
	}

	public void onLoadMoreComplete() {
		
		nCurrentPages++;
		isLoadingMore  = false;
	}

	public void reset() {
		nCurrentPages = 1;
		nLastPages = -1;
	}

	private void init(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);

		// 头部相关的初始化
		headView = (LinearLayout) inflater.inflate(R.layout.lib_list_header,null);
				
		headerArrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		headerArrowImageView.setMinimumWidth(50);
		headerArrowImageView.setMinimumHeight(50);

		headerProgressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		headerTipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		headerLastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);
		measureView(headView);
		nHeadContentHeight = headView.getMeasuredHeight();
		nHeadContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * nHeadContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		headerAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		headerAnimation.setInterpolator(new LinearInterpolator());
		headerAnimation.setDuration(250);
		headerAnimation.setFillAfter(true);

		headerReverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		headerReverseAnimation.setInterpolator(new LinearInterpolator());
		headerReverseAnimation.setDuration(200);
		headerReverseAnimation.setFillAfter(true);

		nCurrentState = STATE_REFRESH_DONE;
		bIsRefreshable = false;

		// 底部相关的初始化
		bIsScrollLoading = false;
	}

	public void onScroll(AbsListView arg0, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		nFirstItemIndex = firstVisibleItem;

		if ((firstVisibleItem + visibleItemCount) == totalItemCount
				&& totalItemCount != 0) {
			if (nLastPages != -1 && nCurrentPages >= nLastPages)
				return;

			onLoadMore( nCurrentPages);
		}
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (bIsRefreshable == false) {
			return super.onTouchEvent(event);
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (nFirstItemIndex == 0 && !bIsRecored) {
				bIsRecored = true;
				nStartY = (int) event.getY();
			}
			break;
		//
		case MotionEvent.ACTION_UP:

			if (nCurrentState != STATE_REFRESHING
					&& nCurrentState != STATE_LOADING) {
				if (nCurrentState == STATE_REFRESH_DONE) {
					// 什么都不做
				}
				if (nCurrentState == STATE_PULL_TO_REFRESH) {
					nCurrentState = STATE_REFRESH_DONE;
					changeHeaderViewByState();
				}
				if (nCurrentState == STATE_RELEASE_TO_REFRESH) {
					nCurrentState = STATE_REFRESHING;
					changeHeaderViewByState();
					onRefresh();
				}
			}

			bIsRecored = false;
			bIsBack = false;

			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();

			if (!bIsRecored && nFirstItemIndex == 0) {
				bIsRecored = true;
				nStartY = tempY;
			}

			if (nCurrentState != STATE_REFRESHING && bIsRecored
					&& nCurrentState != STATE_LOADING) {

				// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

				// 可以松手去刷新了
				if (nCurrentState == STATE_RELEASE_TO_REFRESH) {

					setSelection(0);

					// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if (((tempY - nStartY) / RATIO < nHeadContentHeight)
							&& (tempY - nStartY) > 0) {
						nCurrentState = STATE_PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
					// 一下子推到顶了
					else if (tempY - nStartY <= 0) {
						nCurrentState = STATE_REFRESH_DONE;
						changeHeaderViewByState();
					}
					// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开刷新的时候,STATE_REFRESH_DONE或者是STAT_PULL_TO_REFRESH状态
				if (nCurrentState == STATE_PULL_TO_REFRESH) {

					setSelection(0);

					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if ((tempY - nStartY) / RATIO >= nHeadContentHeight) {
						nCurrentState = STATE_RELEASE_TO_REFRESH;
						bIsBack = true;
						changeHeaderViewByState();
					}
					// 上推到顶了
					else if (tempY - nStartY <= 0) {
						nCurrentState = STATE_REFRESH_DONE;
						changeHeaderViewByState();
					}
				}

				// done状态下
				if (nCurrentState == STATE_REFRESH_DONE) {
					if (tempY - nStartY > 0) {
						nCurrentState = STATE_PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}

				// 更新headView的size
				if (nCurrentState == STATE_PULL_TO_REFRESH) {
					headView.setPadding(0, -1 * nHeadContentHeight
							+ (tempY - nStartY) / RATIO, 0, 0);

				}

				// 更新headView的paddingTop
				if (nCurrentState == STATE_RELEASE_TO_REFRESH) {
					headView.setPadding(0, (tempY - nStartY) / RATIO
							- nHeadContentHeight, 0, 0);
				}

			}

			break;
		}

		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (nCurrentState) {

		case STATE_PULL_TO_REFRESH:
			LogUtils.i(TAG,"STATE_PULL_TO_REFRESH");
			headerProgressBar.setVisibility(View.GONE);
			headerTipsTextview.setVisibility(View.VISIBLE);
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setVisibility(View.VISIBLE);
			// 是由STAT_RELEASE_TO_REFRESH状态转变来的
			if (bIsBack) {
				bIsBack = false;
				headerArrowImageView.clearAnimation();
				headerArrowImageView.startAnimation(headerReverseAnimation);

				headerTipsTextview.setText("下拉列表将刷新");
			} else {
				headerTipsTextview.setText("下拉列表将刷新");
			}
			break;
			
		case STATE_RELEASE_TO_REFRESH:
			LogUtils.i(TAG,"STATE_RELEASE_TO_REFRESH");
			headerArrowImageView.setVisibility(View.VISIBLE);
			headerProgressBar.setVisibility(View.GONE);
			headerTipsTextview.setVisibility(View.VISIBLE);
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);

			headerArrowImageView.clearAnimation();
			headerArrowImageView.startAnimation(headerAnimation);

			headerTipsTextview.setText("松开列表将刷新");

			break;

		case STATE_REFRESHING:
			LogUtils.i(TAG,"STATE_REFRESHING");
			headView.setPadding(0, 0, 0, 0);
			
			headerProgressBar.setVisibility(View.VISIBLE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setVisibility(View.GONE);
			headerTipsTextview.setText("加载中...");
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;

		case STATE_REFRESH_DONE:
			LogUtils.i(TAG,"STATE_REFRESH_DONE");
			headView.setPadding(0, -1 * nHeadContentHeight, 0, 0);
			
			headerProgressBar.setVisibility(View.GONE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setImageResource(R.drawable.drag_arrow);
			headerTipsTextview.setText("下拉列表将刷新");
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		headerLastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		super.setAdapter(adapter);
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	private void onLoadMore(int nNextPage) {
		if (isLoadingMore == true)
			return;

		if (loadMoreListener != null) {
			loadMoreListener.onLoadMore(nNextPage);
		}

		isLoadingMore = true;
	}
}
