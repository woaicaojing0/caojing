package com.cj.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.baidu.location.a.i;
import com.cj.sqlite.DBHelper;
import com.cj.weathbean.CityInfo;
import com.cj.weathbean.City_info;
import com.google.gson.Gson;

public class WeatherCityDao {
	public WeatherCityDao(Context context) {
		this.mcContext = context;
	}

	private static Context mcContext;
	private static DBHelper dbHelper;
	private static String URL = "https://api.heweather.com/x3/citylist?search=allchina&key=";
	private static String APIKey = "59ef8e748dbb4a5cb0f1af7c6f43f2ae";

	public List<String> getWeatherCityList() {
		List<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append(URL + APIKey);
		dbHelper = new DBHelper(mcContext, null, null, 1);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query("WeatherCity",
					new String[] { "WeatherCity_Name" }, null, null, null,
					null, null);
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					result.add(cursor.getString(cursor
							.getColumnIndex("WeatherCity_Name")));
				}
			} else {
				HttpGet httpGet = new HttpGet(sb.toString());
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					Gson gson = new Gson();
					City_info info = gson.fromJson(strResult, City_info.class);
					for (int i = 0; i < info.getCity_info().size(); i++) {
						String city = info.getCity_info().get(i).getCity();
						result.add(city);
						ContentValues cv = new ContentValues();
						cv.put("WeatherCity_Name", city);
						cv.put("isselect", "0");
						db.insert("WeatherCity", null, cv);
					}
				}

			}

		} catch (Exception e) {
			result.add("0");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			Log.e("Error", "*****=" + sw.getBuffer().toString());
		}
		return result;

	}

	public static List<String> getSelectedCityName() {
		List<String> result = new ArrayList<String>();
		if (dbHelper == null) {
			dbHelper = new DBHelper(mcContext, null, null, 1);
		} else {
		}
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query("SelectedCity",
				new String[] { "CityName" }, null, null, null, null, null);
		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				result.add(cursor.getString(cursor.getColumnIndex("CityName")));
			}
		} else {

		}
		return result;
	}

	public static int InsertSelectedCityName(String name) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(mcContext, null, null, 1);
		} else {
		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("CityName", name);
		return (int) db.insert("SelectedCity", null, contentValues);
	}

	public static boolean GetSelectedCity(String cityName) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(mcContext, null, null, 1);
		} else {

		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String sql = "select count(*)as num from SelectedCity where CityName ='"
				+ cityName + "'";
		Cursor i = db.rawQuery(sql, null);
		int num = 0 ;
		while(i.moveToNext())
		{
			num =i.getInt(i.getColumnIndex("num"));
		}
		if(num !=0)
		{
			return true;
		}else {
			return false;
		}
	}
}
