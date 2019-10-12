/**
 * File Name: ByteUtils.java
 * Date: 2019-08-21 14:35:52
 */
package me.belucky.easytool.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: byte工具类
 * @author shenzulun
 * @date 2019-08-21
 * @version 1.0
 */
public class ByteUtils {
	protected static Logger log = LoggerFactory.getLogger(ByteUtils.class);
	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * int类型转16进制的byte数组
	 * 默认为4个字节
	 * @param a
	 * @return
	 */
	public static byte[] intToHexByteArray(int a) {
		String hexString = Integer.toHexString(a);
		//需要补0
		int length = 8;
		int curr = hexString.length();
		while(curr++ < length) {
			hexString = "0" + hexString;
		}
		return hexStringToByteArray(hexString);
	}
	
	
	/**
     * 16进制表示的字符串转换为字节数组
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
    
    /**
     * 16进制的byte[]数组转换成10进制数字
     * @param bytes
     * @return
     */
    public static int bytesArray16ToInt(byte[] bytes) {
    	String str = bytesToHexString(bytes);
    	return hexStringToInt(str);
    }

    /**
     * byte[]数组转换为16进制的字符串
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    /**
     * 16进制字符串转10进制数字
     * @param hexString
     * @return
     */
    public static int hexStringToInt(String hexString) {
    	return Integer.parseInt(hexString, 16);
    }
    
    /**
     * 10进制数字，转换成10位长度，不足右边补空格
     * @param a
     * @return
     */
    public static byte[] intTo10ByteArray(int a, String charset) {
    	byte[] result = null;
    	String intString = String.valueOf(a);
		//需要补0
		int length = 10;
		int curr = intString.length();
		while(curr++ < length) {
			intString = intString + " ";
		}
		try {
			result = intString.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		}
		return result;
    }
    
    /**
     * 10字节的byte数组转数字
     * @param arr
     * @return
     */
    public static int byteArray10ToInt(byte[] bytes) {
    	String str = new String(bytes);
    	return Integer.valueOf(str.trim());
    }
    
    /**
     * 文件转byte数组
     * @param filePath
     * @return
     */
    public static byte[] fileToByteArray(String filePath) {
		byte[] buffer = null;
		File file = new File(filePath);
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			log.error("",e);
		}catch (IOException e) {
			log.error("",e);
		} finally {
            try {
            	if (bos != null) {
            		bos.close();
            	}
            	if (fis != null) {
            		fis.close();
            	}	
			} catch (IOException e) {
				log.error("",e);
			}
		}
		return buffer;
	}
    
    /**
     * byte数组转文件
     * @param byteArray
     * @param targetPath
     */
    public static void byteArrayToFile(byte[] byteArray, String targetPath) {
    	InputStream in = new ByteArrayInputStream(byteArray);
        File file = new File(targetPath);
        int ind1 = targetPath.lastIndexOf("/");
		if(ind1 > 0){
			String p = targetPath.substring(0, ind1);
			File dir = new File(p);
			if(!dir.exists()){
				dir.mkdirs();
			}
		}
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            log.error("",e);
        } finally {
        	try {
				in.close();
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				log.error("",e);
			}
        }
	}
    
    /**
          * 组合多个byte[]
     * @param length
     * @param arrays
     * @return
     */
    public static byte[] byteArrayGroup(int length, byte[]... arrays) {
    	int index = 0;
    	byte[] newArray = new byte[length];
    	for(byte[] arr : arrays) {
    		for(byte b : arr) {
    			newArray[index++] = b;
    		}
    	}
    	return newArray;
    }
    
    /**
          * 拷贝byte数组
     * @param source
     * @param startIndex  原数组的起始INDEX
     * @param length	新数组的长度
     * @return
     */
    public static byte[] clone(byte[] source, int startIndex, int length) {
    	byte[] arr = new byte[length];
    	for(int i=0;i<length;i++) {
    		arr[i] = source[startIndex + i];
    	}
    	return arr;
    }
    
    /**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

}
