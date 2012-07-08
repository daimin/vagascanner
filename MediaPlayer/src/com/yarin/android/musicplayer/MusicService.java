package com.yarin.android.musicplayer;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

public class MusicService extends Service {
	
	public  MediaPlayer player;
	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void playMusic(long arg3) throws IllegalArgumentException, IllegalStateException, IOException{
		player=new MediaPlayer();
		Uri uri = Uri.withAppendedPath(
		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		String.valueOf(arg3));
		player.release();
		player=MediaPlayer.create(this, uri);
		player.start();
		}
	public void onDestory(){
		super.onDestroy();
		player.stop();
	}
	

}
