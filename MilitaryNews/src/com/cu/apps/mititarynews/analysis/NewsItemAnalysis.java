package com.cu.apps.mititarynews.analysis;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cu.apps.mititarynews.bean.NewsBean;
import com.cu.apps.utils.Common;
/**
 * 新闻列表项解析
 */
public class NewsItemAnalysis{

	public ArrayList<NewsBean> newsBeanArr = new ArrayList<NewsBean>();

	public void parser(String str) {
		try {
			JSONArray jsonArray =  new JSONArray(str);
			if (jsonArray != null && jsonArray.length() > 0) {
				JSONObject obj = null;
				NewsBean newsBean = null;
				for (int i = 0; i < jsonArray.length(); i++) {
					obj = jsonArray.getJSONObject(i);
					newsBean = new NewsBean();
					if (!Common.isNullOrBlank(obj.optString("id"))) {
						newsBean.id = obj.optString("id");
					}
					if (!Common.isNullOrBlank(obj.optString("title"))
							&& !"null".equals(obj.optString("title"))) {
						newsBean.title = Common.convSpecialChar(obj.optString("title"));
					}
					if (!Common.isNullOrBlank(obj.optString("resume"))
							&& !"null".equals(obj.optString("resume"))) {
						newsBean.su = Common.convSpecialChar(obj.optString("resume"));
					}
					newsBeanArr.add(newsBean);
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
