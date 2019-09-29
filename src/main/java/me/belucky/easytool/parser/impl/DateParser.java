/**
 * File Name: DateParser.java
 * Date: 2019-08-16 15:55:47
 */
package me.belucky.easytool.parser.impl;

import java.util.Calendar;
import java.util.Date;
import me.belucky.easytool.parser.AbstractParser;

/**
 * Description: 将字符串解析成日期
  *  支持如下格式：<ol>
 * <li>{2016-04-01|17:00:00}</li>
 * <li>{W4|17:00:00},往后最近的一个周四的17点</li>
 * <li>{D3|17:00:00},3天后的17点</li>
 * </ol>
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class DateParser extends AbstractParser<Date>{

	
	public Date handle(String input) {
		if(input == null || "".equals(input)){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		
		char[] arr = input.toCharArray();
		int cursor = 0;
		StringBuffer patt = null;
		int type = 0;   //1: 普通日期格式   2: 自定义日期格式
		int count = 0;    //解析次数
		for(char c : arr){
			if(c == '{'){
				cursor = 1;type = 0;
				patt = new StringBuffer();
				continue;
			}
			if(c == '|'){
				dataParser(type, calendar, patt);
				patt = new StringBuffer();
				count++;
				continue;
			}
			if(c == '}'){
				if(count == 0){
					//适配{2015-01-01}这种场景
					dataParser(type, calendar, patt);
					patt = new StringBuffer();
					count++;
				}
				String[] startTimeStr = patt.toString().split(":");
				if(startTimeStr.length == 3){
					calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTimeStr[0]));
					calendar.set(Calendar.MINUTE, Integer.valueOf(startTimeStr[1]));
					calendar.set(Calendar.SECOND, Integer.valueOf(startTimeStr[2]));
				}
				continue;
			}
			if(cursor == 1){
				if(type == 0){
					if(c >= '0' && c <= '9'){
						type = 1;
					}else if(c >= 'A' && c <= 'Z'){
						type = 2;
					}
				}
				patt.append(c);
			}
		}
		return calendar.getTime();
	}
	
	private void dataParser(int type, Calendar calendar, StringBuffer patt){
		if(type == 1){
			//解析普通日期格式
			String[] ss = patt.toString().split("-");
			calendar.set(Calendar.YEAR, Integer.valueOf(ss[0]).intValue());
			calendar.set(Calendar.MONTH, Integer.valueOf(ss[1]).intValue() - 1);
			calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(ss[2]).intValue());
		}else if(type == 2){
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			//解析自定义日期格式
			String s1 = patt.toString().substring(0,1);
			String s2 = patt.toString().substring(1);
			if("D".equals(s1)){
				//天
				calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(s2));
			}else if("W".equals(s1)){
				int w = Integer.valueOf(s2) + 1;
				if(w > week){
					calendar.add(Calendar.DAY_OF_YEAR, w - week);
				}else if(w < week){
					calendar.add(Calendar.DAY_OF_YEAR, 7 - week + w);
				}else if(w == week){
					//比较时分秒
				}
			}
		}
		type = 0;
	}

}
