package com.oomatomo.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AllService extends Service {

	/**
	 * Logでのタグ
	 */
	public static final String TEST = "test";

	/**
     * サービスのコンテキスト
     */
    private Context context;
    private XmppService xmpp ;
    private GpsService gps;
    
    /**
     * 以下、サービスの
     */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public void onCreate(){
		Log.d(TEST,"Service / onCreate");
		context = getApplicationContext();
		xmpp = new XmppService(context);
		Log.d(TEST,"Service / 接続開始");
		xmpp.setConnect();
		Log.d(TEST,"Service / 接続完了");
		xmpp.openChat();
		gps = new GpsService(context, xmpp);
		//GPSの初期設定
		gps.initGPS();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
	    super.onStartCommand(intent, flags, startId);       
	    return START_STICKY;
	}

	@Override
	public void onStart(Intent intent , int startId){
		Log.d(TEST,"Service / onStart");
	}
	
	@Override
	public void onDestroy(){
		 xmpp.closeChat();
		 Log.d(TEST, "Service / onDestroy");
	}

}
