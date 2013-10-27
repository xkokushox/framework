package com.dragonslayer.framework.widget;

import android.app.Activity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.dragonslayer.framework.R;
import com.dragonslayer.framework.SplashScreenActivity;
import com.dragonslayer.framework.system.Repeater;
import com.dragonslayer.framework.util.LogUtils;
import com.dragonslayer.framework.util.ViewUtils;

public class ImageLoader {
	protected Activity ctx = null;
	protected ImageView imagen = null;
	protected int numImagenes = 0;
	private Repeater repeater = null;
	protected int imgActual = 1;

	public ImageLoader(Activity ctx, int numImagenes, boolean iniciar) {
		this.ctx = ctx;
		this.numImagenes = numImagenes;

		imagen = new ImageView(ctx);
		imagen.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		imagen.setBackgroundResource(R.color.trans);

		if (iniciar) {
			iniciar();
		}
	}

	public ImageView getImageView() {
		return imagen;
	}

	public void detener() {
		imgActual = 1;
		if (repeater != null) {
			repeater.detener();
			repeater.release();
			repeater = null;
		}
	}

	public void iniciar() {
		detener();

		repeater = new Repeater(((SplashScreenActivity) ctx).mHandler, 0.1f,
				"imgLoader", true, new Runnable() {
					@Override
					public void run() {
						try {
							int resIdImg = 0;

							switch (imgActual) {
							case 1:
								resIdImg = R.drawable.loader1;
								break;
							case 2:
								resIdImg = R.drawable.loader2;
								break;
							case 3:
								resIdImg = R.drawable.loader3;
								break;
							case 4:
								resIdImg = R.drawable.loader4;
								break;
							case 5:
								resIdImg = R.drawable.loader5;
								break;
							case 6:
								resIdImg = R.drawable.loader6;
								break;
							case 7:
								resIdImg = R.drawable.loader7;
								break;
							case 8:
								resIdImg = R.drawable.loader8;
								break;
							case 9:
								resIdImg = R.drawable.loader9;
								break;
							}

							ViewUtils.releaseView(imagen);
							imagen.setImageResource(resIdImg);

							++imgActual;
							if (imgActual > numImagenes) {
								imgActual = 1;
							}
						} catch (Error err) {
							LogUtils.log(err);
						}
					}
				});
	}
}