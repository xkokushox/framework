package com.dragonslayer.framework.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.dragonslayer.framework.system.Sys;
import com.dragonslayer.framework.util.LogUtils;

public class HttpWebClient {
	URL urlPost = null;
	String dataPost = "";
	boolean first = true;
	BufferedReader reader = null;
	HttpURLConnection conn;
	HttpClient httpClient = null;
	private int waitTime = 20000;
	String text = "";
	MultipartEntity reqEntity;

	public HttpWebClient(String url) {
		try {
			urlPost = new URL(url);
			LogUtils.log(url.toString());
		} catch (MalformedURLException e) {
			LogUtils.log("Url mal creada");
		}
	}

	public String httpMultipartPost(Bitmap image) {
		String text = "";

		try {
			HttpPost postRequest = new HttpPost(urlPost.toString());
			httpClient = new DefaultHttpClient();
			postRequest.addHeader("Authorization", "header");
			reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			image.compress(CompressFormat.PNG, 0, bos);
			byte[] data = bos.toByteArray();
			ByteArrayBody bab = new ByteArrayBody(data, "avatar.png");

			reqEntity.addPart("bio", new StringBody("body"));
			reqEntity.addPart("file", bab);
			postRequest.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();

			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			text = URLDecoder.decode(s.toString(), "UTF-8");

			LogUtils.log(text);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtils.log(e.toString());
			text = "TimeOut";
		}

		return text;
	}

	public void postData(String id, String value) {
		try {
			if (first)
				dataPost = URLEncoder.encode(id, "UTF-8") + "="
						+ URLEncoder.encode(value, "UTF-8");
			else
				dataPost += "&" + URLEncoder.encode(id, "UTF-8") + "="
						+ URLEncoder.encode(value, "UTF-8");
			first = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String postExecute(Context context) {
		String text = "";
		try {

			conn = (HttpURLConnection) urlPost.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(waitTime);
			conn.setRequestProperty("Authorization",
					"RV6M7&i|r[)806B9040%9UhG*.45Bu");

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			LogUtils.log(dataPost);
			wr.write(dataPost);
			wr.flush();

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line);
			}

			text = URLDecoder.decode(sb.toString(), "UTF-8");

			LogUtils.log(text);
		} catch (java.net.SocketTimeoutException e) {
			text = "TimeOut";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtils.log(e.toString());
		}
		conn.disconnect();

		return text;
	}

	public String getExecute(Context context) {
		String text = "";
		try {
			conn = (HttpURLConnection) urlPost.openConnection();
			conn.setRequestMethod("GET");

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String line = null;

				// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line);
			}

			text = sb.toString();
			LogUtils.log(text);
		} catch (java.net.SocketTimeoutException e) {
			text = "TimeOut";
		} catch (Exception e) {
			text = "TimeOut";
			LogUtils.log(e.toString());
		}

		return text;

	}

}
