/**
 * File Name: NameRandom.java
 * Date: 2016-9-19 下午02:28:52
 */
package me.belucky.easytool.random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能说明: 随机生成姓名
 * @author shenzl
 * @date 2016-9-19
 * @version 1.0
 */
public class NameRandom {
	private static List<String> familyNameDictUnFair = new ArrayList<String>();
	private static List<String> familyNameDictFair = new ArrayList<String>();
	private static List<String> nameDictUnFair = new ArrayList<String>();
	private static List<String> nameDictFair = new ArrayList<String>();
	private static List<String> customName = new ArrayList<String>();		//自定义的名字集合
	
	static{
		init("dict/familyname_dict.txt", 1);
		init("dict/name_dict.txt", 2);
		init("dict/custom_name_dict.txt", 3);
	}
	
	/**
	 * 初始化
	 * @param dictName
	 * @param cursor
	 */
	private static void init(String dictName, int cursor){
		InputStream is = null;
		String line = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(dictName);
			InputStreamReader isr = new InputStreamReader(is,"utf8");
			BufferedReader br = new BufferedReader(isr);
			int counts = 0;
			while(br.ready()){
				line = br.readLine();
				String[] arr = line.split(",");
				String name = arr[0].trim();	
				counts += Integer.valueOf(arr[1]);
				if(cursor == 1){
					int c = Integer.valueOf(arr[1]);
					familyNameDictUnFair.add(name);	
					while(c-- > 0){
						familyNameDictFair.add(name);
					}
				}else if(cursor == 2){
					int c = Integer.valueOf(arr[1]);
					nameDictUnFair.add(name);					
					while(c-- > 0){
						nameDictFair.add(name);
					}
				}else if(cursor == 3){
					customName.add(name);
					nameDictUnFair.add(name);
					nameDictFair.add(name);
				}
			}		
		} catch (Exception e) {
			System.out.println("=======" + line + "==========");
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 随机生成全名	
	 * 默认公平,有权值计算
	 * @return
	 */
	public static String randFullName(){
		return randFamilyName() + randName();
	}
	
	/**
	 * 随机生成姓	
	 * 默认公平,有权值计算
	 * @return
	 */
	public static String randFamilyName(){
		Random rand = new Random();
		int index = rand.nextInt(familyNameDictFair.size() - 1);
		return familyNameDictFair.get(index);
	}
	/**
	 * 随机生成名	
	 * 默认公平,有权值计算
	 * @return
	 */
	public static String randName(){
		Random rand = new Random();
		int index = rand.nextInt(nameDictFair.size() - 1);
		return nameDictFair.get(index);
	}
	
	/**
	 * 随机生成全名 	
	 * 不公平
	 * @return
	 */
	public static String randFullNameUnFair(){
		return randFamilyNameUnFair() + randNameUnFair();
	}
	
	/**
	 * 随机生成姓	
	 * 不公平
	 * @return
	 */
	public static String randFamilyNameUnFair(){
		Random rand = new Random();
		int index = rand.nextInt(familyNameDictUnFair.size() - 1);
		return familyNameDictUnFair.get(index);
	}
	/**
	 * 随机生成名
	 * 不公平
	 * @return
	 */
	public static String randNameUnFair(){
		Random rand = new Random();
		int index = rand.nextInt(nameDictUnFair.size() - 1);
		return nameDictUnFair.get(index);
	}
	
	/**
	 * 随机生成名
	 * 自定义
	 * @return
	 */
	public static String randNameCustom(){
		Random rand = new Random();
		int index = rand.nextInt(customName.size() - 1);
		return customName.get(index);
	}
}
