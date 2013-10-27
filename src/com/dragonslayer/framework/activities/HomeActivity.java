package com.dragonslayer.framework.activities;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.dragonslayer.framework.R;
import com.dragonslayer.framework.activities.widget.DialogExpandView;

public class HomeActivity extends MenuActivity {

	public static Bitmap imageTop;
	public static Bitmap imageDown;
	DialogExpandView eV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hideTitleBar = true;
		inicializarGUI(R.layout.activity_home, true);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}
