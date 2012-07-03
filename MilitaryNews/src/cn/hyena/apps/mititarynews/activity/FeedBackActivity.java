package cn.hyena.apps.mititarynews.activity;

import cn.hyena.apps.mititarynews.R;

import cn.hyena.apps.utils.LogUtils;
import cn.hyena.apps.view.NewsContentView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class FeedBackActivity extends BaseActivity {
	//Views
	private EditText contentEt;
	private EditText emailEt;
	private Button sendBtn;
	
	private Context mContext;
	private String url;
		
	protected static final String TAG = "FeedBackActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mContext = FeedBackActivity.this;
    	setContentView(R.layout.feedback_activity);
        super.onCreate(savedInstanceState);
    }

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		this.contentEt  = (EditText) findViewById(R.id.contentEt);
		this.emailEt = (EditText) findViewById(R.id.emailEt);
		this.sendBtn = (Button) findViewById(R.id.sendBtn);
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
	}
	
	public int sendMailByJavaMail() {  
	        Email m = new Mail("wcfXXXX@gmail.com", "XXXXX");  
	        m.set_debuggable(true);  
	        String[] toArr = {"18170000@qq.com"};   
	        m.set_to(toArr);  
	        m.set_from("18170000@qq.com");  
	        m.set_subject("This is an email sent using icetest from an Android device");  
	        m.setBody("Email body. test by Java Mail");  
	        try {  
	            //m.addAttachment("/sdcard/filelocation");   
	            if(m.send()) {   
	            	LogUtils.i(TAG,"Email was sent successfully.");        
	            } else {  
	            	LogUtils.i(TAG,"Email was sent failed.");  
	            }  
	        } catch (Exception e) {  
	            // Toast.makeText(MailApp.this,  
	            // "There was a problem sending the email.",  
	            // Toast.LENGTH_LONG).show();  
	            LogUtils.e(TAG, "Could not send email", e);  
	        }  
	        return 1;  
	}  
}