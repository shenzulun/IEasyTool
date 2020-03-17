/**
 * File Name: TextCompareUtilTest.java
 * Date: 2020-03-17 08:17:24
 */
package me.belucky.easytool.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-03-17
 * @version 1.0
 */
public class TextCompareUtilTest {
	
	private Map<String, String> bankAliasMap = new HashMap<String, String>();
	private TextCompareUtil tc = null;
	
	@Before
	public void init() {
		bankAliasMap.put("工行", "工商银行");
		bankAliasMap.put("农行", "农业银行");
		bankAliasMap.put("中行", "中国银行");
		bankAliasMap.put("建行", "建设银行");
		bankAliasMap.put("交行", "交通银行");
		bankAliasMap.put("招行", "招商银行");
		bankAliasMap.put("台行", "台州银行");
		bankAliasMap.put("农商", "农村商业");
		tc = new TextCompareUtil();
	}
	
	/**
	 * 数据清洗
	 * @param source
	 * @return
	 */
	public String dataClean(String source) {
		Set<String> keys = bankAliasMap.keySet();
		for(String key : keys) {
			source = source.replaceAll(key, bankAliasMap.get(key));
		}
		return source;
	}
	
	@Test
	public void test1() throws IOException {
		System.out.println(tc.calcMatchingRate("丹东农村商业银行股份有限公司合作区支行", dataClean("黄岩区农村信用合作银行西城支行")));
		System.out.println(tc.calcMatchingRate("浙江台州黄岩农村商业银行股份有限公司西城支行", dataClean("黄岩区农村信用合作银行西城支行")));
//		System.out.println(tc.calcMatchingRate("中国工商银行台州市椒江区支行椒北支行", dataClean("工行台州椒江椒北支行")));		
		
		System.out.println(tc.calcMatchingRate("上海农商银行惠南支行", dataClean("临海市农商银行城南支行")));
		System.out.println(tc.calcMatchingRate("浙江临海农村商业银行股份有限公司城南支行", dataClean("临海市农商银行城南支行")));
	}

}
