package com.cu.apps.mititarynews.bean;

import java.util.Vector;

public class NewsBean {
	/** ��Ѷ���� */
	public String id = "";
	/** ��Ѷ���� */
	public String title = "";
	/** ��Ѷ�������� */
	public String su = "";
//	/** ��Ѷ����ʱ�� */
//	public String time = "";
//	/** ��Ѷ��Դ */
//	public String source = "";
	/** ͼƬ��ַǰ׺ */
	public String imgUrl = "";
	/** ��Ѷ���� */
	public String content = "";
	/** ͼƬ */
	public Vector<ImgageItem> imgageItems = new Vector<NewsBean.ImgageItem>();
	
	public static class ImgageItem{
		
		public ImgageItem(){
			
		}
		
		public int id;
		
		public String url = null;
		
		public String des = null;
	}
}
