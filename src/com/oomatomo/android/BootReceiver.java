package com.oomatomo.android;

import com.oomatomo.android.service.AllService;
import com.oomatomo.android.service.XmppService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends android.content.BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intents) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
        Intent intent = new Intent(context, AllService.class);
        //�ȉ��@AlarmManager�ł�service�̍ċN��
        PendingIntent pendingIntent= PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        //6��������ƂōċN��
        am.setInexactRepeating (AlarmManager.RTC,System.currentTimeMillis(),600000,pendingIntent);
	}

}
