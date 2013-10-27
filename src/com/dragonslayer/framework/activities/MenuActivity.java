package com.dragonslayer.framework.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/*
	 * Llenamos el menu con las opciones necesarias
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		onCreateOptionsMenuStatic(menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * La llamamos cuando la clase no extienda de una actividad
	 */
	public static void onCreateOptionsMenuStatic(Menu menu) {
	}

	@Override
	/**
	 * Realizamos cualquier operaci?n necesario al seleccionar una opci?n del men? principal
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		onOptionsItemSelectedStatic(item, this);
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Realizamos cualquier operaci?n necesario al seleccionar una opci?n del
	 * men? principal
	 */
	public static void onOptionsItemSelectedStatic(MenuItem item,
			Activity activity) {
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setColorActionBar(Drawable drawable) {
		if (!isHoneyComb())
			getSupportActionBar().setBackgroundDrawable(drawable);
		else
			getActionBar().setBackgroundDrawable(drawable);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void enableActionBar() {
		if (isHoneyComb())
			getActionBar().setHomeButtonEnabled(true);
		else
			getSupportActionBar().setHomeButtonEnabled(true);
	}

}
