/**
 * File Name: TextCompareUtilTest.java
 * Date: 2020-03-17 08:17:24
 */
package me.belucky.easytool.util;

import java.io.IOException;
import java.util.LinkedHashMap;
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
	
	private Map<String, String> bankAliasMap = new LinkedHashMap<String, String>();
	private TextCompareUtil tc = null;
	
	@Before
	public void init() {
		//1. 全称替换
		bankAliasMap.put("黄岩区农村合作联社城关信用社", "浙江台州黄岩农村商业银行股份有限公司清算中心");
		bankAliasMap.put("黄岩区农村信用合作联社城关信用社", "浙江台州黄岩农村商业银行股份有限公司清算中心");
		
		bankAliasMap.put("椒江区农村合作银行", "浙江台州椒江农村商业银行股份有限公司清算中心");
		bankAliasMap.put("农业银行路桥杨府庙支行", "中国农业银行股份有限公司台州分行清算中心");
		bankAliasMap.put("黄岩区农村合作联社城关信用社", "浙江台州黄岩农村商业银行股份有限公司清算中心");
		
		bankAliasMap.put("台州银行总行营业部", "台州银行股份有限公司");
		bankAliasMap.put("台州泰隆商业银行\\(泰隆城市信用社石曲支行\\)", "浙江泰隆商业银行清算中心");
		bankAliasMap.put("中国农业银行洪家分行", "中国农业银行股份有限公司台州分行清算中心");
		
		//2. 去除省市区关键字
		bankAliasMap.put("\\(", "");
		bankAliasMap.put("\\)", "");
		bankAliasMap.put("省", "");
		bankAliasMap.put("市", "");
		bankAliasMap.put("区", "");
		bankAliasMap.put("县", "");
		bankAliasMap.put("支行营业部", "支行");
		bankAliasMap.put("分支行", "支行");
		
		//3. 行名简称
		bankAliasMap.put("工行", "工商银行");
		bankAliasMap.put("农行", "农业银行");
		bankAliasMap.put("中行", "中国银行");
		bankAliasMap.put("建行", "建设银行");
		bankAliasMap.put("交行", "交通银行");
		bankAliasMap.put("招行", "招商银行");
		bankAliasMap.put("台行", "台州银行");
		bankAliasMap.put("农商", "农村商业");
		bankAliasMap.put("农村合作", "农村商业");
		bankAliasMap.put("农村信用合作联社", "农村商业银行");

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
		System.out.println(tc.calcMatchingRate(dataClean("丹东农村商业银行股份有限公司合作区支行"), dataClean("黄岩区农村信用合作银行西城支行")));
		System.out.println(tc.calcMatchingRate(dataClean("浙江台州黄岩农村商业银行股份有限公司西城支行"), dataClean("黄岩区农村信用合作银行西城支行")));
		//System.out.println(tc.calcMatchingRate(dataClean("中国工商银行台州市椒江区支行椒北支行"), dataClean("工行台州椒江椒北支行")));		

		System.out.println(tc.calcMatchingRate(dataClean("上海农商银行惠南支行"), dataClean("临海市农商银行城南支行")));
		System.out.println(tc.calcMatchingRate(dataClean("浙江临海农村商业银行股份有限公司城南支行"), dataClean("临海市农商银行城南支行")));

		System.out.println(tc.calcMatchingRate(dataClean("浙江省农村信用社联合社"), dataClean("黄岩区农村信用合作联社高桥信用社")));
		System.out.println(tc.calcMatchingRate(dataClean("浙江台州黄岩农村商业银行股份有限公司高桥支行"), dataClean("黄岩区农村信用合作联社高桥信用社")));

		System.out.println(tc.calcMatchingRate(dataClean("中国工商银行股份有限公司台州椒江支行阳光分理处"), dataClean("工商银行温岭支行万昌分理处")));
		System.out.println(tc.calcMatchingRate(dataClean("中国工商银行股份有限公司温岭支行万昌分理处"), dataClean("工商银行温岭支行万昌分理处")));

		System.out.println(tc.calcMatchingRate(dataClean("台州银行股份有限公司黄岩支行"), dataClean("中国民生银行股份公司黄岩支行")));
		System.out.println(tc.calcMatchingRate(dataClean("中国民生银行股份有限公司台州黄岩小微企业专营支行"), dataClean("中国民生银行股份公司黄岩支行")));

		System.out.println(tc.calcMatchingRate(dataClean("浙江台州黄岩农村商业银行股份有限公司营业部"), dataClean("台州银行总行营业部")));
		System.out.println(tc.calcMatchingRate(dataClean("台州银行股份有限公司"), dataClean("台州银行总行营业部")));

		System.out.println(tc.calcMatchingRate(dataClean("中国农业银行台州市章安支行"), dataClean("农行台州市椒江支行")));
		System.out.println(tc.calcMatchingRate(dataClean("中国农业银行台州椒江区支行"), dataClean("农行台州市椒江支行")));
		
		System.out.println(tc.calcMatchingRate(dataClean("中国建设银行临海支行营业部"), dataClean("中国建设银行椒江支行营业部")));
		System.out.println(tc.calcMatchingRate(dataClean("中国建设银行台州椒江支行"), dataClean("中国建设银行椒江支行营业部")));
		
		System.out.println(tc.calcMatchingRate(dataClean("浙江泰隆商业银行股份有限公司台州科技支行"), dataClean("台州泰隆商业银行(泰隆城市信用社石曲支行)")));
		System.out.println(tc.calcMatchingRate(dataClean("浙江泰隆商业银行清算中心"), dataClean("台州泰隆商业银行(泰隆城市信用社石曲支行)")));
		
		System.out.println(tc.calcMatchingRate(dataClean("中国工商银行股份有限公司临海东城支行"), dataClean("工商银行临海市支行")));
		System.out.println(tc.calcMatchingRate(dataClean("中国工商银行台州市临海市支行"), dataClean("工商银行临海市支行")));
		
		System.out.println(tc.calcMatchingRate(dataClean("中国银行股份有限公司台州椒江花园支行"), dataClean("中国银行股份有限公司台州市椒江支行")));
		System.out.println(tc.calcMatchingRate(dataClean("中国银行椒江支行"), dataClean("中国银行股份有限公司台州市椒江支行")));
	}

}
