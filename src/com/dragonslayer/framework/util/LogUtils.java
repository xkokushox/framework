package com.dragonslayer.framework.util;

import android.util.Log;

public class LogUtils {

	public static final boolean DEPURANDO = true;
	private static final boolean LOG_TO_FILE = false;

	public static void logToFile(String text) {
		Log.d("LOG_TO_FILE", text);
	}

	public static void logToFile(Throwable e) {
		try {
			StringBuffer sb = new StringBuffer(e.toString() + "\n");
			StackTraceElement[] stElements = e.getStackTrace();
			String newLine = "";

			for (StackTraceElement stElement : stElements) {
				sb.append(newLine);
				sb.append("\tat ");
				sb.append(stElement.toString());
				newLine = "\n";
			}

			logToFile(sb.toString());
		} catch (Exception ee) {
			e.printStackTrace();
		}
	}

	public static void log(String texto) {
		log("INFO", texto);
	}

	public static void log(String tag, String texto) {
		if (DEPURANDO) {

			Log.i(tag, texto);

			if (LOG_TO_FILE) {
				LogUtils.logToFile("<" + tag + ">:\t\t" + texto);
			}
		}
	}

	public static void log(Throwable e) {
		if (DEPURANDO) {
			e.printStackTrace();

			if (LOG_TO_FILE) {
				LogUtils.logToFile(e);
			}
		}
	}

}