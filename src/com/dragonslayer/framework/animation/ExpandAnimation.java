package com.dragonslayer.framework.animation;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class ExpandAnimation extends Animation {

	public ExpandAnimation() {
		// TODO Auto-generated constructor stub
	}

	public AnimationSet animationImage(int height, int duration) {
		AnimationSet animSet = new AnimationSet(true);
		Animation animTop = new TranslateAnimation(0, 0, 0, -height / 2);
		animTop.setDuration(duration);
		animTop.setFillAfter(true);
		animSet.addAnimation(animTop);
		
		return animSet;
		
	}
}
