package com.cj.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtils {
	/**
	 * ��鵱ǰ�����Ƿ����
	 * 
	 * @param context
	 * @return
	 */

	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// ��ȡNetworkInfo����
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					System.out.println(i + "===״̬==="
							+ networkInfo[i].getState());
					System.out.println(i + "===����==="
							+ networkInfo[i].getTypeName());
					// �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * �жϵ�ǰ���������ڼ�<br>
	 * <br>
	 * 
	 * @param pTime
	 *            ��Ҫ�жϵ�ʱ��<br>
	 * @return dayForWeek �жϽ��<br>
	 * @Exception �����쳣<br>
	 */
	public static String dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		String result = "";
		switch (dayForWeek) {
		case 1:
			result = "����һ";
			break;
		case 2:
			result = "���ڶ�";
			break;
		case 3:
			result = "������";
			break;
		case 4:
			result = "������";
			break;
		case 5:
			result = "������";
			break;
		case 6:
			result = "������";
			break;
		case 7:
			result = "������";
			break;

		default:
			result = "ˢ��ʧ��";
			break;
		}
		return result;
	}
}
