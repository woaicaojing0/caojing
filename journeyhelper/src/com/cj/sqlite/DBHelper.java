package com.cj.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static String DB_name = "WeatherInfo";
	private static String dB_table = "WeatherCity";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_name, factory, version);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public void onCreate(SQLiteDatabase db) {// interger primary key
												// autoincrement
		String sql = "create table "
				+ dB_table
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,WeatherCity_Name nvarchar(20),isselect nvarchar(2))";
		String sql2 = "create table SelectedCity(_id INTEGER PRIMARY KEY AUTOINCREMENT,CityName nvarchar(20))";
		db.execSQL(sql);
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �Զ����ɵķ������

	}

}
