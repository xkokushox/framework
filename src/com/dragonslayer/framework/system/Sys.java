package com.dragonslayer.framework.system;

import com.dragonslayer.framework.util.LogUtils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Sys {

	public static String IMEI = null;
	public static final int TIMEOUT = 30;

	public static String getAppId(Context ctx) {
		return ctx.getPackageName();
	}

	public static String getIMEI(Context ctx) {
		if (IMEI == null) {
			String imei = null;

			try {
				imei = ((TelephonyManager) ctx
						.getSystemService(Context.TELEPHONY_SERVICE))
						.getDeviceId();

				if (imei == null) {
					imei = "";
				}
			} catch (Exception e) {
				imei = "";
				LogUtils.log(e);
			}

			IMEI = imei;
		}

		LogUtils.log("IMEI: " + IMEI);
		return IMEI;
	}
}