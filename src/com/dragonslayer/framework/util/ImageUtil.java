package com.dragonslayer.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

public class ImageUtil {

	public static int statusBarHeight;

	public static Bitmap drawableToBitmap(Drawable drawable) {
		return ((BitmapDrawable) drawable).getBitmap();
	}

	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	public static void releaseDrawable(Drawable drawable) {
		try {
			if (drawable != null) {
				drawable.setCallback(null);
				drawable = null;
			}
		} catch (Exception e) {
			LogUtils.log(e);
		}
	}

	public static Bitmap descargarImagen(String urlImagen) {
		Bitmap img = null;

		try {
			HttpGet httpRequest = new HttpGet(urlImagen);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);

			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			InputStream instream = bufHttpEntity.getContent();
			img = BitmapFactory.decodeStream(instream);
		} catch (Exception e) {
			LogUtils.log(e);
		}

		return img;
	}

	/**
	 * @note METODO NO PROBADO <Suceptible a errores>
	 */
	public static String guardarImagen(Activity ctx, Bitmap imagen,
			String nombreImg, Bitmap.CompressFormat tipo) {
		try {
			OutputStream fOut = null;
			File file = new File(Environment.getDataDirectory().toString(),
					nombreImg + tipo.name());
			fOut = new FileOutputStream(file);

			imagen.compress(tipo, 90, fOut);
			fOut.flush();
			fOut.close();

			return MediaStore.Images.Media.insertImage(
					ctx.getContentResolver(), file.getAbsolutePath(),
					file.getName(), file.getName());
		} catch (Exception e) {
			LogUtils.log(e);
		}

		return "";
	}

	public static String guardarImagen(Activity ctx, Bitmap imagen,
			String nombreImg) {
		return guardarImagen(ctx, imagen, nombreImg, Bitmap.CompressFormat.PNG);
	}

	/**
	 * @note METODO NO PROBADO <Suceptible a errores>
	 */
	public static Bitmap obtenerImagen(Activity ctx, String imgName) {
		Bitmap img = null;

		try {
			img = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(),
					Uri.parse(imgName));
		} catch (Exception e) {
			LogUtils.log(e);
		}

		return img;
	}

	public static Bitmap resizeImageToHeight(Bitmap bitmapOrg, int newHeight) {
		return resizeImage(bitmapOrg, newHeight * bitmapOrg.getWidth()
				/ bitmapOrg.getHeight(), newHeight);
	}

	public static Bitmap resizeImageToWidth(Bitmap bitmapOrg, int newWidth) {
		return resizeImage(bitmapOrg, newWidth,
				newWidth * bitmapOrg.getHeight() / bitmapOrg.getWidth());
	}

	public static Bitmap resizeImage(Bitmap bitmapOrg, int newWidth,
			int newHeight) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// createa matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap newBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height,
				matrix, true);
		bitmapOrg.recycle();

		return newBitmap;
	}

	public static Bitmap cropImage(Bitmap bitmapOrg, int toWidth, int toHeight) {
		Matrix matrix = new Matrix();
		matrix.postScale(1, 1);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, toWidth,
				toHeight, matrix, true);
		bitmapOrg.recycle();

		return resizedBitmap;
	}

	public static Bitmap getRoundedCornerImage(Bitmap bitmap, int roundPixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = roundPixels;

		paint.setAntiAlias(true);
		paint.setColor(0xff424242);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap mergeImages(Bitmap... imgs) {
		Bitmap newImg = null;

		if (imgs != null) {
			int width = 0;
			int height = 0;

			for (int a = 0; a < imgs.length; a++) {
				if (imgs[a].getWidth() > width) {
					width = imgs[a].getWidth();
				}

				if (imgs[a].getHeight() > height) {
					height = imgs[a].getHeight();
				}
			}

			newImg = Bitmap
					.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas comboImage = new Canvas(newImg);

			for (int a = 0; a < imgs.length; a++) {
				comboImage.drawBitmap(imgs[a], 0f, 0f, null);
			}
		}

		return newImg;
	}

	public static Bitmap mergeImagesToSize(int width, int heigth,
			Bitmap... imgs) {
		Bitmap img = null;
		// nos aseguramos que todas las arrayAdapter tengan el tama��o
		// especificado
		for (int a = 0; a < imgs.length; a++) {
			img = imgs[a];
			if (img.getWidth() != width || img.getHeight() != heigth) {
				imgs[a] = resizeImage(img, width, heigth);
			}
		}

		return mergeImages(imgs);
	}

	// Haces un screen shot de la pantalla para hacer el efecto de divisi�n de
	// pantalla

	public static Bitmap takeScreenShot(View view, boolean position) {
		
		view.getRootView();
		
		Bitmap b1 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(b1);
	    view.draw(canvas);
	    
		Bitmap b = null;
		
		int width = view.getWidth();
		int height = view.getHeight();
		
		if (position) {
			b = Bitmap.createBitmap(b1, 0, 0, width, height / 2);
		} else {
			b = Bitmap.createBitmap(b1, 0, (height / 2), width, height / 2);
		}

		return b;

	}
}