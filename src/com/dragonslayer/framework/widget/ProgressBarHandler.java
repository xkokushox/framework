package com.dragonslayer.framework.widget;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

public class ProgressBarHandler {
	/**
	 * Propiedades
	 */
	
	protected ProgressBar barra = null;
	
	
	/**
	 * Constructores
	 */
	
	public ProgressBarHandler(){}
	
	
	public ProgressBarHandler(ProgressBar barra){
		this.barra = barra;
	}
	
	
	/**
	 * Metodos
	 */
	
	public void setProgressBar(ProgressBar barra){
		this.barra = barra;
	}
	
	
	public void mostrarBarra(){
		if (barra != null){
			barra.setVisibility(ProgressBar.VISIBLE);
			barra.setPadding(0, 4, 0, 2);
			barra.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 16));
		}
	}
	
	
	public void ocultarBarra(){
		if (barra != null){
			barra.setVisibility(ProgressBar.INVISIBLE);
			barra.setPadding(0, 0, 0, 0);
			barra.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0));
		}
	}
}
