package com.dragonslayer.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;

public class ExportLogUtil {

	File root = null;
	File file = null;
	FileWriter filewriter = null;
	BufferedWriter out = null;
	String logName ="";
	Process process = null;
	String  packageName="";
	String pathFile="";
	
	public ExportLogUtil(String packageName, String logName, String fileName) {
		root = Environment.getExternalStorageDirectory();
		pathFile=root.getAbsolutePath();//+"/ironbit/log";
		this.packageName=packageName;
		file = new File(pathFile, fileName);
		this.logName=logName;
	}

	
	public void createLogFile() {
		
		
		try {
			process = Runtime.getRuntime().exec("logcat -d "+packageName+" "+logName+":V *:S");
			filewriter = new FileWriter(file);
			out = new BufferedWriter(filewriter);
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			StringBuilder log = new StringBuilder();
			String line = "\n KOKUSHO";
			while ((line = bufferedReader.readLine()) != null) {
				log.append(line);
			}

			if (root.canWrite()) {
				out.write(log.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeLog() {
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch blockñ
			e.printStackTrace();
		}
	}

}
