/**
 * File Name: IdCardRandom.java
 * Date: 2016-9-19 下午02:22:58
 */
package me.belucky.easytool.random;

import java.util.ArrayList;
import java.util.List;
import me.belucky.easytool.util.IDCardGenerateUtil;

/**
 * 功能说明: 随机生成身份信息
 * @author shenzl
 * @date 2016-9-19
 * @version 1.0
 */
public class IdCardRandom {
	
	/**
	 * 随机生成身份信息
	 * @return
	 */
	public static IdCard randOne(){
		String fullName = NameRandom.randFullName();
		String certno = IDCardGenerateUtil.random();
		return new IdCard(fullName, certno);
	}
	
	/**
	 * 随机生成身份信息-不公平模式
	 * @return
	 */
	public static IdCard randOneUnFair(){
		String fullName = NameRandom.randFullNameUnFair();
		String certno = IDCardGenerateUtil.random();
		return new IdCard(fullName, certno);
	}
	
	/**
	 * 随机生成一组身份信息
	 * @param counts
	 * @return
	 */
	public static List<IdCard> randList(int counts){
		List<IdCard> cards = new ArrayList<IdCard>();
		for(int i=0;i<counts;i++){
			cards.add(randOne());
		}
		return cards;
	}
	
	/**
	 * 随机生成一组身份信息-不公平模式
	 * @param counts
	 * @return
	 */
	public static List<IdCard> randListUnFair(int counts){
		List<IdCard> cards = new ArrayList<IdCard>();
		for(int i=0;i<counts;i++){
			cards.add(randOneUnFair());
		}
		return cards;
	}

}
