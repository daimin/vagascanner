package com.yarin.android.musicplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AudioPlayer extends Activity {
	private boolean isPause;
	private boolean isplay = true;
	private boolean imagechange = true;
	MediaPlayer player;
	private ListView listView;
	private List<Map<String, String>> data;
	private int current;
	private TextView songname1;
	private static String songtitle;
	private static int songItem;
	private ImageButton astopbutton1, afrontbutton2, astartbutton3,
			anextbutton4;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audioplayer);
		songname1 = (TextView) this.findViewById(R.id.songname);
		astopbutton1 = (ImageButton) this.findViewById(R.id.imageButton1);
		afrontbutton2 = (ImageButton) this.findViewById(R.id.imageButton2);
		astartbutton3 = (ImageButton) this.findViewById(R.id.imageButton3);
		anextbutton4 = (ImageButton) this.findViewById(R.id.imageButton4);

		songtitle = getIntent().getStringExtra("songtitle");
		songItem = getIntent().getIntExtra("songItem", songItem);
		current = songItem;
		listView = (ListView) this.findViewById(R.id.listView2);
		// songname1.setText(songtitle);
		player = new MediaPlayer();
		listView.setVisibility(View.GONE);
		// 显示音乐播放列表
		generateListView();
		songname1.setText(data.get(current).get("name"));

		// 播放器监听器
		player.setOnCompletionListener(new MyPlayerListener());
		// 按钮实现
		astopbutton1.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (player.isPlaying()) {
					player.stop();
					player.reset();
					astartbutton3.setImageResource(R.drawable.start);
				}
			}
		});
		afrontbutton2.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				frontMusic();
			}
		});
		anextbutton4.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nextMusic();
			}
		});
		astartbutton3.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imagechange == true && isplay == true) {
					playMusic();
					astartbutton3.setImageResource(R.drawable.pause);
					imagechange = false;
				} else if (imagechange == true && isplay == false) {
					player.start();
					astartbutton3.setImageResource(R.drawable.pause);
					imagechange = false;
				} else if (imagechange == false) {
					player.pause();
					astartbutton3.setImageResource(R.drawable.start);
					imagechange = true;
				}
			}
		});
		

	}

	/**
	 * 查找文件路径中所有mp3文件
	 * 
	 * @param file
	 *            要找的目录
	 * @param list
	 *            用来装文件的List
	 */
	private void findAll(File file, List<File> list) {
		File[] subFiles = file.listFiles();
		if (subFiles != null)
			for (File subFile : subFiles) {
				if (subFile.isFile() && (subFile.getName().endsWith(".mp3")))
					list.add(subFile);
				else if (subFile.isDirectory())// 如果是目录
					findAll(subFile, list); // 递归
			}
	}

	private void generateListView() {
		List<File> list = new ArrayList<File>();

		// 获取sdcard中的所有歌曲
		findAll(Environment.getExternalStorageDirectory(), list);

		// 播放列表进行排序，字符顺序
		// Collections.sort(list);

		data = new ArrayList<Map<String, String>>();
		for (File file : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", file.getName());
			map.put("path", file.getAbsolutePath());
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				new String[] { "name" }, new int[] { R.id.songname });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new MyItemListener());
	}

	private final class MyItemListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			current = position;
			playMusic();
		}
	}

	private final class MyPlayerListener implements OnCompletionListener {
		// 歌曲播放完后自动播放下一首歌曲
		public void onCompletion(MediaPlayer mp) {
			nextMusic();
		}
	}

	public void playMusic() {

		try {
			// 重播
			player.reset();

			// 获取歌曲路径
			player.setDataSource(data.get(current).get("path"));

			// 缓冲
			player.prepare();

			// 开始播放
			player.start();

			// 显示歌名
			songname1.setText(data.get(current).get("name"));
			astartbutton3.setImageResource(R.drawable.start);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isplay = false;

	}

	public void nextMusic() {
		current = (current + 1) % data.size();
		if (player.isPlaying()) {
			player.stop();
			playMusic();
			astartbutton3.setImageResource(R.drawable.pause);
			imagechange = false;
		} else {
			playMusic();
			player.stop();
			astartbutton3.setImageResource(R.drawable.start);
			imagechange = true;
		}
		isplay = true;

	}

	public void frontMusic() {
		current = current - 1 < 0 ? data.size() - 1 : current - 1;
		if (player.isPlaying()) {
			player.stop();
			playMusic();
			astartbutton3.setImageResource(R.drawable.pause);
			imagechange = false;
		} else {
			playMusic();
			player.stop();
			astartbutton3.setImageResource(R.drawable.pause);
			imagechange = true;
		}
		isplay = true;
	}

	private void pause() {
		if (player != null && player.isPlaying()) {
			player.pause();
			isPause = true;
		}
	}

	private void resume() {
		if (isPause) {
			player.start();
			isPause = false;
		}
	}

}
