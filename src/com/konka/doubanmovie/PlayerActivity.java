package com.konka.doubanmovie;

import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.konka.doubanmovie.PlayerService.LocalBinder;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		播放界面
 */
public class PlayerActivity extends Activity{
	public static final String EXTRA_VIDEO_URL = "extra_video_url";
	private static final String TAG = "PlayActivity";
	
	private PlayerService mService;
	private boolean mBound = false;
	
	private String mVideoUrl;
	private SurfaceView mVideoView;
	//private SeekBar mSeekBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_view);
		Debug.debug(TAG, "onCreate()");
		
		initView();
		
		Intent intent = this.getIntent();
		mVideoUrl = null;
		if(intent != null && 
				(mVideoUrl=intent.getStringExtra(EXTRA_VIDEO_URL)) != null){
			Debug.debug(TAG, "video url = " + mVideoUrl);
		}else{
			Debug.warn(TAG, "videoUrl is null");
			Toast.makeText(this, "参数不合法", Toast.LENGTH_LONG).show();
			this.finish();
		}
		
		//bind PlayerService
		Intent intentService = new Intent(this, PlayerService.class);
		bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		MediaPlayer mp = mService.getMediaPlayer();
		if(mp != null && mp.isPlaying()){
			mp.pause();
		}
	}
	
	@Override
	protected void onStop(){
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//unbind PlayerService
		if(mBound){
			unbindService(mConnection);
			mBound = false;
		}
	}
	
	private void initView(){
		mVideoView = (SurfaceView)findViewById(R.id.video_view);
		//mSeekBar = (SeekBar)findViewById(R.id.play_seekbar);
	}
	
	public void play(String mediaUrl){
		MediaPlayer mp = mService.getMediaPlayer();
		if(mp == null){
			Debug.warn(TAG, "play(), mediaPlayer is null");
			return;
		}
		
		mp.setDisplay(mVideoView.getHolder());
		mp.reset();
		try {
			mp.setDataSource(mediaUrl);
			mp.prepareAsync();
			mp.setLooping(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void continuePlay(){
		MediaPlayer mp = mService.getMediaPlayer();
		if(mp != null){
			mp.start();
		}else{
			Debug.warn(TAG, "startPlay() mediaPlayer is null");
		}
	}
	
	public void pausePlay(){
		MediaPlayer mp = mService.getMediaPlayer();
		if(mp != null){
			mp.pause();
		}else{
			Debug.warn(TAG, "pausePlay() mediaPlayer is null");
		}
	}
	
	public void stopPlay(){
		MediaPlayer mp = mService.getMediaPlayer();
		if(mp != null){
			mp.stop();
		}else{
			Debug.warn(TAG, "stopPlay() mediaPlayer is null");
		}
	}
	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mConnection = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Debug.debug(TAG, "onServiceConnected() componentName=" + name);
			LocalBinder binder = (LocalBinder)service;
			mService = binder.getService();
			mBound = true;
			play(mVideoUrl);
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Debug.debug(TAG, "onServiceDisconnected()");
			mBound = false;
		}
	};
}
