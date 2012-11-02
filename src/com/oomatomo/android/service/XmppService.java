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
	 * Logでのタグ
	 */
	public static final String TEST = "test";
	
	/**
	 * GTalkでの設定
	 */
	public static final String HOST = "talk.google.com";
	public static final int PORT = 5222;
	public static final String SERVICE_NAME = "gmail.com";
	private XMPPConnection connection;
    private Chat chat = null;
	private boolean isRuning;
    /**
     * gmailのアカウント
     */
    private String user ="";
    private String password = "";
    private String partner = "";
    /**
     * 受信してメッセージの処理クラス
     */
    private MessageCheck msgCheck = new MessageCheck();
    private Context context;  
    /**
     * コンストラクタ
     */
    public XmppService(Context con){
    	context = con;
    }
    
	/**
	 * XMPPサーバとのコネクションを確率させる
	 */
	public void setConnect(){
		Log.d(TEST,"XMPP / setConnect()");
		//お知らせ機能表示		
		//Toast.makeText(this, "xmppService start", Toast.LENGTH_SHORT).show();
		try{
			ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST, PORT, SERVICE_NAME);
			connection = new XMPPConnection(connConfig);
		}catch (Exception e) {
			Log.d(TEST,"XMPP / ConnectionError :"+e.toString());
		}
				
		//googletalkへの接続
		try {
			connection.connect();
			connection.login(user, password);
		} catch (XMPPException ex) {
			Log.d(TEST,"XMPP / LoginError :"+ex.toString());
		}
	}
	
	/**
	 * ログイン後のchatの接続をする
	 */
	public void openChat(){
		
		Log.d(TEST,"XMPP / openChat()");
		
		try{
	        Presence presence = new Presence(Presence.Type.available);
	        connection.sendPacket(presence);
	           
    	    ChatManager chatManager = connection.getChatManager();
    	    //メッセージ受信時の設定
    	    chat = chatManager.createChat(partner+"@gmail.com", new MessageListener() {
    			public void processMessage(Chat chats, Message msg) {
    				// TODO 自動生成されたメソッド・スタブ
    				//Log.d(TEST,"XMPP / processMessage:"+chats.getParticipant()+" : "+msg.getBody()); 
    				//受け取ったメッセージを解析する
    				String result = msgCheck.decision(context,msg.getBody());
     				try {
     					if(result != null){
     						chat.sendMessage(result);
     					}
					} catch (XMPPException e) {
						// TODO 自動生成された catch ブロック
						Log.d(TEST, e.getLocalizedMessage());
					}
     				Log.d(TEST, "XMPP / processMessage :"+result.toString());
    			}
    		});
    	    
    	    //メッセージ送信
    	    chat.sendMessage("接続完了 testmessage");
    	    this.isRuning = true;
		}catch(XMPPException ex){
			Log.d(TEST,"XMPP / "+ex.toString());			
		}
	}
	
	/**
	 * チャットを閉める
	 */
	public void closeChat() {
		//切断
		Log.d(TEST,"XMPP / closeChat()");
		this.connection.disconnect();
		this.isRuning = false;
	}
	
	/**
	 * メッセージ送信
	 * @param msg 送信したいメッセージ
	 * @return　成功or失敗
	 */
	public boolean sendMessage(String msg) {
		if(this.isRuning && msg != null){
			//メッセージ送信
			try {
				Log.d(TEST,"XMPP / sendMessage:" + msg);
				chat.sendMessage(msg);
				Log.d(TEST,"XMPP / sendMessage: SUCCESS");
			} catch (XMPPException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return false;
			}
			return true;
		}
		Log.d(TEST,"XMPP / isRuning false");	
		return false;
	}
}
