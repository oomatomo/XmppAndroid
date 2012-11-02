package com.oomatomo.android.Controller;

import java.io.IOException;

import android.accounts.AccountManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import com.oomatomo.android.R;

public class Device {
	
	private static String test ="test";
	
	/**
	 * バイブレーションを起動させる
	 * @param context
	 * @param secondString
	 */
	public static void setVibrator(Context context, String secondString){
		int second = Integer.parseInt(secondString);
		((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(second*1000);
		Log.d(test, "バイブレーションセット");
	}
	
	/**
	 * 時間をずらす
	 * @param second
	 */
	public static void setTime(String second){
		
		Log.d(test,second);
	}
	
	/**
	 * 音楽をならす
	 */
	public static void setMusic(Context context){
        // 音声を再生
		MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.fail);
        mMediaPlayer.start();
		
		Log.d(test,"music");
	}
	
	/**
	 * 音量を設定する	
	 * @param context
	 */
	public static void setVolume(Context context,String msg){
		AudioManager mAudio =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int volumeMax = mAudio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		int volume = Integer.parseInt(msg);		
		volume = volume > volumeMax ? volumeMax : Integer.parseInt(msg);
		mAudio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
	}
	
	/**
	 * モードを設定する
	 * @param context
	 */
	public static void setMode(Context context,String msg){
		AudioManager mAudio =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		/*
		 * AudioManager.RINGER_MODE_NORMAL　：　標準モード
		 * AudioManager.RINGER_MODE_VIBRATE　：　バイブモード
		 * AudioManager.RINGER_MODE_SILENT　：　サイレントモード
		 */
		if("NORMAL".equals(msg)){
			mAudio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
		if("VIBRATE".equals(msg)){
			mAudio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);			
		}
		if("SILENT".equals(msg)){
			mAudio.setRingerMode(AudioManager.RINGER_MODE_SILENT);			
		}
	}
	
	/**
	 * 壁紙を変更する
	 * @param context
	 */
	public static void setWall(Context context){
		WallpaperManager mWM = WallpaperManager.getInstance(context);
		try {
			mWM.setResource(R.drawable.musuka);
			Log.d(test, "壁紙変更");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} 
	}
	
	/**
	 * Androidの再起動　Root権限がないと出来ない
	 */
	public static void setReboot(Context context){
        //パワーマネージャを取得する　
		Log.d(test, "再起動");
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        pm.reboot("特になし");
        Log.d(test, "再起動");
	}
	
	/**
	 * GPS情報を取得する
	 * @param context
	 * @return
	 */
	public static String getGPS(Context context){
		LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		boolean state = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if(!state){
			
		}
		return "GPS取得失敗";
	}
	
	/**
	 * アカウント情報を取得する
	 * @param context
	 * @return
	 */
	public static String getAccount(Context context){
		    //アカウント情報取得
		AccountManager accountManager =AccountManager.get(context);
		android.accounts.Account[] accounts = accountManager.getAccounts();
		//アカウントの種類設定　getAccountsByType("com.google");
		String result ="";
		for (android.accounts.Account account : accounts) {			
			Log.d("test", "アカウントネーム"+account.name + account.type + account.describeContents());
			result +="name:"+account.name +"  type:"+ account.type+""; 
		}
		return result;
	}
}
