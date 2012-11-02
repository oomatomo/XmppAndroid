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
	 * �o�C�u���[�V�������N��������
	 * @param context
	 * @param secondString
	 */
	public static void setVibrator(Context context, String secondString){
		int second = Integer.parseInt(secondString);
		((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(second*1000);
		Log.d(test, "�o�C�u���[�V�����Z�b�g");
	}
	
	/**
	 * ���Ԃ����炷
	 * @param second
	 */
	public static void setTime(String second){
		
		Log.d(test,second);
	}
	
	/**
	 * ���y���Ȃ炷
	 */
	public static void setMusic(Context context){
        // �������Đ�
		MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.fail);
        mMediaPlayer.start();
		
		Log.d(test,"music");
	}
	
	/**
	 * ���ʂ�ݒ肷��	
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
	 * ���[�h��ݒ肷��
	 * @param context
	 */
	public static void setMode(Context context,String msg){
		AudioManager mAudio =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		/*
		 * AudioManager.RINGER_MODE_NORMAL�@�F�@�W�����[�h
		 * AudioManager.RINGER_MODE_VIBRATE�@�F�@�o�C�u���[�h
		 * AudioManager.RINGER_MODE_SILENT�@�F�@�T�C�����g���[�h
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
	 * �ǎ���ύX����
	 * @param context
	 */
	public static void setWall(Context context){
		WallpaperManager mWM = WallpaperManager.getInstance(context);
		try {
			mWM.setResource(R.drawable.musuka);
			Log.d(test, "�ǎ��ύX");
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} 
	}
	
	/**
	 * Android�̍ċN���@Root�������Ȃ��Əo���Ȃ�
	 */
	public static void setReboot(Context context){
        //�p���[�}�l�[�W�����擾����@
		Log.d(test, "�ċN��");
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        pm.reboot("���ɂȂ�");
        Log.d(test, "�ċN��");
	}
	
	/**
	 * GPS�����擾����
	 * @param context
	 * @return
	 */
	public static String getGPS(Context context){
		LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		boolean state = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if(!state){
			
		}
		return "GPS�擾���s";
	}
	
	/**
	 * �A�J�E���g�����擾����
	 * @param context
	 * @return
	 */
	public static String getAccount(Context context){
		    //�A�J�E���g���擾
		AccountManager accountManager =AccountManager.get(context);
		android.accounts.Account[] accounts = accountManager.getAccounts();
		//�A�J�E���g�̎�ސݒ�@getAccountsByType("com.google");
		String result ="";
		for (android.accounts.Account account : accounts) {			
			Log.d("test", "�A�J�E���g�l�[��"+account.name + account.type + account.describeContents());
			result +="name:"+account.name +"  type:"+ account.type+""; 
		}
		return result;
	}
}
