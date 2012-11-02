package com.oomatomo.android.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GpsService {

	/**
	 * Logでのタグ
	 */
	public static final String TEST = "test";
	private Context context;
	private XmppService xmpp = null;
	/**
	 * GPSの設定
	 */
	private LocationManager mLocationManager = null;
	// GPSの取得インターバル(分)
	private static final int LOCATION_INTERVAL = 10000;
	// GPSの距離の種類
	private static final float LOCATION_DISTANCE = 0;

	/**
	 * コンストラクタ
	 */
	public GpsService(Context con,XmppService xmp){
		context = con;
		xmpp = xmp;
	}
	/**
	 * GPSのリスナー
	 * @author oomagaritomohisa
	 *
	 */
	private class GpsLocationListener implements LocationListener {

		Location mLastLocation;
		private static final String TEST = "test";

		public GpsLocationListener(String provider) {
			Log.d(TEST, "GPS / LocationListener " + provider);
			mLastLocation = new Location(provider);
		}

		/**
		 * GPS情報の位置変更時呼び出される
		 */
		@Override
		public void onLocationChanged(Location location) {
			Log.d(TEST, "GPS / onLocationChanged: ");
			mLastLocation.set(location);
			String result = "GPS";
			result += "," + location.getLatitude();
			result += "," + location.getLongitude();
			// result += ",Altitude:" + location.getAltitude();
			
			Log.d(TEST, "GPS / messageBody :"+result);
			boolean reflag = xmpp.sendMessage(result);
			Log.d(TEST, "GPS / XMPPsendMessage　:"+reflag);
		}

		/**
		 * GPS機能が無効時、呼び出される
		 */
		@Override
		public void onProviderDisabled(String provider) {
			Log.d(TEST, "GPS / onProviderDisabled: " + provider);
		}

		/**
		 * GPS機能が有効時、呼び出される
		 */
		@Override
		public void onProviderEnabled(String provider) {
			Log.d(TEST, "GPS / onProviderEnabled: " + provider);
		}

		/**
		 * GPSのステータスが変更時、呼び出される上記は２つは初期に呼び出される
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(TEST, "GPS / onStatusChanged: " + provider);
		}

	}

	GpsLocationListener[] mLocationListeners = new GpsLocationListener[] {
			new GpsLocationListener(LocationManager.GPS_PROVIDER),
			new GpsLocationListener(LocationManager.NETWORK_PROVIDER) };

	/**
	 * GPSの設定
	 */
	private void initializeLocationManager() {
		Log.d(TEST, "GPS / initializeLocationManager");
		if (mLocationManager == null) {
			mLocationManager = (LocationManager)context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		}
	}

	/*
	 * 
	 */
	public void initGPS() {
		initializeLocationManager();
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[1]);
		} catch (java.lang.SecurityException ex) {
			Log.d(TEST, "GPS / fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TEST, "GPS / network provider does not exist, " + ex.getMessage());
		}
		Log.d(TEST, "GPS / mLocationListeners[1] ok");

		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[0]);
		} catch (java.lang.SecurityException ex) {
			Log.d(TEST, "GPS / fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TEST, "GPS / gps provider does not exist " + ex.getMessage());
		}
		Log.d(TEST, "GPS / mLocationListeners[0] ok");
	}
}
