package com.oomatomo.android.Controller;

import android.content.Context;
import android.util.Log;

public class MessageCheck {

	public String decision(Context context,String msg){
		
		if(msg == null) return null;
		Log.d("test", "MessageCheck / "+msg);
		String[] msgArray = msg.split(","); 
		
		//�o�C�u���[�V����
		if("Vibrator".equals(msgArray[0])){
			Device.setVibrator(context,msgArray[1]);
			return "Vibrator ok";
		}		
		//���y
		if("Music".equals(msgArray[0])){
			Device.setMusic(context);
			return "Music ok";
		}
		//����
		if("Volume".equals(msgArray[0])){
			Device.setVolume(context, msgArray[1]);
			return "Volume ok";
		}
		//���[�h�ݒ�
		if("Mode".equals(msgArray[0])){
			Device.setMode(context, msgArray[1]);
			return "Mode ok";
		}
		//�ǎ�
		if("Wall".equals(msgArray[0])){
			Device.setWall(context);
			return "Wall ok";
		}
		//�ċN�� �ł��Ă��Ȃ�
		if("Reboot".equals(msgArray[0])){
			Device.setReboot(context);
			return "Reboot ok";
		}
		//GPS���擾
		if("Gps".equals(msgArray[0])){
			return null;
		}
		//�A�J�E���g
		if("Account".equals(msgArray[0])){
			return Device.getAccount(context);
		}		
		return null;
	}
}
