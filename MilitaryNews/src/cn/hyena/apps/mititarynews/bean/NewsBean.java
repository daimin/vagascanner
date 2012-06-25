package cn.hyena.apps.mititarynews.bean;

import java.util.Vector;

public class NewsBean {
	/** 资讯标题 */
	public String id = "";
	/** 资讯标题 */
	public String title = "";
	/** 资讯导读内容 */
	public String su = "";
//	/** 资讯发表时间 */
//	public String time = "";
//	/** 资讯来源 */
//	public String source = "";
	/** 图片地址前缀 */
	public String imgUrl = "";
	/** 资讯内容 */
	public String content = "";
	/** 图片 */
	public Vector<ImgageItem> imgageItems = new Vector<NewsBean.ImgageItem>();
	
	public static class ImgageItem{
		
		public ImgageItem(){
			
		}
		
		public int id;
		
		public String url = null;
		
		public String des = null;
	}
}
