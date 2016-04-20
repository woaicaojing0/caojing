package com.cj.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;

import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.cj.robotbean.Result;
import com.cj.robotbean.ReturnMsg;
import com.google.gson.Gson;

public class HttpUtils {
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "7c05d8a991acb5de3f74a5832d5a1df6";
	private static String resultstring ="";
	public static ReturnMsg sendMssage(String msg) {
		ReturnMsg returnMsg = new ReturnMsg();
		String jsonresult = getresult(msg);
		Gson gson = new Gson();
		Result result = gson.fromJson(jsonresult, Result.class);
		returnMsg.setDate(new Date());
		returnMsg.setMsg(result.getText());
		returnMsg.setType(ReturnMsg.Type.INCOMING);
		return returnMsg;

	}

	/**
	 * 
	 * @param msg用户输入的内容
	 * @return 返回结果json格式
	 */
	public static String getresult(String msg) {
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = new String(baos.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String setParams(String msg) {
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info="
					+ URLEncoder.encode(msg, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return url;
	}

	public static String GetWeather(String city) {
		Parameters para = new Parameters();
		para.put("city", city);
		ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
				ApiStoreSDK.GET, para, new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
						Log.i("caojingtianqi", "onSuccess");
						resultstring= responseString;
					}

					@Override
					public void onComplete() {
						Log.i("sdkdemo", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.i("caojingtianqi", "onError, status: " + status);
						Log.i("caojingtianqi",
								"errMsg: " + (e == null ? "" : e.getMessage()));
						resultstring = responseString;
					}

				});
		return resultstring;

	}
}
