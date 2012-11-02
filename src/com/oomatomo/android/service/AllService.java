package com.oomatomo.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AllService extends Service {

	/**
	 * Log�ł̃^�O
	 */
	public static final String TEST = "test";

	/**
     * �T�[�r�X�̃R���e�L�X�g
     */
    private Context context;
    private XmppService xmpp ;
    private GpsService gps;
    
    /**
     * �ȉ��A�T�[�r�X��
     */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	@Override
	public void onCreate(){
		Log.d(TEST,"Service / onCreate");
		context = getApplicationContext();
		xmpp = new XmppService(context);
		Log.d(TEST,"Service / �ڑ��J�n");
		xmpp.setConnect();
		Log.d(TEST,"Service / �ڑ�����");
		xmpp.openChat();
		gps = new GpsService(context, xmpp);
		//GPS�̏����ݒ�
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
