package cn.hyena.apps.mititarynews.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.hyena.apps.mititarynews.R;

import cn.hyena.apps.mititarynews.bean.NewsBean;

public class NewsItemAdapter extends BaseAdapter{
	//private static final String TAG = "NewsItemAdapter";
	private LayoutInflater mInflater;
	private ArrayList<NewsBean> newsBeanArr;
	private NewsBean newsBean;
	public NewsItemAdapter(Context context,ArrayList<NewsBean> newsBeanArr) {
		this.mInflater = LayoutInflater.from(context);
		this.newsBeanArr = newsBeanArr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsBeanArr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.news_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			//holder.time = (TextView) convertView.findViewById(R.id.br_item_time);
			holder.guidance = (TextView) convertView.findViewById(R.id.item_guidance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		newsBean = (NewsBean) newsBeanArr.get(position);
		
		holder.title.setText(newsBean.title);
		holder.guidance.setText(newsBean.su);
		return convertView;
	}
	
	public final class ViewHolder {
		 public TextView time;
		 public TextView guidance;
		 public TextView title;
    }
}
