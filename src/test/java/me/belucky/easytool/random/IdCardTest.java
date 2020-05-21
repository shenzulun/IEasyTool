/**
 * File Name: IdCardTest.java
 * Date: 2020-05-21 17:02:20
 */
package me.belucky.easytool.random;

import org.junit.Test;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-21
 * @version 1.0
 */
public class IdCardTest {
	
	@Test
	public void test1() {
		IdCard idcard = IdCardRandom.randOne();
		System.out.println(idcard);
	}
	
	@Test
	public void test2() {
		IdCard idcard = IdCardRandom.randOneUnFair();
		System.out.println(idcard);
	}

}
