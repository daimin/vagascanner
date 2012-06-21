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
	// ����ˢ����Ҫ������
	// ===================================================

	// ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı���
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

	// ���ڱ�֤nStartY��ֵ��һ��������touch�¼���ֻ����¼һ��
	protected boolean bIsRecored;
	protected boolean bIsBack;

	protected int nHeadContentWidth;
	protected int nHeadContentHeight;

	protected boolean bIsRefreshable;
	protected OnRefreshListener refreshListener;

	// ===================================================
	// �������ײ������µ�һҳ����
	// ===================================================
	protected int nCurrentPages = 1;
	protected int nLastPages = -1; // ���һ��ҳ��
	protected boolean isLoadingMore = false; // �Ƿ��ڼ��ظ��࣬����ǲ�����Ӧ��Ϣ

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
	 * @desc ��ȡ��ǰ������ʾ��page
	 * @return
	 */
	public int getCurrentPage() {
		return nCurrentPages;
	}


	/**
	 * @desc �������һҳ
	 * @param nIndex
	 * @return
	 */
	public void setLastPage(int lastPage) {
		nLastPages = lastPage;
	}

	/**
	 * @desc ����page�Ƿ������һҳ
	 * @param nPage
	 * @return
	 */
	public boolean isLastPage(int nPage) {
		if (nLastPages == -1)
			return false;

		return nPage == nLastPages;
	}
	
	/**
	 * ֹͣ����
	 */
	public void stopLoadingMore(){
		this.isLoadingMore = false;
	}

	// ============================================
	// ����ˢ�¶����ṩ���Ľӿ�
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
		headerLastUpdatedTextView.setText("�������:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	// ============================================
	// �������ײ����ض����ṩ���Ľӿ�
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

		// ͷ����صĳ�ʼ��
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

		// �ײ���صĳ�ʼ��
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
					// ʲô������
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

				// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���

				// ��������ȥˢ����
				if (nCurrentState == STATE_RELEASE_TO_REFRESH) {

					setSelection(0);

					// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
					if (((tempY - nStartY) / RATIO < nHeadContentHeight)
							&& (tempY - nStartY) > 0) {
						nCurrentState = STATE_PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
					// һ�����Ƶ�����
					else if (tempY - nStartY <= 0) {
						nCurrentState = STATE_REFRESH_DONE;
						changeHeaderViewByState();
					}
					// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
					else {
						// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
					}
				}
				// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,STATE_REFRESH_DONE������STAT_PULL_TO_REFRESH״̬
				if (nCurrentState == STATE_PULL_TO_REFRESH) {

					setSelection(0);

					// ���������Խ���RELEASE_TO_REFRESH��״̬
					if ((tempY - nStartY) / RATIO >= nHeadContentHeight) {
						nCurrentState = STATE_RELEASE_TO_REFRESH;
						bIsBack = true;
						changeHeaderViewByState();
					}
					// ���Ƶ�����
					else if (tempY - nStartY <= 0) {
						nCurrentState = STATE_REFRESH_DONE;
						changeHeaderViewByState();
					}
				}

				// done״̬��
				if (nCurrentState == STATE_REFRESH_DONE) {
					if (tempY - nStartY > 0) {
						nCurrentState = STATE_PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}

				// ����headView��size
				if (nCurrentState == STATE_PULL_TO_REFRESH) {
					headView.setPadding(0, -1 * nHeadContentHeight
							+ (tempY - nStartY) / RATIO, 0, 0);

				}

				// ����headView��paddingTop
				if (nCurrentState == STATE_RELEASE_TO_REFRESH) {
					headView.setPadding(0, (tempY - nStartY) / RATIO
							- nHeadContentHeight, 0, 0);
				}

			}

			break;
		}

		return super.onTouchEvent(event);
	}

	// ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	private void changeHeaderViewByState() {
		switch (nCurrentState) {

		case STATE_PULL_TO_REFRESH:
			LogUtils.i(TAG,"STATE_PULL_TO_REFRESH");
			headerProgressBar.setVisibility(View.GONE);
			headerTipsTextview.setVisibility(View.VISIBLE);
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setVisibility(View.VISIBLE);
			// ����STAT_RELEASE_TO_REFRESH״̬ת������
			if (bIsBack) {
				bIsBack = false;
				headerArrowImageView.clearAnimation();
				headerArrowImageView.startAnimation(headerReverseAnimation);

				headerTipsTextview.setText("�����б�ˢ��");
			} else {
				headerTipsTextview.setText("�����б�ˢ��");
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

			headerTipsTextview.setText("�ɿ��б�ˢ��");

			break;

		case STATE_REFRESHING:
			LogUtils.i(TAG,"STATE_REFRESHING");
			headView.setPadding(0, 0, 0, 0);
			
			headerProgressBar.setVisibility(View.VISIBLE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setVisibility(View.GONE);
			headerTipsTextview.setText("������...");
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;

		case STATE_REFRESH_DONE:
			LogUtils.i(TAG,"STATE_REFRESH_DONE");
			headView.setPadding(0, -1 * nHeadContentHeight, 0, 0);
			
			headerProgressBar.setVisibility(View.GONE);
			headerArrowImageView.clearAnimation();
			headerArrowImageView.setImageResource(R.drawable.drag_arrow);
			headerTipsTextview.setText("�����б�ˢ��");
			headerLastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	// �˷���ֱ���հ��������ϵ�һ������ˢ�µ�demo���˴��ǡ����ơ�headView��width�Լ�height
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
		headerLastUpdatedTextView.setText("�������:" + new Date().toLocaleString());
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
