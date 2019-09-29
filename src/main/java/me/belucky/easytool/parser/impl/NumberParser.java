/**
 * File Name: NumberParser.java
 * Date: 2019-08-15 17:02:27
 */
package me.belucky.easytool.parser.impl;

import me.belucky.easytool.parser.AbstractParser;

/**
 * Description: 解析字符串,转换成对应的数值
 * test case： 
 * <pre>
 * IParse<Long> parser = new NumberParser();
 * long v = parser.parse("${1d12h30m15s}");
 * System.out.println(v);
 *		
 * long expect = 24*60*60*1000+12*60*60*1000+30*60*1000+15*1000;
 * System.out.println(expect);
 * Assert.assertEquals(expect, v);
 * </pre>
 * @author shenzulun
 * @date 2019-08-15
 * @version 1.0
 */
public class NumberParser extends AbstractParser<Long>{

	public Long handle(String input) {
		long period = 0;
		long tmp = 0;
		int cursor = 0;
		char[] periodArr = input.toCharArray();
		for(char c : periodArr){
			if(c == '$'){
				cursor = 1;		//准备替换标志
				continue;
			}else if(c == '}'){
				break;
			}else if(cursor == 1) {
				if(c == '{'){
					//确定为${开始的字符
					cursor = 2;
					continue;
				}else{
					//$后面不是跟着{,回退标志
					cursor = 0;
					continue;
				}
			}else if(cursor >= 2){
				//核心逻辑
				if(c >= '0' && c <= '9'){
					//数字
					if(cursor == 2){
						tmp = (long)c - 48;
					}else{
						tmp = tmp * 10 + (long)c - 48;
					}
					cursor++;
				}else {
					switch(c) {
						case 'd' : 
							period += tmp * 24 * 60 * 60 * 1000;
							break;
						case 'h' :
							period += tmp * 60 * 60 * 1000;
							break;
						case 'm' :
							period += tmp * 60 * 1000;
							break;
						case 's' :
							period += tmp * 1000;
							break;
						case 'w' :
							period += tmp * 7 * 24 * 60 * 60 * 1000;
							break;
						case 'p' :
							period += tmp;
							break;
					}
					tmp = 0;
					cursor = 2;
					continue;
				}
			}
		}
		return period;
	}

}
