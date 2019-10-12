/**
 * File Name: DateTimeUtils.java
 * Date: 2019-08-16 15:44:18
 */
package me.belucky.easytool.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 日期处理工具类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class DateTimeUtils {
	
	/**
	 * 获取日期格式化信息
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(Date date, String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 日期转换成字符串
	 * @param date
	 * @return
	 */
	public static String getDate(Date date){
		return getDateStr(date, "yyyy-MM-dd");
	}
	
	/**
	 * 日期转换成字符串
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date){
		return getDateStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取今天的字符串表示  yyyy-MM-dd
	 * @return
	 */
	public static String getToday() {
		return getDateStr(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 获取当前的时间
	 * @return
	 */
	public static String getDateTimeNow() {
		return getDateTime(new Date());
	}
	
	/**
	 * 获取当前的时分秒
	 * @return
	 */
	public static String getNowTime() {
		return getDateStr(new Date(), "HHmmss");
	}
	
	/**
	 * 获取最近的指定日期
	 * <p>
	 * 	例子：1. getEarliestDate(Calendar, -1, 20, 0, 0)
	 *   		如果现在是19点，则返回的是当天的20点；如果现在是21点，则返回的是下一天的20点
	 * @param currentDate
	 * @param dayOfWeek
	 * @param hourOfDay
	 * @param minuteOfHour
	 * @param secondOfMinute
	 * @return
	 */
	public static Calendar getEarliestDate(Calendar currentDate, int dayOfWeek, int hourOfDay, 
			int minuteOfHour, int secondOfMinute){
		int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_MONTH);
		int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
		int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
		int currentMinute = currentDate.get(Calendar.MINUTE);
		int currentSecond = currentDate.get(Calendar.SECOND);
		
		if(dayOfWeek == -1){
			dayOfWeek = currentDayOfWeek;
		}
		
		if(hourOfDay == -1){
			hourOfDay = currentHour;
		}
		
		if(minuteOfHour == -1){
			minuteOfHour = currentMinute;
		}
		
		boolean weekLater = false;
		if(dayOfWeek < currentDayOfWeek){
			//延迟
			weekLater = true;
		}else if(dayOfWeek == currentDayOfWeek){
			if(hourOfDay < currentHour){
				//延迟
				weekLater = true;
			}else if(hourOfDay == currentHour){
				if(minuteOfHour < currentMinute){
					//延迟
					weekLater = true;
				}else if(minuteOfHour == currentMinute){
					if(secondOfMinute < currentSecond){
						//延迟
						weekLater = true;
					}
				}
			}
		}
		if(weekLater){
			currentDate.set(Calendar.WEEK_OF_MONTH, currentWeekOfYear + 1);
		}
		
		currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentDate.set(Calendar.MINUTE, minuteOfHour);
		currentDate.set(Calendar.SECOND, secondOfMinute);
		
		return currentDate;
	}
	
	public static Calendar getEarliestDate(Calendar currentDate, int hourOfDay, 
			int minuteOfHour, int secondOfMinute){
		return getEarliestDate(currentDate, -1, hourOfDay, minuteOfHour, secondOfMinute);
	}
	
	public static Calendar getEarliestDate(Calendar currentDate, String firstRunPattern){
		String[] startTimeStr = firstRunPattern.split(":");
		return getEarliestDate(currentDate,
				Integer.valueOf(startTimeStr[0]),
				Integer.valueOf(startTimeStr[1]),
				Integer.valueOf(startTimeStr[2]));
	}
	
	public static long getDelay(Calendar date, String firstRunPattern){
		long currentDateLong = date.getTime().getTime();
		long earliestDateLong = getEarliestDate(date, firstRunPattern).getTime().getTime();
		return earliestDateLong - currentDateLong;
	}
	
	public static long getDelay(String firstRunPattern){
		return getDelay(Calendar.getInstance(), firstRunPattern);
	}
	/**
	 * 获取指定日期与现在的毫秒差
	 * @param expectDate
	 * @return
	 */
	public static long getDelay(Date expectDate){
		Calendar currentDate = Calendar.getInstance();
		return expectDate.getTime() - currentDate.getTime().getTime();
	}

}
