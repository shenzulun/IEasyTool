/**
 * File Name: StringUtilsTest.java
 * Date: 2019-10-28 09:15:09
 */
package me.belucky.easytool.util;

import org.junit.Test;

/**
 * Description: 
 * @author shenzulun
 * @date 2019-10-28
 * @version 1.0
 */
public class StringUtilsTest {
	
	@Test
	public void test0() {
		String source = "Question_type";
		String[] target = {"id","question_type"};
		System.out.println(StringUtils.isExsit(source, target));
		System.out.println(StringUtils.isExsit(source, target, true));
	} 

}
