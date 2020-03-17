/**
 * File Name: TextCompareUtil.java
 * Date: 2019-3-5 下午02:58:35
 */
package me.belucky.easytool.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能说明: 两个字符串比较,输出匹配度
 * 分词 + 匹配
 * @author shenzl
 * @date 2019-3-5
 * @version 1.0
 */
public class TextCompareUtil {
	protected Logger log = LoggerFactory.getLogger(TextCompareUtil.class);
	
	private SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
    
    private List<String> smallWeightWords = Arrays.asList("中国","有限公司","股份","浙江省","公司","浙江","区","市","县");
    
    private double smallWeight = 0.3D;
    
    public TextCompareUtil() {
    	
    }
    
    public TextCompareUtil(List<String> smallWeightWords) {
    	this.smallWeightWords = smallWeightWords;
    }
    
    public TextCompareUtil(List<String> smallWeightWords, double smallWeight) {
    	this.smallWeightWords = smallWeightWords;
    	this.smallWeight = smallWeight;
    }
    
    public static void main( String[] args ) throws IOException{
    	TextCompareUtil tc = new TextCompareUtil();
    	System.out.println(tc.calcMatchingRate("中国工商银行股份有限公司温岭支行", "工商银行浙江省分行温岭支行") + "");
    	System.out.println(tc.calcMatchingRate("中国工商银行台州市黄岩区支行", "工行黄岩支行") + "");
    }
    
    /**
     * 计算两个字符串的匹配度
     * @param text1
     * @param text2
     * @return
     * @throws IOException 
     */
    public double calcMatchingRate(String text1, String text2) throws IOException{
    	double matchingRate = 0.0;
    	if(isExistChineseChar(text1) || isExistChineseChar(text2)){
    		//存在中文
    		matchingRate =  twoWayMatch(text1,text2);
    	}else{
    		matchingRate = getSimilarityRatio(text1,text2);
    	}
    	return matchingRate;
    }
    
    public void showTextAnalyzer(String text) throws IOException{
        SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
        TokenStream tokenStream = smartChineseAnalyzer.tokenStream("content", text);
        CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        tokenStream.reset();
        StringBuffer buff = new StringBuffer(text).append(": ");
        while (tokenStream.incrementToken()) {
        	buff.append(charTermAttribute.toString()).append("|");
        }
        log.debug(buff.toString());
        tokenStream.end();
        tokenStream.close();
        smartChineseAnalyzer.close();
    }
    
    private double twoWayMatch(String text1,String text2) throws IOException {
    	showTextAnalyzer(text1);
        showTextAnalyzer(text2);
        //return oneWayMatch(text1, text2) + oneWayMatch(text2, text1);
        return 1 - (oneWayMatch(text1, text2) + oneWayMatch(text2, text1));
    }
    
    private double oneWayMatch(String text1,String text2) {
        try {
            Set<String> set = new HashSet<String>(10);
            TokenStream tokenStream = smartChineseAnalyzer.tokenStream("field", text1);
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                set.add(charTermAttribute.toString());
            }
            int originalCount = set.size();//
            tokenStream.end();
            tokenStream.close();
            tokenStream = smartChineseAnalyzer.tokenStream("field", text2);
            charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            int smallWeightWordsCount = 0;
            int denominator = 0;//
            while (tokenStream.incrementToken()) {
                denominator++;//
                String word = charTermAttribute.toString();
                int tempSize = set.size();
                set.add(word);
                if (tempSize + 1 == set.size() && smallWeightWords.contains(word)) {
                    smallWeightWordsCount++;
                }
            }
            int numerator = set.size() - originalCount;
            double unmatchRate = (smallWeightWordsCount * smallWeight + numerator - ((double)smallWeightWordsCount))/denominator;//
            tokenStream.end();
            tokenStream.close();
            return unmatchRate;
        } catch (IOException e) {
            return 1D;
        }
    }
    
    /**
	 * 判断是否存在中文字符
	 * @param input
	 * @return
	 */
    private boolean isExistChineseChar(String input){
		boolean isExist = false;
		char[] arr = input.toCharArray();
		for(char c : arr){
			if(c >= '\u4e00' && c <= '\u9fa5'){
				return true;
			}else if(c >= '\uf900' && c <= '\ufa2d'){
				return true;
			}
		}
		return isExist;
	}
	
	private int compare(String str, String target) {
		int d[][]; // 矩阵
		int n = str.length();
		int m = target.length();
		int i; // 遍历str的
		int j; // 遍历target的
		char ch1; // str的
		char ch2; // target的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) { // 遍历str
			ch1 = str.charAt(i - 1);
			// 去匹配target
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
			}
		}
		return d[n][m];
	}

	private int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	/**
	* 获取两字符串的相似度
	* 
	* @param str
	* @param target
	* @return
	*/
	private double getSimilarityRatio(String str, String target) {
		return 1 - (double) compare(str, target)/ Math.max(str.length(), target.length());
	}

}
