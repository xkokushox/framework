package com.dragonslayer.framework.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtils {
	
	public static void releaseView(View view){
		try{
			if (view != null){
				//Liberamos el bitmap del ImageView
				if (view instanceof ImageView){
					ImageView imageView = (ImageView)view;
					ImageUtil.releaseDrawable(imageView.getDrawable());
					imageView.setImageDrawable(null);
					imageView.invalidate();
					imageView = null;
				}
				
				//Liberamos el string del TextView
				if (view instanceof TextView){
					TextView textView = (TextView)view;
					textView.setText("");
					textView = null;
				}
				
				//Liberamos el bitmap del background
				ImageUtil.releaseDrawable(view.getBackground());
				view.setBackgroundDrawable(null);
				view.invalidate();
				view = null;
			}
		}catch(Exception e){
			LogUtils.log(e);
		}
	}
}