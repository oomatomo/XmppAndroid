package com.oomatomo.android;

import com.oomatomo.android.service.AllService;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class Android_PermissionActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Log.d("test", "Srvice Start");
        Intent intent = new Intent(this, AllService.class);
        //startService(intent);
        //ˆÈ‰º@AlarmManager‚Å‚Ìservice‚ÌÄ‹N“®
        PendingIntent pendingIntent= PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //6•ª‚¿‚å‚Á‚Æ‚ÅÄ‹N“®
        am.setInexactRepeating (AlarmManager.RTC,System.currentTimeMillis(),600000,pendingIntent);
    }
}