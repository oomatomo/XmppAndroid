package com.oomatomo.android.service;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.oomatomo.android.Controller.MessageCheck;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class XmppService {

	/**
	 * Log�ł̃^�O
	 */
	public static final String TEST = "test";
	
	/**
	 * GTalk�ł̐ݒ�
	 */
	public static final String HOST = "talk.google.com";
	public static final int PORT = 5222;
	public static final String SERVICE_NAME = "gmail.com";
	private XMPPConnection connection;
    private Chat chat = null;
	private boolean isRuning;
    /**
     * gmail�̃A�J�E���g
     */
    private String user ="";
    private String password = "";
    private String partner = "";
    /**
     * ��M���ă��b�Z�[�W�̏����N���X
     */
    private MessageCheck msgCheck = new MessageCheck();
    private Context context;  
    /**
     * �R���X�g���N�^
     */
    public XmppService(Context con){
    	context = con;
    }
    
	/**
	 * XMPP�T�[�o�Ƃ̃R�l�N�V�������m��������
	 */
	public void setConnect(){
		Log.d(TEST,"XMPP / setConnect()");
		//���m�点�@�\�\��		
		//Toast.makeText(this, "xmppService start", Toast.LENGTH_SHORT).show();
		try{
			ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST, PORT, SERVICE_NAME);
			connection = new XMPPConnection(connConfig);
		}catch (Exception e) {
			Log.d(TEST,"XMPP / ConnectionError :"+e.toString());
		}
				
		//googletalk�ւ̐ڑ�
		try {
			connection.connect();
			connection.login(user, password);
		} catch (XMPPException ex) {
			Log.d(TEST,"XMPP / LoginError :"+ex.toString());
		}
	}
	
	/**
	 * ���O�C�����chat�̐ڑ�������
	 */
	public void openChat(){
		
		Log.d(TEST,"XMPP / openChat()");
		
		try{
	        Presence presence = new Presence(Presence.Type.available);
	        connection.sendPacket(presence);
	           
    	    ChatManager chatManager = connection.getChatManager();
    	    //���b�Z�[�W��M���̐ݒ�
    	    chat = chatManager.createChat(partner+"@gmail.com", new MessageListener() {
    			public void processMessage(Chat chats, Message msg) {
    				// TODO �����������ꂽ���\�b�h�E�X�^�u
    				//Log.d(TEST,"XMPP / processMessage:"+chats.getParticipant()+" : "+msg.getBody()); 
    				//�󂯎�������b�Z�[�W����͂���
    				String result = msgCheck.decision(context,msg.getBody());
     				try {
     					if(result != null){
     						chat.sendMessage(result);
     					}
					} catch (XMPPException e) {
						// TODO �����������ꂽ catch �u���b�N
						Log.d(TEST, e.getLocalizedMessage());
					}
     				Log.d(TEST, "XMPP / processMessage :"+result.toString());
    			}
    		});
    	    
    	    //���b�Z�[�W���M
    	    chat.sendMessage("�ڑ����� testmessage");
    	    this.isRuning = true;
		}catch(XMPPException ex){
			Log.d(TEST,"XMPP / "+ex.toString());			
		}
	}
	
	/**
	 * �`���b�g��߂�
	 */
	public void closeChat() {
		//�ؒf
		Log.d(TEST,"XMPP / closeChat()");
		this.connection.disconnect();
		this.isRuning = false;
	}
	
	/**
	 * ���b�Z�[�W���M
	 * @param msg ���M���������b�Z�[�W
	 * @return�@����or���s
	 */
	public boolean sendMessage(String msg) {
		if(this.isRuning && msg != null){
			//���b�Z�[�W���M
			try {
				Log.d(TEST,"XMPP / sendMessage:" + msg);
				chat.sendMessage(msg);
				Log.d(TEST,"XMPP / sendMessage: SUCCESS");
			} catch (XMPPException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
				return false;
			}
			return true;
		}
		Log.d(TEST,"XMPP / isRuning false");	
		return false;
	}
}
