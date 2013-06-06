package com.konka.doubanmovie;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		音视频后台播放服务
 */
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener,
MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener,
MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{
	
	private static final String TAG = "PlayerService";
	
	//binder given to clients
	private final IBinder mBinder = new LocalBinder();
	
	private MediaPlayer mMediaPlayer;
	private AudioManager mAudioManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder extends Binder{
		PlayerService getService(){
			return PlayerService.this;
		}
	}
	
	public void initMediaPlayer(){
		Debug.debug(TAG, "initMediaPlayer()");
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnBufferingUpdateListener(this);
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		if(mMediaPlayer != null){
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		Debug.debug(TAG, "onCreate()");
		initMediaPlayer();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(mMediaPlayer != null){
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	public MediaPlayer getMediaPlayer(){
		return mMediaPlayer;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Debug.debug(TAG, "onPrepared()");
		//开始播放
		mp.start();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Debug.warn("PlayerService", "onError() what="+what);
		mp.release();
		mp = null;
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Debug.debug(TAG, "onCompletion");
		mp.stop();
	}
	
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		Debug.debug(TAG, "onBufferingUpdate(" + percent + ")" );
	}
	
	//
	public void initAudioManager(){
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC	, 
				AudioManager.AUDIOFOCUS_GAIN);
	}
	
	//maybe useless for douban movie.
	@Override
	public void onAudioFocusChange(int focusChange) {
		switch(focusChange){
		case AudioManager.AUDIOFOCUS_GAIN:
			//resume playback
			if(mMediaPlayer == null){
				initMediaPlayer();
			}else if( !mMediaPlayer.isPlaying() ){
				mMediaPlayer.start();
			}
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			//Lost focus for an unbounded amount of time: stop playback and release media player
			if( mMediaPlayer.isPlaying() ){
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
			break;
		 case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            // Lost focus for a short time, but we have to stop
            // playback. We don't release the media player because playback
            // is likely to resume
            if (mMediaPlayer.isPlaying()){
            	mMediaPlayer.pause();
            }
            break;
        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            // Lost focus for a short time, but it's ok to keep playing
            // at an attenuated level
            if (mMediaPlayer.isPlaying()){
            	mMediaPlayer.setVolume(0.1f, 0.1f);
            }
            break;
		default:
			break;
		}
	}

}
