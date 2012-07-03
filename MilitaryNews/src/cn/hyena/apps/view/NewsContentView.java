package cn.hyena.apps.view;

import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hyena.apps.mititarynews.R;

import cn.hyena.apps.mititarynews.activity.BrowserActivity;
import cn.hyena.apps.mititarynews.bean.NewsBean;
import cn.hyena.apps.mititarynews.bean.NewsBean.ImgageItem;
import cn.hyena.apps.utils.Common;
import cn.hyena.apps.utils.ImageAsync;
import cn.hyena.apps.utils.ImageUtils;
import cn.hyena.apps.utils.Utils;

/**
 * 新闻内容正文布局
 * @author 
 *
 */
public class NewsContentView extends LinearLayout {
	
	protected static final String TAG = "NewsContentView";
	/** 上下文*/
	private static Context context = null;
	/** 数据对象*/
	private NewsBean newsBean = null;
	/** 图片对象*/
	private ImageView imageView = null;
	/** 文本对象*/
	private TextView textView = null;
	
	private TextView title;
	
//	private TextView time;
//	
//	private TextView source;
//	
//	private TextView su;
//	
//	private ImageView suIV;
	
	private LinearLayout contentLinearLayout;

	private int index =0;
	
	//private static String titleStr= "";
	
	public static ArrayList<TextView> textArrayList = new ArrayList<TextView>();
	
	private String newspicSwitch = "off";//是否下载新闻图片开关
	
	@SuppressWarnings("static-access")
	public NewsContentView(Context context,NewsBean newsBean) {
		super(context);
		this.context = context;
		this.newsBean = newsBean;
		this.init();
	}

	@SuppressWarnings("static-access")
	public NewsContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.init();
	}

	private void init() {
		this.setBackgroundResource(R.drawable.resource);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.setPadding(0, 0, 0, 0);
		newspicSwitch = Common.getCacheStr(context,"NEWSPIC_Cached");
		Common.screenwidth=Common.GetW(context);
	}
	
	public void doView()
	{
		doHead();
		if(newsBean != null)
		{   //addLine();
		    contentLinearLayout = new LinearLayout(context);
		    contentLinearLayout.setOrientation(VERTICAL);
		    contentLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
		    addView(contentLinearLayout);
			doBody(newsBean.content);
		}
	}
	
	
	public void doBody(String content)
	{    
		if(!"on".equals(newspicSwitch) || Common.NetWorkGet(context) == 2){
		index = 0;
		//Log.e(TAG,newsBean.content);
			try {
				String array [] = content.split("<p>");
				if(array.length > 0)
				{
					Vector<ImgageItem> vectorI = newsBean.imgageItems;
					for(int i = 0; i< array.length; i++)
					{
						String temp = array[i];
						//Log.e(TAG,array.length+"");
						//Log.e(TAG,"temp"+temp);
						if(temp != null && "<img>".equals(temp.trim()))
						{
							 
							if(vectorI != null && vectorI.size() > 0)
							{
						//		System.out.println("sie --------" + vector.size());
								ImgageItem imgageItem =  vectorI.get(index);
								addImage(imgageItem.url, imgageItem.des);
								index++;
							}
						}else
						{
							//addText(temp);//过滤掉剩余的标签
							addText(Utils.html2Text(temp));
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			 content = content.replace("<p>", "").replace("<img>", "");
			 addText(Utils.html2Text(content));
		}
	}
	
	/**
	 *生成标题
	 */
	private void doHead()
	{
		LinearLayout headLayout = new LinearLayout(context);
		headLayout.setOrientation(VERTICAL);
		headLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
		addView(headLayout);
		
		title = new TextView(context);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setTextSize(18);
		title.setTextColor(Color.BLACK);
		title.setPadding(0, ImageUtils.dip2px(10), 0, ImageUtils.dip2px(5));
		title.setText(newsBean.title);
		headLayout.addView(title);
		
//		LinearLayout timeLayout = new LinearLayout(context);
//		timeLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
//		timeLayout.setPadding(0, 0, 0, ImageUtils.dip2px(5));
//		addView(timeLayout);
//		
//		time = new TextView(context);
//		time.setTextSize(12);
//		time.setTextColor(Color.GRAY);
//		time.setPadding(0, 0, 0, ImageUtils.dip2px(5));
//		time.setText(newsBean.time);
//		timeLayout.addView(time);
//
//		source = new TextView(context);
//		source.setTextSize(12);
//		source.setTextColor(Color.GRAY);
//		source.setPadding(ImageUtils.dip2px(5), 0, 0, ImageUtils.dip2px(5));
//		
//		suIV = new ImageView(context);
//		su = new TextView(context);
//		if(newsBean.source != null && !"".equals(newsBean.source)){
//			source.setText("导读"+ newsBean.source);
//		}
//		
//		timeLayout.addView(source);
//		
//		if(newsBean.su != null && !"".equals(newsBean.su.trim()))
//		{
//			//addLine();
//			LinearLayout suLayout = new LinearLayout(context);
//			suLayout.setPadding(ImageUtils.dip2px(5), ImageUtils.dip2px(5), ImageUtils.dip2px(5), ImageUtils.dip2px(5));
//			suLayout.setBackgroundResource(R.drawable.resource_bg);
//			addView(suLayout);
//			suLayout.addView(suIV);
//			
//			su.setTextSize(16);
//			su.setTextColor(Color.BLACK);
//			//su.setBackgroundResource(R.drawable.resource);
//			su.setLineSpacing(1.1f,1.2f);
//			su.setPadding(ImageUtils.dip2px(5), 0, 0, 0);
//			SpannableString msp = new SpannableString("[导读] "); 
//			msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
//			su.setText(msp+newsBean.su);
//			suLayout.addView(su);
//		}
	}
	
	/**
	 * 添加图片布局
	 * @param url
	 * @param des
	 */
	private void addImage(String url,String des)
	{
		LinearLayout imgageLayout = new LinearLayout(context);
		imgageLayout.setOrientation(VERTICAL);
		imgageLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
		imgageLayout.setPadding(0,ImageUtils.dip2px(10), 0, ImageUtils.dip2px(10));
		contentLinearLayout.addView(imgageLayout);
//		
//		 int tempW = loadDrawable.getIntrinsicWidth() * 2;
//         int tempH = loadDrawable.getIntrinsicHeight() * 2;
         
//		Bitmap temp = ImageUtils.resizeImage(ImageUtils.drawableToBitmap(loadDrawable), tempW, tempH);
		
//		imageView.setImageBitmap(temp);//默认图片
		imageView = new ImageView(context);
		imageView.setImageResource(R.drawable.default_pic);
		imageView.setTag(url);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setAdjustViewBounds(true);
		imgageLayout.addView(imageView);
		ImageAsync.getImageAscnc().execute(imageView,"newscontent");//异步拿取图片
		
//		  
//         if(temp != null)
//         {
//       	   softReference = new SoftReference<Bitmap>(temp);
//         }
//         loadDrawable = null;
		if(des != null && !"".equals(des))
		{
			textView = new TextView(context);
			textView.setTextSize(14);
			textView.setTextColor(Color.BLACK);
			textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
			textView.setPadding(0, ImageUtils.dip2px(5), 0, 0);
			textView.setText(des);
			imgageLayout.addView(textView);
		}
	}
	
	private void addText(String des)
	{   
		textView = new TextView(context);
		String wordSizeStr = Common.getCacheStr(context,"MEDIACLIENT_SIZE");
		if (!Common.isNullOrBlank(wordSizeStr)){    
		    if ("1".equals(wordSizeStr)) {
		    	textView.setTextSize(Common.wordSize[0]);
		    }else if ("2".equals(wordSizeStr)){
		    	textView.setTextSize(Common.wordSize[1]);
		    }else if ("3".equals(wordSizeStr)){
		    	textView.setTextSize(Common.wordSize[2]);
		    }
		}else{
			textView.setTextSize(Common.wordSize[1]);
		}
		textView.setTextColor(Color.BLACK);
		textView.setLineSpacing(1.1f,1.3f);
		textView.setPadding(ImageUtils.dip2px(10), 2, ImageUtils.dip2px(10), ImageUtils.dip2px(5));
		textView.setText(Html.fromHtml(des));
		//textView.setText(des);
		textView.setMovementMethod(LinkMovementMethod.getInstance());   
        CharSequence text = textView.getText();   
        if (text instanceof Spannable) {   
            int end = text.length();   
            //Log.e("textView.getText",textView.getText().toString());
            Spannable sp = (Spannable) textView.getText();   
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);   
            SpannableStringBuilder style = new SpannableStringBuilder(text);   
            style.clearSpans();// should clear old spans   
   
            //循环把链接发过去             
            for (URLSpan url : urls) {   
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL());   
                style.setSpan(myURLSpan, sp.getSpanStart(url),   
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }   
            textView.setText(style);   
        }
        
		textArrayList.add(textView);
		contentLinearLayout.addView(textView);
		Animation anim = AnimationUtils.loadAnimation(textView.getContext(),R.anim.fade);
		textView.startAnimation(anim);
	}

	private static class MyURLSpan extends ClickableSpan {   
        private String mUrl;   
   
        MyURLSpan(String url) {   
            mUrl = url;   
        }   
        
        @Override
    	public void updateDrawState(TextPaint ds) {
    	    ds.setColor(Color.parseColor("#1f376d"));
    	    ds.setUnderlineText(false); //去掉下划线
    	}
   
        @Override   
        public void onClick(View view) {   
             Intent intent=new Intent();
			 Bundle bundle = new Bundle();
			 bundle.putString("url",mUrl);
			 intent.putExtras(bundle);
			 intent.setClass(context, BrowserActivity.class);
			 context.startActivity(intent);
        }   
    }   
}
