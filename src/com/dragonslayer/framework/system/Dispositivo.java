package com.dragonslayer.framework.system;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Window;

import com.dragonslayer.framework.util.LogUtils;

/**
 * Realiza operaciones relativas al dispositivo móvil
 * 
 * @author Angel López
 */
public class Dispositivo {

	private static String IMEI = null;

	public static int getStatusBarHeight(Activity ctx) {
		Rect rectgle = new Rect();
		Window window = ctx.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		return rectgle.top;
	}

	/**
	 * Obtiene la altura del dispositivo en orientación portrait menos el alto
	 * del status bar, en pixeles. Prácticamente es la altura neta del área que
	 * usará la aplicación (cuando el status bar está visible)
	 * 
	 * @param ctx
	 *            context requerido para acceder a estas propiedades
	 * @return la altura del dispositivo en orientación portrait menos el alto
	 *         del status bar, en pixeles. Prácticamente es la altura neta del
	 *         área que usará la aplicación (cuando el status bar está visible)
	 */
	public static int getHeightPortraitLessSBHeight(Activity ctx) {
		return getHeightPortrait(ctx) - getStatusBarHeight(ctx);
	}

	/**
	 * Obtiene el ancho de la pantalla en orientación portrait, en pixeles
	 * 
	 * @param ctx
	 *            contexto requerido para acceder a esta propiedad
	 * @return el ancho de la pantalla en orientación portrait, en pixeles
	 */
	public static int getWidthPortrait(Activity ctx) {
		Display display = ctx.getWindowManager().getDefaultDisplay();
		return Math.min(display.getWidth(), display.getHeight());
	}

	/**
	 * Obtiene el alto de la pantalla en orientación portrait, en pixeles
	 * 
	 * @param ctx
	 *            contexto requerido para acceder a esta propiedad
	 * @return el alto de la pantalla en orientación portrait, en pixeles
	 */
	public static int getHeightPortrait(Activity ctx) {
		Display display = ctx.getWindowManager().getDefaultDisplay();
		return Math.max(display.getWidth(), display.getHeight());
	}

	/**
	 * Obtiene el IMEI del dispositivo. El IMEI es un identificador único del
	 * dispositivo. Ningún otro dispositivo en el mundo cuenta con el mismo IMEI
	 * 
	 * @param ctx
	 *            contexto para acceder a esta propiedad
	 * @return IMEI del dispositivo
	 */
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
				LogUtils.log("Dispositivo",
						"Error al obtener el IMEI del dispositivo.");
				LogUtils.log(e);
			}

			IMEI = imei;
		}

		LogUtils.log("Dispositivo", "IMEI: " + IMEI);
		return IMEI;
	}
}
