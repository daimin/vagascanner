package com.yarin.android.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes.Name;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends Activity {
    /** Called when the activity is first created. */
	ListView mListView;
	String songtitle;
	TextView asongtitle;
	String songname=null;
	int songtotal;
	int songItem;





   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mListView=(ListView)this.findViewById(R.id.listView1);
        asongtitle=(TextView)this.findViewById(R.id.textView1);

        String[] projection = 
            { MediaStore.Audio.AudioColumns._ID, 
                    MediaStore.Audio.AudioColumns.ARTIST, 
                    MediaStore.Audio.AudioColumns.TITLE, 
                    MediaStore.Audio.AudioColumns.DURATION }; 
            Cursor c = getContentResolver().query( 
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, 
                    null, null); 
            songtotal=c.getCount();
            int[] musicList = new int[c.getCount()]; 
            int index = 0; 
            while (c.moveToNext()) 
            { 
                musicList[index] = c.getInt(0); 
                index++; 
            } 
            songname=MediaStore.Audio.AudioColumns.TITLE;
     
            startManagingCursor(c); 
            //String songName=MediaStore.Audio.AudioColumns.TITLE;
            String[] from = 
            {songname, 
                    MediaStore.Audio.AudioColumns.ARTIST, 
                    MediaStore.Audio.AudioColumns.DURATION }; 
            int[] to = 
            { R.id.textView1, R.id.textView2, R.id.textView3 }; 
            
            ListAdapter adapter = new SimpleCursorAdapter(this, 
                    R.layout.relative, c, from, to); 
            mListView.setAdapter(adapter); 
            
            
            mListView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {								    	
					Uri uri = Uri.withAppendedPath(
					    		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					    		String.valueOf(arg3));
					songItem=arg2;
					songtitle=uri.toString();
                    DisplayToast("—°÷–¡À"+Integer.toString(arg2+1));  
//                    setContentView(R.layout.audioplayer);
//                    
//					try{
//						playMusic(arg3);
//					}
//					catch (IllegalArgumentException e) {
//	                 // TODO Auto-generated catch block
//	            	e.printStackTrace();
//	            	} 
//					catch (IllegalStateException e) {
//	            	// TODO Auto-generated catch block
//	            		e.printStackTrace();
//	            	} 
//					catch (IOException e) {
//	            	// TODO Auto-generated catch block
//						e.printStackTrace();
//	            	}
					Intent intent=new Intent();
					
					intent.setClass(MusicPlayer.this, AudioPlayer.class);
					intent.putExtra("songtitle",songtitle);
					intent.putExtra("songItem", songItem);
					intent.putExtra("arg2", arg2);
					startActivityForResult(intent,songItem);
					MusicPlayer.this.finish();
		        }	
				});
    }
    public void DisplayToast(String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	
}
