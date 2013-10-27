package com.dragonslayer.framework.system;

import android.os.Handler;

import com.dragonslayer.framework.util.LogUtils;

public class Repeater extends Thread{
	
	public Handler handler = null;
	private Runnable listener = null;
	
	/**
	 * Tiempo que espera para revisar si ya llegó al tiempo solicitado
	 */
	public float segundosEspera = 0;
	public String nombre = "";
	private boolean running = false;
	private boolean ejecutarAlInicio = false;
	
	
	public Repeater(Handler handler, long segundosEspera, boolean ejecutarAlInicio, Runnable listener){
		this(handler, segundosEspera, "", ejecutarAlInicio, listener);
	}
	
	
	public Repeater(Handler handler, long segundosEspera, String nombre, boolean ejecutarAlInicio, Runnable listener){
		this(handler, (float)segundosEspera, nombre, ejecutarAlInicio, listener);
	}

	
	public Repeater(Handler handler, float segundosEspera, String nombre, boolean ejecutarAlInicio, Runnable listener){
		this.nombre = nombre;
		this.handler = handler;
		this.listener = listener;
		this.segundosEspera = segundosEspera;
		this.ejecutarAlInicio = ejecutarAlInicio;
		running = true;
		
		//iniciamos el contador
		start();
	}
	
	
	public Repeater setSegundosEspera(long segundosEspera){
		this.segundosEspera = segundosEspera;
		return this;
	}
	
	
	public void detener(){
		running = false;
	}
	
	
	@Override
	public void run(){
		try{
			if (ejecutarAlInicio){
				handler.post(listener);
			}
			
			while(running){
				sleep((long)(segundosEspera * 1000));
						
				if (running){
					handler.post(listener);
				}
			}
		}catch(Exception e){
			LogUtils.log(e);
		}
	}
	
	
	public void release() {
		running = false;
		handler = null;
		listener = null;
		nombre = null;
		
		if (isAlive()){
			interrupt();
		}
	}
}