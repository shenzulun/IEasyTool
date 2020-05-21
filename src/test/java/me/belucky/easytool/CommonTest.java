/**
 * File Name: CommonTest.java
 * Date: 2019-12-09 10:27:16
 */
package me.belucky.easytool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import me.belucky.easytool.config.ConstCode;
import me.belucky.easytool.util.FileTools;

/**
 * Description: 
 * @author shenzulun
 * @date 2019-12-09
 * @version 1.0
 */
public class CommonTest {
	
	@Test
	public void test0() {
		String str = String.valueOf(ConstCode.SEPARATOR_0X1E);
		System.out.println(str);
	}
	
	@Test
	public void test1() {
		String str = "22农村信用社";
		System.out.println(str.replaceAll("^农村信用社$", "浙江省农村信用社联合社"));
	}
	
	@Test
	public void test2() {
		List<String> list1 = FileTools.getContentList("C:/Users/shenzl/Desktop/医保项目/hm.txt",true,"UTF-8");
		List<String> list2 = FileTools.getContentList("C:/Users/shenzl/Desktop/医保项目/行名行号/数据清洗/output.txt",true,"UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		for(String str : list2) {
			String[] arr = str.split("\\|");
			map.put(arr[0], str);
		}
		for(String s : list1) {
			String line = map.get(s);
			System.out.println(line);
		}
	}

}
