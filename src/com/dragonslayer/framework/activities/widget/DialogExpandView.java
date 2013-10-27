package com.dragonslayer.framework.activities.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dragonslayer.framework.R;
import com.dragonslayer.framework.animation.ExpandAnimation;

public class DialogExpandView extends Dialog {

	public Activity ctx;
	ImageView imagenTop, imagenDown;
	RelativeLayout infoView;
	View view;
	ExpandAnimation animTop;
	ExpandAnimation animBottom;

	// ValueAnimator bounceAnim = null;

	public DialogExpandView(Activity context, Bitmap top, Bitmap down) {
		super(context);
		ctx = context;
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.expand_dialog_view);
		// TODO Auto-generated constructor stub
	}

	public DialogExpandView(Activity context, int theme, Bitmap top, Bitmap down) {
		super(context, theme);
		
		view = View.inflate(context, R.layout.expand_dialog_view, null);
		imagenTop = (ImageView) view.findViewById(R.id.imgDialogTop);
		imagenDown = (ImageView) view.findViewById(R.id.imgDialogButtom);

		imagenTop.setImageBitmap(top);
		imagenDown.setImageBitmap(down);

		animateImages(down.getHeight(), 1000);
		
		LayoutParams paramB = (LayoutParams) imagenDown.getLayoutParams();
		paramB.height = down.getHeight();
		paramB.width = down.getWidth();
		imagenDown.setLayoutParams(paramB);

		LayoutParams params = (LayoutParams) imagenTop.getLayoutParams();
		params.height = top.getHeight();
		params.width = top.getWidth();
		imagenTop.setLayoutParams(params);

		setContentView(view);


		
	}

	public void animateImages(int height, int duration) {
		AnimationSet animSetTop = new AnimationSet(true);
		AnimationSet animSetButtom = new AnimationSet(true);
		Animation animTop = new TranslateAnimation(0, 0, 0, -height);
		Animation animButtom = new TranslateAnimation(0, 0, 0, height);
		animTop.setDuration(duration);
		animButtom.setDuration(duration);
		animTop.setFillAfter(true);
		animButtom.setFillAfter(true);
		animButtom.setFillBefore(true);
		animSetTop.addAnimation(animTop);
		animSetButtom.addAnimation(animButtom);
		imagenTop.clearAnimation();
		imagenDown.clearAnimation();
		animSetTop.setFillAfter(true);
		animSetButtom.setFillAfter(true);
		imagenTop.startAnimation(animSetTop);
		imagenDown.startAnimation(animSetButtom);

	}

//	public void reverseAnimateImages() {
//		AnimationSet animSetTop = new AnimationSet(true);
//		AnimationSet animSetButtom = new AnimationSet(true);
//		Animation animTop = new TranslateAnimation(0, 0, -infoHeight, 0);
//		Animation animButtom = new TranslateAnimation(0, 0, infoHeight, 0);
//		animTop.setDuration(800);
//		animButtom.setDuration(800);
//		animTop.setFillAfter(true);
//		animButtom.setFillAfter(true);
//		animButtom.setFillBefore(true);
//		animSetTop.addAnimation(animTop);
//		animSetButtom.addAnimation(animButtom);
//		imagenTop.clearAnimation();
//		imagenDown.clearAnimation();
//		animSetTop.setFillAfter(true);
//		animSetButtom.setFillAfter(true);
//		imagenTop.startAnimation(animSetTop);
//		imagenDown.startAnimation(animSetButtom);
//
//	}

}
