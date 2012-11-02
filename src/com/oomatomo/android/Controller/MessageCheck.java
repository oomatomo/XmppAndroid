package com.oomatomo.android.Controller;

import android.content.Context;
import android.util.Log;

public class MessageCheck {

	public String decision(Context context,String msg){
		
		if(msg == null) return null;
		Log.d("test", "MessageCheck / "+msg);
		String[] msgArray = msg.split(","); 
		
		//バイブレーション
		if("Vibrator".equals(msgArray[0])){
			Device.setVibrator(context,msgArray[1]);
			return "Vibrator ok";
		}		
		//音楽
		if("Music".equals(msgArray[0])){
			Device.setMusic(context);
			return "Music ok";
		}
		//音量
		if("Volume".equals(msgArray[0])){
			Device.setVolume(context, msgArray[1]);
			return "Volume ok";
		}
		//モード設定
		if("Mode".equals(msgArray[0])){
			Device.setMode(context, msgArray[1]);
			return "Mode ok";
		}
		//壁紙
		if("Wall".equals(msgArray[0])){
			Device.setWall(context);
			return "Wall ok";
		}
		//再起動 できていない
		if("Reboot".equals(msgArray[0])){
			Device.setReboot(context);
			return "Reboot ok";
		}
		//GPSを取得
		if("Gps".equals(msgArray[0])){
			return null;
		}
		//アカウント
		if("Account".equals(msgArray[0])){
			return Device.getAccount(context);
		}		
		return null;
	}
}
