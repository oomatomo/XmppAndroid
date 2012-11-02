package com.oomatomo.android.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GpsService {

	/**
	 * Log�ł̃^�O
	 */
	public static final String TEST = "test";
	private Context context;
	private XmppService xmpp = null;
	/**
	 * GPS�̐ݒ�
	 */
	private LocationManager mLocationManager = null;
	// GPS�̎擾�C���^�[�o��(��)
	private static final int LOCATION_INTERVAL = 10000;
	// GPS�̋����̎��
	private static final float LOCATION_DISTANCE = 0;

	/**
	 * �R���X�g���N�^
	 */
	public GpsService(Context con,XmppService xmp){
		context = con;
		xmpp = xmp;
	}
	/**
	 * GPS�̃��X�i�[
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
		 * GPS���̈ʒu�ύX���Ăяo�����
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
			Log.d(TEST, "GPS / XMPPsendMessage�@:"+reflag);
		}

		/**
		 * GPS�@�\���������A�Ăяo�����
		 */
		@Override
		public void onProviderDisabled(String provider) {
			Log.d(TEST, "GPS / onProviderDisabled: " + provider);
		}

		/**
		 * GPS�@�\���L�����A�Ăяo�����
		 */
		@Override
		public void onProviderEnabled(String provider) {
			Log.d(TEST, "GPS / onProviderEnabled: " + provider);
		}

		/**
		 * GPS�̃X�e�[�^�X���ύX���A�Ăяo������L�͂Q�͏����ɌĂяo�����
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
	 * GPS�̐ݒ�
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
