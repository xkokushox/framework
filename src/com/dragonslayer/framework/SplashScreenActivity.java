package com.dragonslayer.framework;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;

import com.dragonslayer.framework.activities.HomeActivity;
import com.dragonslayer.framework.activities.MenuActivity;
import com.dragonslayer.framework.util.ImageUtil;

public class SplashScreenActivity extends MenuActivity {

	View splashLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideTitleBar = true;
		inicializarGUI(R.layout.inicio, false);
		splashLayout = (LinearLayout) findViewById(R.id.splashLayout);

		final CountDownTimer cont = new CountDownTimer(3000, 500) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				ocultarProgressDialog();	
				startActivity(new Intent(getApplicationContext(),
						HomeActivity.class));
				finish();
			}
		};
		mostrarProgressDialog();
		cont.start();

	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}