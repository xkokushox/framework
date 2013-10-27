package com.dragonslayer.framework.activities;

import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dragonslayer.framework.R;
import com.dragonslayer.framework.events.OnPressBackListener;
import com.dragonslayer.framework.util.ConnectionUtils;
import com.dragonslayer.framework.util.LogUtils;
import com.dragonslayer.framework.widget.ImageLoader;

public class MainActivity extends ActionBarActivity {
	protected ProgressDialog progressDialog = null;
	private Dialog alertProgressDialog = null;
	protected ImageLoader imageLoader = null;
	protected InputMethodManager imm = null;
	protected boolean showInFullScreen = false;
	protected boolean hideTitleBar = false;
	protected boolean isFirstGUIInicialization = true;
	public final Handler mHandler = new Handler();
	public static final Stack<Intent> ACTIVITIES = new Stack<Intent>();
	protected boolean addToActivitiesStack = true;
	public ActionBar actionBar = null;
	public static CountDownTimer timerLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.log("onCreate():" + getClass().getName());
		imageLoader = new ImageLoader(this, 9, false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtils.log("onStart():" + getClass().getName());
	}

	@Override
	protected void onStop() {
		LogUtils.log("onStop():" + getClass().getName());
		mHandler.removeCallbacksAndMessages(null);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		LogUtils.log("onDestroy():" + getClass().getName());

		imm = null;
		progressDialog = null;

		if (alertProgressDialog != null) {
			if (alertProgressDialog.isShowing()) {
				alertProgressDialog.dismiss();
			}

			alertProgressDialog = null;
		}

		if (imageLoader != null) {
			imageLoader = null;
		}

		// closeLogs(logInfoUtil);
		super.onDestroy();
		destroyReferences();
	}

	private Dialog getAlertProgressDialog() {
		if (alertProgressDialog == null) {
			alertProgressDialog = new Dialog(this,
					R.style.Theme_Dialog_Translucent);
			alertProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertProgressDialog.setCancelable(false);
			alertProgressDialog.setContentView(imageLoader.getImageView());
			createTimer();
		}

		return alertProgressDialog;
	}

	public void hideSoftKeyboard(View view) {
		if (imm == null) {
			imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		}

		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void hideSoftKeyboard(Activity activity, View view) {
		((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public void mostrarProgressDialog() {
		getAlertProgressDialog().show();
		imageLoader.iniciar();
	}

	public void mostrarProgressDialog(final OnPressBackListener listener) {
		getAlertProgressDialog().setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& alertProgressDialog.isShowing()) {
					listener.onPressBack();
					return true;
				}

				return false;
			}
		});

		mostrarProgressDialog();
	}

	public void ocultarProgressDialog() {
		if (alertProgressDialog != null) {
			alertProgressDialog.dismiss();
			alertProgressDialog.setOnKeyListener(null);
			imageLoader.detener();
			if (timerLoader != null)
				timerLoader.cancel();
		}
	}

	protected void inicializarGUI(int layoutResID, boolean hasActionBar) {
		if (hideTitleBar) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		} else if (showInFullScreen && isFirstGUIInicialization) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		if (!isHoneyComb())
			if (hasActionBar)
				getSupportActionBar().show();
			else
				getSupportActionBar().hide();

		setContentView(layoutResID);
		configValidations();
		isFirstGUIInicialization = false;
	}

	public void configValidations() {
	}

	public boolean isHoneyComb() {
		if (Build.VERSION.SDK_INT >= 11)
			return true;
		else
			return false;
	}

	public void startActivity(Activity act, Class actividad) {
		Intent activity = new Intent(act, actividad);
		startActivity(activity);
	}

	public void destroyReferences() {

	}

	public void createShorToast(int idTxt) {
		Toast.makeText(getApplicationContext(),
				getResources().getString(idTxt), Toast.LENGTH_SHORT).show();
	}

	public void createShorToast(String idTxt) {
		Toast.makeText(getApplicationContext(), idTxt, Toast.LENGTH_SHORT)
				.show();
	}

	public void createLongToast(int idTxt) {
		Toast.makeText(getApplicationContext(),
				getResources().getString(idTxt), Toast.LENGTH_LONG).show();
	}

	public boolean isNetworkAvailable() {

		boolean isNet = ConnectionUtils
				.isNetworkAvailable(getApplicationContext());

		if (isNet == false) {
			ocultarProgressDialog();
			showNetworkErrorToast();
		}

		return isNet;

	}

	public void createTimer() {
		timerLoader = new CountDownTimer(14000, 11000) {

			@Override
			public void onTick(long millisUntil) {
				// TODO Auto-generated method stub

				if (millisUntil <= 10000) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.alert_long_time),
							Toast.LENGTH_SHORT).show();
					LogUtils.log("se tardo mucho el loader");
				}
			}

			@Override
			public void onFinish() {
				if (alertProgressDialog != null)
					alertProgressDialog.setCancelable(true);
			}
		};

		timerLoader.start();
	}

	@SuppressLint("NewApi")
	public void setActionBarTitle(int title) {
		if (isHoneyComb())
			getActionBar().setTitle(getResources().getString(title));
		else
			getSupportActionBar().setTitle(getResources().getString(title));
	}

	public void showNetworkErrorToast() {
		Toast.makeText(getApplicationContext(),
				getResources().getString(R.string.network_error_conection),
				Toast.LENGTH_SHORT).show();
	}

	public void closeApp() {
		System.runFinalizersOnExit(true);
		System.exit(0);
	}

}