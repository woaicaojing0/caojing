package com.cj.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cj.IInterFace.WeatherInterface;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class WeatherAsynctask extends AsyncTask<Object, Object, Object> {

	private static String URL = "http://api.heweather.com/x3/weather?city=";
	private static String APIKey = "59ef8e748dbb4a5cb0f1af7c6f43f2ae";
	private ProgressDialog dialog;
	private WeatherInterface weatherInterface;

	public WeatherAsynctask(ProgressDialog progressDialog,
			WeatherInterface weatherInterface) {
		this.dialog = progressDialog;
		this.weatherInterface = weatherInterface;
	}

	@Override
	protected void onPreExecute() {
		// TODO 自动生成的方法存根
		super.onPreExecute();
		dialog.show();
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO 自动生成的方法存根
		// String httpUrl = "http://apis.baidu.com/heweather/pro/attractions";
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String lasturl = URL + params[0].toString() + "&key=" + APIKey;

		try {
			URL url = new URL(lasturl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		// TODO 自动生成的方法存根
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO 自动生成的方法存根
		super.onPostExecute(result);
		dialog.dismiss();
		weatherInterface.GetWeatherInfo(result.toString());
	}

}
