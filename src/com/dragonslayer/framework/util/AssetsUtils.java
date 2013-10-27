package com.dragonslayer.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class AssetsUtils {
	public static String getStringFromAsset(String asset, Context ctx) {
		BufferedReader in = null;
		StringBuilder buffer = new StringBuilder();
		
		try {
			in = new BufferedReader(new InputStreamReader(ctx.getAssets().open(asset)));
			String line;
			while ((line = in.readLine()) != null) {
				buffer.append(line).append('\n');
			}
		} catch (IOException e) {
			LogUtils.log(e);
		} finally {
			if (in != null){
				try{in.close();}catch(Exception ee){}
				in = null;
			}
		}
		
		return buffer.toString();
	}
	
	
	public static Bitmap getImageFromAsset(Context ctx, String asset) {
		try{
			return BitmapFactory.decodeStream(ctx.getAssets().open(asset));
		}catch(Exception e){
			LogUtils.log(e);	
		}
		
		return null;
	}
	
	
	public static Bitmap getImageFromAsset(Context ctx, String asset, int defaultImgResId) {
		Bitmap img = getImageFromAsset(ctx, asset);
		
		if (img == null){
			//Cargamos imagen default
			img = ImageUtil.drawableToBitmap(ctx.getResources().getDrawable(defaultImgResId));
		}
		
		return img; 
	}
	
	
	public static Drawable getDrawableFromAsset(Context ctx, String asset) {
		try{
			return ImageUtil.bitmapToDrawable(BitmapFactory.decodeStream(ctx.getAssets().open(asset)));
		}catch(Exception e){
			LogUtils.log(e);	
		}
		
		return null;
	}
	
	
	public static Drawable getDrawableFromAsset(Context ctx, String asset, int defaultImgResId) {
		Drawable img = getDrawableFromAsset(ctx, asset);
		
		if (img == null){
			//Cargamos imagen default
			img = ctx.getResources().getDrawable(defaultImgResId);
		}
		
		return img; 
	}
}